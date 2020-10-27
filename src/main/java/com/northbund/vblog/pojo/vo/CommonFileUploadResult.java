package com.northbund.vblog.pojo.vo;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.northbund.vblog.utils.handler.JsonDatetimeFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author Alan Lu
 */
@Data
public class CommonFileUploadResult {

    /**
     * 文件id
     */
	protected Long id;

    /**
     * 关联id
     */
	protected Long relId;

    /**
     * 类型：1 采购 2 合同 3 发票 4 供应商
     */
	protected Integer attachmentType;

    /**
     * 原始文件名称
     */
	protected String originalFileName;

	protected String filePath;

    protected String url;

    @JsonSerialize(using = JsonDatetimeFormat.class)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    protected Date uploadAt;



}

