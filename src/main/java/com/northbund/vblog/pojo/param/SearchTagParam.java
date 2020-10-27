package com.northbund.vblog.pojo.param;

import lombok.Data;

@Data
public class SearchTagParam {
    /**
     * id
     */
    protected Long id;


    /**
     * 搜索名称
     */
    protected String name;

    protected String searchTag;
}
