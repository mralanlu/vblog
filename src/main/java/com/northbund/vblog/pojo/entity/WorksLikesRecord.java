package com.northbund.vblog.pojo.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="works_likes_record")
public class WorksLikesRecord {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    /**
     * 用户id
     */
    @Column(name = "user_id")
    protected Long userId;

    /**
     * 作品id
     */
    @Column(name = "works_id")
    protected Long worksId;

    /**
     * 点赞状态 0 取消点赞 1 点赞
     */
    @Column(name = "click_status")
    protected Integer clickStatus;

    /**
     * 点赞日期
     */
    @Column(name = "likes_date")
    protected Date likesDate;


}
