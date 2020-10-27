package com.northbund.vblog.pojo.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.northbund.vblog.pojo.entity.CommonFileUpload;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ImageCalendarParam extends PageParam{
    /**
     * id
     */
    protected Long id;

    /**
     * 展示时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    protected Date displayTime;

    /**
     * 主题
     */
    protected String title;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected Date createTime;

    /**
     * 创建人
     */
    protected Long createBy;


    protected Integer year;

    protected Integer month;

    protected Integer day;

    protected String yearDescribe;

    protected String introduction;

    protected Date endAt;

    /**
     * 海报
     */
    protected List<CommonFileUpload> poster;

    /**
     * 缩略图
     */
    protected List<CommonFileUpload> thumbnail;

}
