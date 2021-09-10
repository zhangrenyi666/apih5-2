package com.apih5.annotation;

import java.lang.annotation.*;

/**
 * 检验唯一性
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckOnlyPoint {
    /**
     * 对象全路径
     *
     * @return
     */
    String objName() default "";

    /**
     * 条件
     *
     * @return
     */
    String condStr();
    /**
     * 提示语
     *
     * @return
     */
    String message();
}