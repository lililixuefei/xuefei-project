package com.xuefei.log.service.impl;

import com.alibaba.fastjson.JSON;
import com.xuefei.log.model.LogRecordInfo;
import com.xuefei.log.service.ILogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @author: xuefei
 * @date: 2021/3/19 9:54 上午
 */
@Slf4j
@Service(value = "logService")
public class LogServiceImpl implements ILogService {


    @Value(value = "${app.name}")
    private String appKey;

    /**
     * 兼容内容平台查询条件
     */
    private static final String CONTENT_API_APPKEY = "com.sankuai.newbusiness.content.api";


    @Override
    public void recordLog(LogRecordInfo logRecordInfo) {
        log.info("LogServiceImpl.recordLog logRecordInfo = {}",JSON.toJSONString(logRecordInfo));
    }
}
