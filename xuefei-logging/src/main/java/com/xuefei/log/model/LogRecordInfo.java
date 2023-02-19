package com.xuefei.log.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 日志记录
 *
 * @version 1.0
 * @author: xuefei
 * @date: 2021/11/2 2:26 下午
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LogRecordInfo {

    private Long id;

    /**
     * appKey
     */
    private String appKey;

    /**
     * Mis账号
     */
    private String misId;

    /**
     * 操作用户ID
     */
    private String operatorId;

    /**
     * 操作内容
     */
    private String info;

    /**
     * 业务
     */
    private String business;

    /**
     * 模块
     */
    private String module;

    /**
     * 方法描述
     */
    private String value;

    /**
     * 方法名称
     */
    private String methodName;

    /**
     * 方法参数
     */
    private String params;

    /**
     * 操作类型
     */
    private String event;

    /**
     * 执行耗时
     */
    private Long consumeTime;

    /**
     * 日志类型：SUCCESS、ERROR
     */
    private String logType;

    /**
     * 异常描述
     */
    private String exceptionDetail;

    /**
     * 对象ID
     */
    private String objId;

    /**
     * 操作对象
     */
    private String objName;

    /**
     * platForm
     */
    private String platForm;


    /**
     * 创建日期
     */
    private Long addTime;

}
