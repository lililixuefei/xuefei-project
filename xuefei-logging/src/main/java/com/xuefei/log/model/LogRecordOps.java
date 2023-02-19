package com.xuefei.log.model;

import lombok.Builder;
import lombok.Data;

/**
 * 日志操作记录.
 *
 * @version 1.0
 * @author: xuefei
 * @date: 2021/11/2 2:26 下午
 */
@Data
@Builder
public class LogRecordOps {

    /**
     * Mis账号
     */
    private String misId;

    /**
     * 业务
     */
    private String business;

    /**
     * 模块
     */
    private String module;

    /**
     * 操作内容
     */
    private String info;

    /**
     * 操作者ID
     */
    private String operatorId;

    /**
     * appKey
     */
    private String appKey;

    /**
     * 操作类型
     */
    private String event;

    /**
     * 方法描述
     */
    private String value;

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

}
