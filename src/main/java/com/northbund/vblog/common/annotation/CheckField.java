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
public @interface CheckField {
    //默认是可为空
    boolean isNull() default true;

    //默认为0 不校验长度
    int length() default 0;

    //实例 0-99
    String range() default "MAX";

    String[] enums() default "";
}
