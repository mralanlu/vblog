package com.northbund.vblog.common.annotation;

import java.lang.annotation.*;

/**
 * @program: pms-manage
 * @description: ""
 * @author: guosheng
 * @create: 2020-03-12 14:06
 **/
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckEntity {

    //默认是可为空
    boolean isNull() default true;

    //是否递归
    boolean isRecursion() default false;
}
