package com.northbund.vblog.common.security;

import com.northbund.vblog.mapper.UserMapper;
import com.northbund.vblog.pojo.entity.User;
import com.northbund.vblog.pojo.vo.TokenUserResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lk
 */
@Service
public class UrlUserService implements UserDetailsService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String userName) { //重写loadUserByUsername 方法获得 userdetails 类型用户
        User user = userMapper.findByAccountAndStatus(userName, 1);
        if (user != null) {
            TokenUserResult tokenUserResult = new TokenUserResult(user.getAccount(), user.getPwd(), null);
            tokenUserResult.setUser(user);
            List<String> permissions = userMapper.getPathsByUserId(user.getId());
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            for (String path : permissions) {
                if (path != null ) {
                    GrantedAuthority grantedAuthority = new UrlGrantedAuthority(path);
                    grantedAuthorities.add(grantedAuthority);
                }
            }
            tokenUserResult.setAuthorities(grantedAuthorities);
            return tokenUserResult;

        } else {
            throw new UsernameNotFoundException("admin: " + userName + " do not exist!");
        }
    }
}