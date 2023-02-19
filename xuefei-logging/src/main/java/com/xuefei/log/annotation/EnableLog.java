package com.xuefei.log.annotation;

import com.xuefei.log.config.LogModuleConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @description: 开启日志注解
 * @author: xuefei
 * @date: 2022/05/26 14:01
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(LogModuleConfig.class)
public @interface EnableLog {
}
