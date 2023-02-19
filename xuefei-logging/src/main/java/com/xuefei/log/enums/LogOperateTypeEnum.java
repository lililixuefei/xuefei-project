package com.xuefei.log.enums;

import com.google.common.collect.Maps;

import java.util.Map;

/**
 * 日志操作类型
 *
 * @version 1.0
 * @author: xuefei
 * @date: 2022/3/21 2:12 下午
 */
public enum LogOperateTypeEnum {

    /**
     * 查询
     */
    QUERY(0, "查询"),
    /**
     * 新增
     */
    ADD(1, "新增"),
    /**
     * 删除
     */
    DELETE(2, "删除"),
    /**
     * 更新
     */
    UPDATE(3, "更新"),
    ;

    private static final Map<Integer, LogOperateTypeEnum> codeMap = Maps.newHashMap();

    static {
        for (LogOperateTypeEnum type : LogOperateTypeEnum.values()) {
            codeMap.put(type.getCode(), type);
        }
    }

    private final Integer code;
    private final String message;

    LogOperateTypeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public static LogOperateTypeEnum getByCode(Integer code) {
        return codeMap.get(code);
    }

    public Integer getCode() {
        return code;
    }

    public Byte getByte() {
        return Byte.parseByte(code.toString());
    }

    public String getMessage() {
        return message;
    }
}
