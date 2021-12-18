package com.lorin.aop;

import java.lang.annotation.*;
/**
 * 日志注解
 * @author lorin
 * @date 2021/12/17 8:42
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAnnotation {

    String module() default "";

    String operation() default "";
}
