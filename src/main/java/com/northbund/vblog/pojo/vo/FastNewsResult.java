package com.northbund.vblog.pojo.vo;


import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class FastNewsResult {
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
    protected Date createTime;

    /**
     * 更新时间
     */
    protected Date updateTime;

    /**
     * 创建人
     */
    protected Long createBy;

    /**
     * 展示时间
     */
    private  Date displayTime;

    protected List<CommonFileUploadResult> commonFileUploadResults;
}
