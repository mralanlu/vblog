package com.northbund.vblog.pojo.vo;

import lombok.Data;

import java.util.Date;

@Data
public class WorksLikesRecordResult {
    /**
     * id
     */
    protected Long id;

    /**
     * 用户id
     */
    protected Long userId;

    /**
     * 作品id
     */
    protected Long worksId;

    /**
     * 点赞状态
     */
    protected Integer clickStatus;

    protected Date likesDate;
}
