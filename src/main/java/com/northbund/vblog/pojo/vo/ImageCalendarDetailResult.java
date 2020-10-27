package com.northbund.vblog.pojo.vo;


import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ImageCalendarDetailResult {
    /**
     * id
     */
    protected Long id;

    /**
     * 影像日历id
     */
    protected Long imageCalendarId;

    /**
     * 默认标识
     */
    protected Integer defaultFlag;

    /**
     * 介绍
     */
    protected String introduction;

    /**
     * 创建时间
     */
    protected Date createTime;

    /**
     * 更新时间
     */
    protected Date updateTime;

    /**
     * 创建人
     */
    protected Long createBy;

    protected List<CommonFileUploadResult> commonFileUploadResults;
}
