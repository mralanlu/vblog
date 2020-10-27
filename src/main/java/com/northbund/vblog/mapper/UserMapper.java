package com.northbund.vblog.mapper;

import com.northbund.vblog.pojo.entity.User;
import com.northbund.vblog.pojo.entity.UserRoleRel;
import com.northbund.vblog.pojo.param.UserParam;
import com.northbund.vblog.pojo.vo.UserResult;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户mapper
 * @author lk
 */
@Repository
public interface UserMapper {

    List<UserResult> findAllByParam(UserParam userParam);

    Long insert(User user);

    int insertUserRoleRel(UserRoleRel userRoleRel);

    int update(User user);

    int delete(Long id);

    int deleteRel(Long id);

    UserResult findByAccountAndPwd(String account, String pwd);

    User findById(Long id);

    UserResult findUserInfoById(Long id);

    User findUserInfoByAccount(String account);

    User findByAccountAndStatus(String account,Integer status);

    List<String> getPathsByUserId(Long userId);

    void clearContinueSignIn(String yesterday,String today);

    List<Integer> getRoleIdByUserId(@Param("userId") Long userId);

}
