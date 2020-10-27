package com.northbund.vblog.pojo.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.northbund.vblog.utils.handler.JsonDatetimeFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="image_calendar")
public class ImageCalendar {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    protected Long id;

    /**
     * 时间
     */
    @Column(name="display_time")
    protected Date displayTime;

    /**
     * 主题
     */
    @Column(name="title")
    protected String title;

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

    @Column(name = "year_describe")
    protected String yearDescribe;

    @Column(name = "introduction")
    protected String introduction;
    /**
     * 年
     */
    @Column(name="year")
    protected Integer year;

    /**
     * 月
     */
    @Column(name="month")
    protected Integer month;

    /**
     * 日
     */
    @Column(name="day")
    protected Integer day;

}
