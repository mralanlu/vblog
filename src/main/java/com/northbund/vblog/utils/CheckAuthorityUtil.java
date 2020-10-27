package com.northbund.vblog.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * lk
 **/
@Component
public class CheckAuthorityUtil {

    private static String ignore_permission_urls;

    @Value("${ignore_permission_urls}")
    public void setIgnore_permission_urls(String ignore_permission_urls) {
        CheckAuthorityUtil.ignore_permission_urls = ignore_permission_urls;
    }

    /**
     * 是否可以直接访问
     * @return
     */
    public static boolean check(HttpServletRequest request){
        String url = request.getServletPath();
        String pathInfo = request.getPathInfo();
        if (pathInfo != null) {
            url = StringUtils.hasLength(url) ? url + pathInfo : pathInfo;
        }
        String[] urls=ignore_permission_urls.split(",");
        for(String l:urls){
            if(url.trim().contains(l.trim())){
                return true;
            }
        }
        return  false;
    }
}
