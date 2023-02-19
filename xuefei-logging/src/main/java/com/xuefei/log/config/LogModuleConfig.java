package com.xuefei.log.config;

import com.xuefei.log.parse.LogRecordOperationSource;
import com.xuefei.log.parse.LogRecordValueParser;
import com.xuefei.log.service.OperatorGetService;
import com.xuefei.log.service.impl.DefaultOperatorGetServiceImpl;
import com.xuefei.log.thread.LogRecordPoolHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.*;

/**
 * 日志功能配置类
 *
 * @version 1.0
 * @author: xuefei
 * @date: 2021/3/31 10:53 上午
 */
@Slf4j
@Configuration
@ComponentScan({"com.xuefei.log"})
public class LogModuleConfig implements InitializingBean, DisposableBean {

    /**
     * 注入配置类
     */
    private final AsyncTaskProperties config;

    public LogModuleConfig(AsyncTaskProperties config) {
        this.config = config;
    }


    @Override
    public void afterPropertiesSet() {
        System.out.println("xuefei-logging enableLogPool = "+config.getEnableLogPool());
        log.info("xuefei-logging enableLogPool = {}",config.getEnableLogPool());
        // 用户配置开启日志模块线程池
        if (config.getEnableLogPool()) {
            LogRecordPoolHandler.toStart(config);
        }
    }

    @Override
    public void destroy() {
        LogRecordPoolHandler.toStop();
    }

    @Bean
    @Role(BeanDefinition.ROLE_APPLICATION)
    @ConditionalOnMissingBean(OperatorGetService.class)
    public OperatorGetService operatorGetService() {
        return new DefaultOperatorGetServiceImpl();
    }

    @Bean
    public LogRecordValueParser logRecordValueParser() {
        return new LogRecordValueParser();
    }

    @Bean
    public LogRecordOperationSource logRecordOperationSource() {
        return new LogRecordOperationSource();
    }

}