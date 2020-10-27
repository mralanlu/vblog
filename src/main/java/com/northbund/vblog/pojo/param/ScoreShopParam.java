package com.northbund.vblog.pojo.param;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.northbund.vblog.pojo.entity.CommonFileUpload;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ScoreShopParam extends PageParam{
    /**
     * id
     */
    protected Long id;

    /**
     * 积分商品名
     */
    protected String scoreGoodsName;

    /**
     * 积分商品介绍
     */
    protected String introduction;

    /**
     * 积分商品上架状态 0 下架  1 上架
     */
    protected Integer scoreGoodsStatus;

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
     * 所需积分
     */
    protected Integer score;

    /**
     * 折扣积分
     */
    protected Integer discount;

    /**
     * 规格
     */
    protected String specifications;

    /**
     * 材质
     */
    protected String material;

    /**
     * 品牌
     */
    protected String brand;

    protected List<CommonFileUpload> commonFileUploads;
}
