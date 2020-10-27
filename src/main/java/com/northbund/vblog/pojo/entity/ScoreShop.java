package com.northbund.vblog.pojo.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.northbund.vblog.utils.handler.JsonDatetimeFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="score_shop")
public class ScoreShop  {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    protected Long id;

    /**
     * 积分商品名
     */
    @Column(name="score_goods_name")
    protected String scoreGoodsName;

    /**
     * 积分商品介绍
     */
    @Column(name="introduction")
    protected String introduction;

    /**
     * 积分商品上架状态
     */
    @Column(name="score_goods_status")
    protected Integer scoreGoodsStatus;

    /**
     * 创建时间
     */
    @JsonSerialize(using = JsonDatetimeFormat.class)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name="create_time")
    protected Date createTime;

    /**
     * 更新时间
     */
    @JsonSerialize(using = JsonDatetimeFormat.class)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name="update_time")
    protected Date updateTime;

    /**
     * 创建人
     */
    @Column(name="create_by")
    protected Long createBy;

    /**
     * 所需积分
     */
    @Column(name="score")
    protected Integer score;

    /**
     * 折扣积分
     */
    @Column(name="discount")
    protected Integer discount;

    /**
     * 规格
     */
    @Column(name="specifications")
    protected String specifications;

    /**
     * 材质
     */
    @Column(name="material")
    protected String material;

    /**
     * 品牌
     */
    @Column(name="brand")
    protected String brand;

}
