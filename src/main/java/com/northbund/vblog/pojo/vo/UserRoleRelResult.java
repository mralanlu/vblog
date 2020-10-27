package com.northbund.vblog.pojo.vo;


import lombok.Data;

@Data
public class UserRoleRelResult {

    protected Integer id;

    /**
     * 用户id
     */
    protected Integer relId;

    /**
     * 角色id
     */
    protected Integer roleId;
}
