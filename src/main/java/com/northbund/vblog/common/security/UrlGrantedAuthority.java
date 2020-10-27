package com.northbund.vblog.common.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by lk
 */
public class UrlGrantedAuthority implements GrantedAuthority {

    private String permissionUrl;

    public String getPermissionUrl() {
        return permissionUrl;
    }

    public void setPermissionUrl(String permissionUrl) {
        this.permissionUrl = permissionUrl;
    }


    public UrlGrantedAuthority (String permissionUrl) {
        this.permissionUrl = permissionUrl;
    }

    @Override
    public String getAuthority() {
        return this.permissionUrl;
    }
}
