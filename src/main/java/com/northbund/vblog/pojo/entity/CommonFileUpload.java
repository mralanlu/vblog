package com.northbund.vblog.pojo.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.northbund.vblog.utils.handler.JsonDatetimeFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author Alan Lu
 */
@Entity
@Data
@Table(name="common_file_upload")
public class CommonFileUpload implements Serializable {

    /**
     * 文件id
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
	protected Long id;

    /**
     * 关联id
     */ 
    @Column(name="rel_id")
	protected Long relId;

    /**
     * 类型
     */ 
    @Column(name="attachment_type")
	protected Integer attachmentType;

    /**
     * 原始文件名称
     */ 
    @Column(name="original_file_name")
	protected String originalFileName;

    @Column(name="file_path")
	protected String filePath;

    @Column(name="url")
    protected String url;

    @Column(name="upload_at")
    @JsonSerialize(using = JsonDatetimeFormat.class)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    protected Date uploadAt;



}

