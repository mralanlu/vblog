package com.northbund.vblog.utils;

public class EmptyUtil {
    public static boolean empty(String s)
    {
        if(s==null||"".equals(s.trim())||"null".equals(s)){
            return true;
        }
        return s.trim().length()==0;
    }
}
