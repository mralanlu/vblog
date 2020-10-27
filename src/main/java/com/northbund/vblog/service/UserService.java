package com.northbund.vblog.service;

import com.alibaba.fastjson.JSONObject;
import com.northbund.vblog.common.PageBean;
import com.northbund.vblog.common.annotation.PagingQuery;
import com.northbund.vblog.common.annotation.ValidParamAnnotation;
import com.northbund.vblog.common.enums.ResultCodeEnum;
import com.northbund.vblog.common.exceptions.CommonException;
import com.northbund.vblog.mapper.UserMapper;
import com.northbund.vblog.pojo.entity.User;
import com.northbund.vblog.pojo.entity.UserRoleRel;
import com.northbund.vblog.pojo.param.LoginParam;
import com.northbund.vblog.pojo.param.UserParam;
import com.northbund.vblog.pojo.vo.TokenUserResult;
import com.northbund.vblog.pojo.vo.UserResult;
import com.northbund.vblog.utils.BeanUtilExtend;
import com.northbund.vblog.utils.DateUtil;
import com.northbund.vblog.utils.MD5Util;
import com.northbund.vblog.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.utility.RandomString;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static com.northbund.vblog.common.constant.Constant.SESSION_PRE;
import static com.northbund.vblog.common.constant.Constant.VERIFY_CODE;

/**
 * @author lk
 */
