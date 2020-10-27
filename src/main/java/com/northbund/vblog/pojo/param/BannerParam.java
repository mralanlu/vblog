package com.northbund.vblog.pojo.param;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.northbund.vblog.pojo.entity.CommonFileUpload;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class BannerParam extends PageParam{
    /**
     * id
     */
    protected Long id;

    /**
     * 链接
     */
    protected String link;

    /**
     * 内容
     */
    protected String content;

    /**
     * 标题
     */
    protected String title;

    protected String address;

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

    /**
     * 展示时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private  Date displayTime;


    protected List<CommonFileUpload> commonFileUploads;
}
