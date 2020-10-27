package com.northbund.vblog.pojo.param;

import lombok.Data;

import java.util.Date;

@Data
public class WorksLikesRecordParam{
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
     * 点赞状态 0 取消点赞 1 点赞
     */
    protected Integer clickStatus;

    protected Date likesDate;
}
