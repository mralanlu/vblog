package com.northbund.vblog.common.security;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.northbund.vblog.common.enums.ResultCodeEnum;
import com.northbund.vblog.utils.CheckAuthorityUtil;
import com.northbund.vblog.utils.DateUtil;
import com.northbund.vblog.utils.EmptyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.northbund.vblog.common.constant.Constant.*;

@Slf4j
public class TokenAuthorizationFilter extends BasicAuthenticationFilter {

    private UserDetailsService userDetailsService;
    private StringRedisTemplate redisTemplate;


    public TokenAuthorizationFilter(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, StringRedisTemplate redisTemplate) {
        super(authenticationManager);
        this.userDetailsService=userDetailsService;
        this.redisTemplate=redisTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        response.setContentType(APPLICATION_JSON_UTF8_VALUE);
        if(!CheckAuthorityUtil.check(request)){
            String token = request.getHeader(TOKEN);
            if(EmptyUtil.empty(token)){
                log.error("由于获取token为空,报错102"+request.getServletPath()+"---"+request.getPathInfo());
                representation(ResultCodeEnum.GET_TOKEN_ERROR,response);
            return;
            }
            String tokenValue = redisTemplate.opsForValue().get(SESSION_PRE + token);
            if(EmptyUtil.empty(tokenValue)){
                log.error("由于获取到的token为空,报错104,token值为"+token+"----"+request.getServletPath()+"---"+request.getPathInfo()+"------时间为"+ DateUtil.formatYYYYMMDDHHMMSSS(new Date()));
                representation(ResultCodeEnum.GET_TOKEN_ERROR_TIEMOUT,response);
                return;
            }else{
                log.error("获取到token----"+"token值为:"+token+"----"+request.getServletPath()+"---"+request.getPathInfo()+"------时间为"+ DateUtil.formatYYYYMMDDHHMMSSS(new Date())+"----剩余时间为"+redisTemplate.getExpire(SESSION_PRE + token));
            }
            try{
                redisTemplate.expire(SESSION_PRE + token,2, TimeUnit.HOURS);
                JSONObject json = JSON.parseObject(tokenValue);
                UserDetails tokenEmployee=userDetailsService.loadUserByUsername(json.getString("account"));
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(tokenEmployee, null, tokenEmployee.getAuthorities()));
            }catch (Exception e){
                log.error(" 系统异常错误----"+request.getServletPath()+"---"+request.getPathInfo());
                representation(ResultCodeEnum.SYSTEM_ERROR,response);
                return;
            }
        }else{
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(null, null, new ArrayList<>()));
        }
        super.doFilterInternal(request, response, chain);
    }

    protected void representation(ResultCodeEnum msg, HttpServletResponse response) throws IOException {
        JSONObject json = new JSONObject();
        json.put("resultCode", msg.getCode().toString());
        json.put("msg", msg.getDesc());
        response.getWriter().write(JSON.toJSONString(json));
    }
}
