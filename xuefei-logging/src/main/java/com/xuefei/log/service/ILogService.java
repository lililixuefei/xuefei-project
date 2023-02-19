package com.xuefei.log.service;


import com.xuefei.log.model.LogRecordInfo;

/**
 * @version 1.0
 * @author: xuefei
 * @date: 2021/3/17 4:57 下午
 */
public interface ILogService {


    /**
     * 保存日志
     *
     * @param logRecordInfo 日志信息
     */
    void recordLog(LogRecordInfo logRecordInfo);





}
