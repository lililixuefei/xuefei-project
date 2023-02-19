package com.xuefei.log.annotation;


import java.lang.annotation.*;

/**
 * 日志记录注解.
 *
 * @author xuefei
 * @date 2021/10/23 21:29
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogRecord {

    /**
     * 操作人
     */
    String operator() default "";

    /**
     * 方法描述
     */
    String value() default "";

    /**
     * 操作内容
     */
    String info() default "";

    /**
     * 业务
     */
    String business() default "";

    /**
     * 模块
     */
    String module() default "";

    /**
     * 操作类型
     */
    String event() default "";

    /**
     * appKey
     *
     * @return
     */
    String appKey() default "";

    /**
     * 对象ID
     */
    String objId() default "";

    // -------以下字段兼容内容平台日志记录字段，如果您已经使用了以下字段暂时可以继续使用,但是建议替换以上通用字段，后期可能会考虑去除下面字段--------

    /**
     * misId
     */
    String misId() default "";

    /**
     * platForm
     */
    String platForm() default "";

    /**
     * 对象名称
     */
    String objName() default "";

}
