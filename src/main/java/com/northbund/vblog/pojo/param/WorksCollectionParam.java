package com.northbund.vblog.pojo.param;


import com.northbund.vblog.pojo.entity.CommonFileUpload;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class WorksCollectionParam extends PageParam{
    /**
     * id
     */
    protected Long id;

    /**
     * 用户id
     */
    protected Long userId;

    /**
     * 当前用户id（查询点赞记录用）
     */
    protected Long currentUserId;

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
     * 点赞数
     */
    protected Integer likes;

    protected Date createTime;

    protected Long createBy;

    protected Date reviewTime;

    protected Long reviewBy;

    private String verifyCode;

    protected List<CommonFileUpload> commonFileUploads;
}
