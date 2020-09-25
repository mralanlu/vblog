package com.northbund.vblog.common.annotation;

import java.lang.annotation.*;

/**
 * @program: pms-manage
 * @description: ""
 * @author: guosheng
 * @create: 2020-03-12 14:06
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CheckParam {

    //包含
    String include() default "";

    //排除
    String exclude() default "";

}
