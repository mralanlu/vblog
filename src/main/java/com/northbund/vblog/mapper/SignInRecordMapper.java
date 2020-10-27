package com.northbund.vblog.mapper;

import com.northbund.vblog.pojo.entity.SignInRecord;
import com.northbund.vblog.pojo.param.SignInRecordParam;
import com.northbund.vblog.pojo.vo.SignInRecordResult;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户mapper
 * @author lk
 */
@Repository
public interface SignInRecordMapper {

    List<SignInRecordResult> findAllByParam(SignInRecordParam signInRecordParam);

    Long insert(SignInRecord signInRecord);

    int update(SignInRecord signInRecord);

    int delete(Long id);

}
