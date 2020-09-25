package com.northbund.vblog.common.annotation;

import java.lang.annotation.*;

/**
 * Created by D1M on 2018/6/8.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface PagingQuery {
    String pageNumParameterName() default "pageNum";//页号的参数名
    String pageSizeParameterName() default "pageSize";//每页行数的参数名
    String orderByParameterName() default "orderBy";//每页行数的参数名
}
