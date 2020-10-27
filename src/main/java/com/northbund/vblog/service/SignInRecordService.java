package com.northbund.vblog.service;

import com.northbund.vblog.common.PageBean;
import com.northbund.vblog.common.annotation.PagingQuery;
import com.northbund.vblog.common.enums.ResultCodeEnum;
import com.northbund.vblog.common.exceptions.CommonException;
import com.northbund.vblog.mapper.SignInRecordMapper;
import com.northbund.vblog.mapper.UserMapper;
import com.northbund.vblog.pojo.entity.SignInRecord;
import com.northbund.vblog.pojo.entity.User;
import com.northbund.vblog.pojo.param.SignInRecordParam;
import com.northbund.vblog.pojo.vo.SignInRecordResult;
import com.northbund.vblog.utils.BeanUtilExtend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SignInRecordService {

    @Autowired
    private SignInRecordMapper signInRecordMapper;
    @Autowired
    private UserMapper userMapper;

    @PagingQuery
    public PageBean findAllByParam(SignInRecordParam signInRecordParam) {
        List<SignInRecordResult> signInRecordResults = signInRecordMapper.findAllByParam(signInRecordParam);
        return new PageBean(signInRecordResults);
    }

    @Transactional
    public void signIn(SignInRecordParam signInRecordParam){
        if(null != signInRecordParam.getUserId() || null == signInRecordParam.getSignInDate()){
            //verify if already sign in
            List<SignInRecordResult> signInRecordResults = signInRecordMapper.findAllByParam(signInRecordParam);
            if(signInRecordResults.size()>0){
                throw new CommonException(ResultCodeEnum.ALREADY_SIGN_IN);
            }else {
                SignInRecord signInRecord = new SignInRecord();
                BeanUtilExtend.copyProperties( signInRecordParam,signInRecord,true,null);
                signInRecordMapper.insert(signInRecord);
                User user = userMapper.findById(signInRecordParam.getUserId());
                int score = (null == user.getScore())?0:user.getScore();
                if(null!=signInRecordParam.getContinueSignInFlag() && signInRecordParam.getContinueSignInFlag()){
                    //连续签到+1
                    user.setContinueSignIn(user.getContinueSignIn()+1);
                    //连续签到七天以上，积分加7
                    if(user.getContinueSignIn()>7){
                        user.setScore(score+7);
                    }else {
                        user.setScore(score+user.getContinueSignIn());
                    }
                }else {
                    //非连续签到
                    user.setContinueSignIn(1);
                    user.setScore(score+1);
                }
                userMapper.update(user);
            }
        }else {
            throw new CommonException(ResultCodeEnum.PARAM_ERROR);
        }
    }

    public void update(SignInRecordParam signInRecordParam){
        SignInRecord signInRecord = new SignInRecord();
        BeanUtilExtend.copyProperties( signInRecordParam,signInRecord,true,null);
        signInRecordMapper.update(signInRecord);
    }
    public void delete(SignInRecordParam signInRecordParam){
        signInRecordMapper.delete(signInRecordParam.getId());
    }
}
