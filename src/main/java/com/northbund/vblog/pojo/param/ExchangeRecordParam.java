package com.northbund.vblog.pojo.param;


import lombok.Data;

import java.util.Date;

@Data
public class ExchangeRecordParam extends PageParam{
    /**
     * id
     */
    protected Long id;

    /**
     * 用户id
     */
    protected Long userId;

    /**
     * 积分商品id
     */
    protected Long scoreGoodsId;

    /**
     * 兑换时间
     */
    protected Date exchangeTime;


    protected Date startAt;
    protected Date endAt;
    /**
     * 积分商品所需积分
     */
    protected Integer realScore;

}
