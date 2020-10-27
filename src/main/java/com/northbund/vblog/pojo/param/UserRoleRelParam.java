package com.northbund.vblog.pojo.param;


import lombok.Data;

@Data
public class UserRoleRelParam {

    protected Long id;

    /**
     * 用户id
     */
    protected Long relId;

    /**
     * 角色id
     */
    protected Long roleId;
}
