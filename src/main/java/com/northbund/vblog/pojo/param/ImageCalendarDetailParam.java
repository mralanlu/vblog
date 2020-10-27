package com.northbund.vblog.pojo.param;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.northbund.vblog.pojo.entity.CommonFileUpload;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ImageCalendarDetailParam {
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected Date updateTime;

    /**
     * 创建人
     */
    protected Long createBy;

    protected List<CommonFileUpload> commonFileUploads;
}
