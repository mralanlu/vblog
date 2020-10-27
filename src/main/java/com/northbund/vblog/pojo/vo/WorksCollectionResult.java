package com.northbund.vblog.pojo.vo;


import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class WorksCollectionResult {
    /**
     * id
     */
    protected Long id;

    /**
     * 用户id
     */
    protected Integer relId;

    /**
     * 作品介绍
     */
    protected String worksIntroduction;

    /**
     * 作品标题
     */
    protected String title;

    protected String phone;

    protected String name;

    protected String address;

    /**
     * 作品状态
     */
    protected Integer worksStatus;

    /**
     * 当前用户点赞状态
     */
    protected Integer currentUserLikesStatus = 0;

    /**
     * 点赞数
     */
    protected Integer likes;

    protected Date createTime;

    protected Long createBy;

    protected Date reviewTime;

    protected Long reviewBy;

    protected List<CommonFileUploadResult> commonFileUploadResults;
}
