package com.northbund.vblog.pojo.param;

import lombok.Data;

/**
 * @author: lk
 **/
@Data
public class PageParam {

    Integer pageNum=1;

    Integer pageSize=10;

    /**
     * 字段
     */
    String orderBy="";

}