@Slf4j
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private SendSmsService sendSmsService;

    private static long EXPIRE_TIME = 2;

    @PagingQuery
    public PageBean getUserInfoList(UserParam userParam){
        return new PageBean(userMapper.findAllByParam(userParam));
    }

    public UserResult getUserInfo(Long id){
        return userMapper.findUserInfoById(id);
    }

    public User getUserInfoByAccount(String account){
        return userMapper.findUserInfoByAccount(account);
    }

    public void updateUserInfo(UserParam userParam){
        if(null == userParam.getId()){
            throw new CommonException(ResultCodeEnum.USER_ID_NULL);
        }
        User user = new User();
        BeanUtilExtend.copyProperties( userParam,user,true,null);
        if(null != user.getPwd()){
            user.setPwd(MD5Util.md5(user.getPwd()));
        }
        userMapper.update(user);
    }

    /**
     * 获取短信验证码
     * @param phone
     * @return
     */
    public void getVerifyCode(String phone){
        if(null == phone){
            throw new CommonException("手机号不能为空！");
        }
        String redisVerifyCode = redisUtils.get(VERIFY_CODE+phone);
        if(null != redisVerifyCode){
            throw new CommonException(ResultCodeEnum.GET_VERIFICATION_FREQUENTLY);
        }
        String verifyCode = String.format("%04d",new Random().nextInt(9999));
        //redisUtils.setEx(VERIFY_CODE+phone,verifyCode,60,TimeUnit.SECONDS);
        redisUtils.setEx(VERIFY_CODE+phone,verifyCode,60,TimeUnit.SECONDS);
        sendSmsService.sendSms("【虹口融媒中心】验证码："+verifyCode+"。此验证码60秒内有效，如非本人操作，请忽略。",phone);
        //sendSmsService.getSmsStatus("45021013163906495100","1",null,null);
    }

    /**
     * 注册
     * @param userParam
     * @return
     */
    public User register(UserParam userParam){
        //校验参数
        if(null == userParam.getPhone()){
            throw new CommonException(ResultCodeEnum.PARAM_ERROR);
        }
        //校验短信验证码
        String redisVerifyCode = redisUtils.get(VERIFY_CODE+userParam.getPhone());
        if(null == redisVerifyCode){
            throw new CommonException(ResultCodeEnum.VERIFICATION_OUT_OF_TIME);
        }
        if(redisVerifyCode.equals(userParam.getVerifyCode())){
            User oldUser = getUserInfoByAccount(userParam.getAccount());
            if(null == oldUser){
                User user = new User();
                BeanUtilExtend.copyProperties( userParam,user,true,null);
                user.setCreateTime(new Date());
                if(null != userParam.getPwd()){
                    user.setPwd(MD5Util.md5(userParam.getPwd()));
                }else {
                    user.setPwd(MD5Util.md5("@lukailoveluyusen@"));
                }
                if(null == userParam.getName()){
                    user.setName("北外滩"+ RandomString.make(5));
                }
                userMapper.insert(user);
                UserRoleRel userRoleRel = new UserRoleRel();
                userRoleRel.setUserId(user.getId());
                userRoleRel.setRoleId(2l);
                userMapper.insertUserRoleRel(userRoleRel);
                return user;
            }else {
                User user = new User();
                BeanUtilExtend.copyProperties( oldUser,user,true,null);
                if(null != userParam.getPwd()){
                    user.setPwd(MD5Util.md5(userParam.getPwd()));
                }
                userMapper.update(user);
                return user;
            }
        }{
            throw new CommonException(ResultCodeEnum.VERIFICATION_CODE_ERROR);
        }
    }

    /**
     * 增加管理员
     * @param userParam
     * @return
     */
    public Long addAdmin(UserParam userParam){
        User oldUser = getUserInfoByAccount(userParam.getAccount());
        if(null!=oldUser){
            throw new CommonException(ResultCodeEnum.USER_NAME_EXEITS);
        }
        User user = new User();
        BeanUtilExtend.copyProperties( userParam,user,true,null);
        user.setCreateTime(new Date());
        if(null==user.getPwd()){
            user.setPwd(MD5Util.md5("123456"));
        }else {
            user.setPwd(MD5Util.md5(user.getPwd()));
        }
        userMapper.insert(user);
        UserRoleRel userRoleRel = new UserRoleRel();
        userRoleRel.setUserId(user.getId());
        userRoleRel.setRoleId(1l);
        userMapper.insertUserRoleRel(userRoleRel);
        return user.getId();
    }

    /**
     * 删除管理员
     * @param userParam
     * @return
     */
    public int delete(UserParam userParam){
        if(null == userParam.getId()){
            throw new CommonException(ResultCodeEnum.USER_ID_NULL);
        }
        userMapper.delete(userParam.getId());
        return userMapper.deleteRel(userParam.getId());
    }


    /**
     * 登录
     * @param loginParam
     * @return
     */
    @Transactional
    public JSONObject login(@ValidParamAnnotation LoginParam loginParam) {

        UserResult user = userMapper.findByAccountAndPwd(loginParam.getAccount(), loginParam.getPassword());
        if(null != loginParam.getVerifyCode()){
            String redisVerifyCode = redisUtils.get(VERIFY_CODE+loginParam.getPhone());
            if(null == redisVerifyCode){
                throw new CommonException(ResultCodeEnum.VERIFICATION_OUT_OF_TIME);
            }
            if(redisVerifyCode.equals(loginParam.getVerifyCode())){
                User oldUser = getUserInfoByAccount(loginParam.getAccount());
                if(null==oldUser){
                    UserParam userParam = new UserParam();
                    userParam.setPhone(loginParam.getPhone());
                    userParam.setAccount(loginParam.getAccount());
                    userParam.setVerifyCode(loginParam.getVerifyCode());
                    oldUser = new User();
                    oldUser = register(userParam);
                }
                //验证码登录
                User updateUser = new User();
                updateUser.setId(oldUser.getId());
                updateUser.setLastLoginTime(new Date());
                userMapper.update(updateUser);
                JSONObject json = new JSONObject();
                json.put("userId",oldUser.getId());
                json.put("account",loginParam.getAccount());
                String token = DigestUtils.md2Hex(oldUser.getId()+loginParam.getAccount()+System.currentTimeMillis()+"");
                redisUtils.set(SESSION_PRE + token,json.toJSONString());
                redisUtils.expire(SESSION_PRE + token,EXPIRE_TIME, TimeUnit.HOURS);
                JSONObject responseJson = new JSONObject();
                log.error("登陆--获取到token----"+"token值为:"+token+"----"+"------时间为"+ DateUtil.formatYYYYMMDDHHMMSSS(new Date())+"----剩余时间为"+redisUtils.getExpire(SESSION_PRE + token));
                List<Integer> role = userMapper.getRoleIdByUserId(user.getId());
                Integer roleId = 2;
                if(!CollectionUtils.isEmpty(role)){
                    for (Integer r :role){
                        if(r.intValue() == 1){
                            roleId = r.intValue();
                        }
                    }

                }
                responseJson.put("token",token);
                responseJson.put("userId",oldUser.getId());
                responseJson.put("name",oldUser.getName());
                responseJson.put("roleId",roleId);
                return responseJson;
            }else {
                throw new CommonException(ResultCodeEnum.VERIFICATION_CODE_ERROR);
            }

        }else {
            if(user.getStatus()== 2){
                throw new CommonException(ResultCodeEnum.LOGIN_ERROR_EM_NOT_EXISTENT);
            }
            if(MD5Util.md5("@lukailoveluyusen@").equals(user.getPwd())){
                throw new CommonException(ResultCodeEnum.PLEASE_REGISTER);
            }
            User updateUser = new User();
            updateUser.setId(user.getId());
            updateUser.setLastLoginTime(new Date());
            userMapper.update(updateUser);
            JSONObject json = new JSONObject();
            json.put("userId",user.getId());
            json.put("account",user.getAccount());
            String token = DigestUtils.md2Hex(user.getId()+user.getAccount()+System.currentTimeMillis()+"");
            redisUtils.set(SESSION_PRE + token,json.toJSONString());
            redisUtils.expire(SESSION_PRE + token,EXPIRE_TIME, TimeUnit.HOURS);
            JSONObject responseJson = new JSONObject();
            log.error("登陆--获取到token----"+"token值为:"+token+"----"+"------时间为"+ DateUtil.formatYYYYMMDDHHMMSSS(new Date())+"----剩余时间为"+redisUtils.getExpire(SESSION_PRE + token));
            List<Integer> role = userMapper.getRoleIdByUserId(user.getId());
            Integer roleId = 2;
            if(!CollectionUtils.isEmpty(role)){
                for (Integer r :role){
                    if(r.intValue() == 1){
                        roleId = r.intValue();
                    }
                }

            }
            responseJson.put("token",token);
            responseJson.put("userId",user.getId());
            responseJson.put("name",user.getName());
            responseJson.put("roleId",roleId);
            return responseJson;
        }
    }

    //登陆验证
    public ResultCodeEnum loginVerification(String loginName, Long timeMillis, String verificationCode){
        // 查询出登陆错误次数
        //Integer loginErrorNumber=loginErrorMapperl.getNumberBySameDay(loginName);
        Integer loginErrorNumber =0;
        if(null==loginErrorNumber||loginErrorNumber.intValue()<3){
            //返回成功,顺利进行下面的内容
            return ResultCodeEnum.SUCCESS;
        }
        if(null==verificationCode){
            return ResultCodeEnum.CODE_ERROR;
        }
        String codeValue = redisUtils.get(SESSION_PRE + timeMillis );
        redisUtils.delete(SESSION_PRE + timeMillis);
        if(verificationCode.equals(codeValue)){
            return ResultCodeEnum.SUCCESS;
        }else{
            return ResultCodeEnum.CODE_ERROR;
        }
    }

    /**
     * 退出登陆
     * @param token
     * @return
     */
    public boolean logout(String token) {
        redisUtils.delete(SESSION_PRE + token);
        return true;
    }

    /**
     *
     * @return Current logged {@link User#getId()} or null
     */
    public static Long getPrincipal() {
        TokenUserResult principal = (TokenUserResult) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Optional.ofNullable(principal).map(TokenUserResult::getUser).map(User::getId).orElse(null);
    }

    public static User getUser() {
        TokenUserResult principal = (TokenUserResult) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Optional.ofNullable(principal).map(TokenUserResult::getUser).orElse(null);
    }

    public User validateEmployeeId(Long userId, String property) {
        User user = userMapper.findById(userId);
        if (user == null) {
            throw new CommonException(ResultCodeEnum.USER_ID_NOT_EXIST_ERROR, property);
        }
        return user;
    }
}
