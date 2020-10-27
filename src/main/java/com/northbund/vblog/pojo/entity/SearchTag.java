package com.northbund.vblog.pojo.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name="search_tag")
public class SearchTag {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    protected Long id;


    /**
     * 搜索名称
     */
    @Column(name="name")
    protected String name;
}
