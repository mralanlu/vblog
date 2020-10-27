package com.northbund.vblog.pojo.vo;


import lombok.Data;

import java.util.Date;

@Data
public class ExchangeRecordResult {
    /**
     * id
     */
    protected Integer id;

    /**
     * 用户id
     */
    protected Integer userId;

    protected String userName;

    /**
     * 积分商品id
     */
    protected Long scoreGoodsId;

    protected String scoreGoodsName;



    /**
     * 兑换时间
     */
    protected Date exchangeTime;
}
