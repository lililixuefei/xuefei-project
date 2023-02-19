package com.xuefei.log.model;

import lombok.Data;

import java.io.Serializable;


/**
 * 日志查询
 *
 * @version 1.0
 * @author: xuefei
 * @date: 2021/3/18 9:59 上午
 */
@Data
public class LogQueryCriteria implements Serializable {

    /**
     * mis账号
     */
    private String misId;

    /**
     * appKey
     */
    private String appKey;

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
     * 操作者用户Id
     */
    private String operatorId;

    /**
     * 操作内容集合
     */
    private String events;

    /**
     * 日志类型
     */
    private String logType;

    /**
     * 开始时间
     */
    private Long startTime;

    /**
     * 结束时间
     */
    private Long endTime;

    private Integer offset;

    private Integer limit;

    /**
     * 对象Id
     */
    private String objId;

    /**
     * 对象名称
     */
    private String objName;

    /**
     * 操作页面
     */
    private String operatePlatforms;

}
