package com.northbund.vblog.pojo.entity;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.northbund.vblog.utils.handler.JsonDatetimeFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="image_calendar_detail")
public class ImageCalendarDetail  {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    protected Long id;

    /**
     * 影像日历id
     */
    @Column(name="image_calendar_id")
    protected Long imageCalendarId;

    /**
     * 默认标识
     */
    @Column(name="default_flag")
    protected Integer defaultFlag;

    /**
     * 介绍
     */
    @Column(name="introduction")
    protected String introduction;

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
}
