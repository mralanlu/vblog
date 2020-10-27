package com.northbund.vblog.common.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.northbund.vblog.common.enums.ResultCodeEnum;
import com.northbund.vblog.common.exceptions.CommonException;
import com.northbund.vblog.pojo.param.LoginParam;
import com.northbund.vblog.pojo.vo.TokenUserResult;
import com.northbund.vblog.service.UserService;
import com.northbund.vblog.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static com.northbund.vblog.common.constant.Constant.VERIFY_CODE;


@Slf4j
public class TokenAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UserDetailsService userDetailsService;

    private AuthenticationManager authenticationManager;

    private UserService userService;

    private RedisUtils redisUtils;




    public TokenAuthenticationFilter(AuthenticationManager authenticationManager) {

        this.authenticationManager = authenticationManager;
        super.setFilterProcessesUrl("/user/login");

    }

    public TokenAuthenticationFilter(RedisUtils redisUtils,AuthenticationManager authenticationManager, UserService userService, UserDetailsService userDetailsService) {
        this.redisUtils = redisUtils;
        this.userService=userService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService=userDetailsService;
        super.setFilterProcessesUrl("/user/login");

    }

    //校验前
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        // 从输入流中获取到登录的信息
        try{
            LoginParam loginParam = new ObjectMapper().readValue(request.getInputStream(),LoginParam.class);
            request.getSession().setAttribute("account",loginParam.getAccount());
            request.getSession().setAttribute("verifyCode",loginParam.getVerifyCode());
            request.getSession().setAttribute("phone",loginParam.getPhone());
            //ResultCodeEnum resultCodeEnum = userService.loginVerification(loginParam.getAccount(), loginParam.getTimeMillis(), loginParam.getVerificationCode());
            ResultCodeEnum resultCodeEnum = ResultCodeEnum.SUCCESS;
            if(resultCodeEnum.getCode().intValue()!=ResultCodeEnum.SUCCESS.getCode().intValue()){
                JSONObject json = new JSONObject();
                json.put("resultCode", ResultCodeEnum.VERIFICATION_CODE_ERROR.getCode());
                json.put("msg","验证码错误");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(JSON.toJSONString(json));
                return null;
            }
            /*String  verifyCode = redisUtils.get(VERIFY_CODE+loginParam.getPhone());
            if(null == verifyCode){
                throw new CommonException(ResultCodeEnum.VERIFICATION_OUT_OF_TIME);
            }
            if(verifyCode.equals(loginParam.getVerifyCode())){
                User user = userService.getUserInfoByAccount(loginParam.getAccount());
                loginParam.setPassword(user.getPwd());
            }*/
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginParam.getAccount(), loginParam.getPassword(), new ArrayList<>())
            );
        }catch (IOException e){
            e.printStackTrace();
            return null;
        }
    }

    // 成功验证后调用的方法
    // 如果验证成功，就生成token并返回
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        TokenUserResult tokenUserResult = (TokenUserResult) authResult.getPrincipal();
        LoginParam loginParam= JSON.parseObject(JSON.toJSONString(tokenUserResult),LoginParam.class);
        try {
            JSONObject json = new JSONObject();
            json.put("resultCode", ResultCodeEnum.LOGIN.getCode()+"");
            json.put("msg","login_success");
            json.put("data",userService.login(loginParam));
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JSON.toJSONString(json));
        }catch (CommonException e){
            log.error("登陆错误",e);
            response.setCharacterEncoding("UTF-8");
            representation(e.getMessageInfo(),response);
        }catch (Exception e){
            log.error("登陆错误",e);
            response.setCharacterEncoding("UTF-8");
            representation(ResultCodeEnum.SYSTEM_ERROR,response);
        }
    }

    // 这是验证失败时候调用的方法
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException {
        //账户密码错误后存入记录表
        /*try{
           // LoginParam loginParam = new ObjectMapper().readValue(request.getInputStream(),LoginParam.class);
            String loginName=(String)request.getSession().getAttribute("loginName");
            if(null!=loginName){
                userService.saveLoginError(loginName);
                request.getSession().removeAttribute("loginName");
            }
        }catch (Exception e){
            log.error("存入登陆错误记录表出错",e);
        }*/
        JSONObject json = new JSONObject();
        String verifyCode=(String)request.getSession().getAttribute("verifyCode");
        String account=(String)request.getSession().getAttribute("account");
        String phone=(String)request.getSession().getAttribute("phone");
        if(null!=verifyCode){
            String redisVerifyCode = redisUtils.get(VERIFY_CODE+phone);
            if(null==redisVerifyCode){
                json.put("resultCode", ResultCodeEnum.LOGIN_ERROR.getCode()+"");
                json.put("msg",ResultCodeEnum.VERIFICATION_OUT_OF_TIME.getDesc());
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write(JSON.toJSONString(json));
            }else {
                if(redisVerifyCode.equals(verifyCode)){
                    LoginParam loginParam = new LoginParam();
                    loginParam.setAccount(account);
                    loginParam.setPhone(phone);
                    loginParam.setVerifyCode(verifyCode);
                    json.put("resultCode", ResultCodeEnum.LOGIN.getCode()+"");
                    json.put("msg","login_success");
                    json.put("data",userService.login(loginParam));
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(JSON.toJSONString(json));
                }else {
                    json.put("resultCode", ResultCodeEnum.LOGIN_ERROR.getCode());
                    json.put("msg",ResultCodeEnum.VERIFICATION_CODE_ERROR.getDesc());
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write(JSON.toJSONString(json));
                }
            }

        }else {
            json.put("resultCode", ResultCodeEnum.LOGIN_ERROR.getCode());
            json.put("msg","账户名或者密码错误!");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(JSON.toJSONString(json));
        }


       // response.getWriter().write("authentication failed, reason: " + failed.getMessage());
    }

    protected void representation(ResultCodeEnum msg, HttpServletResponse response) throws IOException {
        JSONObject json = new JSONObject();
        json.put("resultCode", msg.getCode().toString());
        json.put("msg", msg.name());
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(JSON.toJSONString(json));
    }


}