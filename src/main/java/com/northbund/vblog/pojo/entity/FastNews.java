package com.northbund.vblog.pojo.entity;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.northbund.vblog.utils.handler.JsonDatetimeFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="fast_news")
public class FastNews  {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    protected Long id;

    /**
     * 链接
     */
    @Column(name="link")
    protected String link;

    /**
     * 内容
     */
    @Column(name="content")
    protected String content;

    /**
     * 标题
     */
    @Column(name="title")
    protected String title;

    /**
     * 地址
     */
    @Column(name="address")
    protected String address;

    /**
     * 创建时间
     */
    @JsonSerialize(using = JsonDatetimeFormat.class)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name="create_time")
    protected Date createTime;

    /**
     * 更新时间
     */
    @JsonSerialize(using = JsonDatetimeFormat.class)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name="update_time")
    protected Date updateTime;

    /**
     * 创建人
     */
    @Column(name="create_by")
    protected Long createBy;

    /**
     * 展示时间
     */
    @JsonSerialize(using = JsonDatetimeFormat.class)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Column(name="display_time")
    private  Date displayTime;
}
