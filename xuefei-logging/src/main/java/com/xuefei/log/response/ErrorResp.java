package com.xuefei.log.response;

/**
 * @version 1.0
 * @author: xuefei
 * @date: 2021/4/15 7:15 下午
 */
public class ErrorResp {
    private Integer code;
    private String message;
    private String traceInfo;

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setTraceInfo(String traceInfo) {
        this.traceInfo = traceInfo;
    }

    public String getTraceInfo() {
        return this.traceInfo;
    }

}
