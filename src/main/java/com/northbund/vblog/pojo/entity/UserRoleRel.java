package com.northbund.vblog.pojo.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="user_role_rel")
public class UserRoleRel  {

    /**
     * id
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    protected Long id;

    /**
     * 用户id
     */
    @Column(name="user_id")
    protected Long userId;

    /**
     * 角色id
     */
    @Column(name="role_id")
    protected Long roleId;
}
