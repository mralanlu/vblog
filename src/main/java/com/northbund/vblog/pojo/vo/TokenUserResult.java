package com.northbund.vblog.pojo.vo;

import com.northbund.vblog.pojo.entity.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * @description: lk
 **/
@Data
public class TokenUserResult implements UserDetails {

    protected String account;
    protected String password;

    private User user;

    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return account;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    public TokenUserResult(){

    }

    public TokenUserResult(String account, String password, Collection<? extends GrantedAuthority> authorities){
        this.authorities=authorities;
        this.account=account;
        this.password=password;

    }
}
