package com.northbund.vblog.pojo.entity;

import lombok.Data;
import javax.persistence.*;

@Entity
@Data
@Table(name="role")
public class Role {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    protected Long id;


    /**
     * 角色名
     */
    @Column(name="name")
    protected String name;
}
