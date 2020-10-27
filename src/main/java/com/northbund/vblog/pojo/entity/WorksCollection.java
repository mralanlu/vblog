package com.northbund.vblog.pojo.entity;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.northbund.vblog.utils.handler.JsonDatetimeFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="works_collection")
public class WorksCollection{
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
     * 作品介绍
     */
    @Column(name="works_introduction")
    protected String worksIntroduction;

    /**
     * 作品标题
     */
    @Column(name="title")
    protected String title;

    /**
     * 作者名字
     */
    @Column(name="name")
    protected String name;

    /**
     * 手机
     */
    @Column(name="phone")
    protected String phone;

    /**
     * 拍摄地址
     */
    @Column(name="address")
    protected String address;

    /**
     * 作品状态  0 待审核  1 审核成功 2 审核失败
     */
    @Column(name="works_status")
    protected Integer worksStatus;

    /**
     * 点赞数
     */
    @Column(name="likes")
    protected Integer likes;

    /**
     * 创建时间
     */
    @JsonSerialize(using = JsonDatetimeFormat.class)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name="create_time")
    protected Date createTime;


    /**
     * 创建人
     */
    @Column(name="create_by")
    protected Long createBy;

    /**
     * 审核时间
     */
    @JsonSerialize(using = JsonDatetimeFormat.class)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name="review_time")
    protected Date reviewTime;

    /**
     * 审核人
     */
    @Column(name="review_by")
    protected Long reviewBy;


}
