package com.xuefei.log.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 线程池配置类
 *
 * @version 1.0
 * @author: xuefei
 * @date: 2021/4/6 10:21 上午
 */
@Data
@Component
@ConfigurationProperties(prefix = "task.pool")
public class AsyncTaskProperties {


    /**
     * 是否开启日志模块线程池,默认关闭线程池
     */
    private Boolean enableLogPool = false;

    /**
     * 默认核心线程数 10
     */
    private int corePoolSize = 10;

    /**
     * 最大线程数 30
     */
    private int maxPoolSize = 30;

    /**
     * 存活时间 60s
     */
    private int keepAliveSeconds = 60;

    /**
     * 队列容量 50
     */
    private int queueCapacity = 50;
}
