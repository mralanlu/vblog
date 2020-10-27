package com.northbund.vblog.pojo.entity;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.northbund.vblog.utils.handler.JsonDatetimeFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(name="exchange_record")
public class ExchangeRecord {
    /**
     * id
     */
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    protected Long id;

    /**
     * 用户id
     */
    @Column(name="user_id")
    protected Long userId;

    /**
     * 积分商品id
     */
    @Column(name="score_goods_id")
    protected Long scoreGoodsId;

    /**
     * 兑换时间
     */
    @JsonSerialize(using = JsonDatetimeFormat.class)
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column(name="exchange_time")
    protected Date exchangeTime;
}
