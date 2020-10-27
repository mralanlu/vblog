package com.northbund.vblog.pojo.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ImageCalendarResult {
    /**
     * id
     */
    protected Long id;

    /**
     * 时间
     */
    protected Date displayTime;

    /**
     * 主题
     */
    protected String title;

    /**
     * 创建时间
     */
    protected Date createTime;

    /**
     * 创建人
     */
    protected Long createBy;

    protected Date updateTime;

    protected Integer year;

    protected Integer month;

    protected Integer day;

    protected String yearDescribe;

    protected String introduction;

    /**
     * 海报
     */
    protected List<CommonFileUploadResult> posterResults;

    /**
     * 缩略图
     */
    protected List<CommonFileUploadResult> thumbnailResults;

}
