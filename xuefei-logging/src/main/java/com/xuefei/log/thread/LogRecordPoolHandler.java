package com.xuefei.log.thread;

import com.xuefei.log.config.AsyncTaskProperties;
import com.xuefei.log.model.LogRecordInfo;
import com.xuefei.log.service.ILogService;
import com.xuefei.log.util.ApplicationContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @version 1.0
 * @author: xuefei
 * @date: 2022/3/3 8:45 下午
 */
public class LogRecordPoolHandler {

    private static final Logger logger = LoggerFactory.getLogger(LogRecordPoolHandler.class);

    private ILogService logService;

    /**
     * 线程池
     */
    private ThreadPoolExecutor logRecordPool = null;

    public void start(AsyncTaskProperties config) {
        // 支持同步和异步记录日志的方式,用户可配置是否开启线程池,也可以自定义配置线程池
        logRecordPool = new ThreadPoolExecutor(
                config.getCorePoolSize(),
                config.getMaxPoolSize(),
                config.getKeepAliveSeconds(),
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(config.getQueueCapacity()),
                new ThreadFactory() {
                    @Override
                    public Thread newThread(Runnable r) {
                        return new Thread(r, "LogRecordPoolHelper,logRecordPool-" + r.hashCode());
                    }
                });
    }


    public void stop() {
        if (Objects.isNull(logRecordPool)) {
            return;
        }
        logRecordPool.shutdownNow();
        logger.info("LogRecordPoolHelper thread pool shutdown success.");
    }

    /**
     * record log
     */
    public void recordLog(LogRecordInfo logRecordInfo) {

        logService = (ILogService) ApplicationContextUtil.getBean("logService");

        // logRecordPool is null means pool is off
        if (logRecordPool == null) {
            // record log
            logService.recordLog(logRecordInfo);
            return;
        }
        // thread pool execute record
        logRecordPool.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    // record log
                    logService.recordLog(logRecordInfo);
                } catch (Exception e) {
                    logger.error(e.getMessage(), e);
                }
            }
        });
    }

    private static LogRecordPoolHandler helper = new LogRecordPoolHandler();

    public static void toStart(AsyncTaskProperties config) {
        helper.start(config);
    }

    public static void toStop() {
        helper.stop();
    }


    public static void record(LogRecordInfo logRecordInfo) {
        helper.recordLog(logRecordInfo);
    }
}
