package com.northbund.vblog.pojo.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author Alan Lu
 */
@Data
public class CommonFileUploadParam {

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

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date uploadAt;



}

