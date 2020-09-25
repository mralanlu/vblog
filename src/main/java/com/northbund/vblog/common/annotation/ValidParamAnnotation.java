package com.northbund.vblog.common.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.PARAMETER)
public @interface ValidParamAnnotation {

}
