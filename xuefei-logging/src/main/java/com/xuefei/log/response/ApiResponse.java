package com.xuefei.log.response;


import com.alibaba.fastjson.JSON;

import java.io.Serializable;

/**
 * @version 1.0
 * @author: xuefei
 * @date: 2021/4/15 7:14 下午
 */
public class ApiResponse<T> implements Serializable {
    private static final long serialVersionUID = -2465140747749709626L;
    private boolean success;
    private T data;
    private ErrorResp error;
    private PagingResp paging;

    public ApiResponse() {
    }

    public static <T> ApiResponse<T> buildSuccess() {
        ApiResponse<T> response = new ApiResponse();
        response.setSuccess(true);
        return response;
    }

    public static <T> ApiResponse<T> buildSuccess(T date) {
        ApiResponse<T> response = new ApiResponse();
        response.setData(date);
        response.setSuccess(true);
        return response;
    }

    public static <T> ApiResponse<T> buildSuccess(T date, Boolean hasMore) {
        ApiResponse<T> response = new ApiResponse();
        response.setSuccess(true);
        response.setData(date);
        PagingResp paging = new PagingResp();
        paging.setHasMore(hasMore);
        paging.setTimestamp(System.currentTimeMillis());
        response.setPaging(paging);
        return response;
    }

    public static <T> ApiResponse<T> buildSuccess(T date, PagingResp paging) {
        ApiResponse<T> response = new ApiResponse();
        response.setSuccess(true);
        response.setData(date);
        response.setPaging(paging);
        return response;
    }

    public static <T> ApiResponse<T> buildFailure() {
        ApiResponse<T> response = new ApiResponse();
        response.setSuccess(false);
        return response;
    }

    public static <T> ApiResponse<T> buildFailure(String errorMsg) {
        ApiResponse<T> response = new ApiResponse();
        response.setSuccess(false);
        ErrorResp error = new ErrorResp();
        error.setMessage(errorMsg);
        response.setError(error);
        return response;
    }

    public static <T> ApiResponse<T> buildFailure(int errorCode, String errorMsg) {
        ApiResponse<T> response = new ApiResponse();
        response.setSuccess(false);
        ErrorResp error = new ErrorResp();
        error.setCode(errorCode);
        error.setMessage(errorMsg);
        response.setError(error);
        return response;
    }

    public static <T> ApiResponse<T> buildFailure(int errorCode, String errorMsg, String traceInfo) {
        ApiResponse<T> response = new ApiResponse();
        response.setSuccess(false);
        ErrorResp error = new ErrorResp();
        error.setCode(errorCode);
        error.setMessage(errorMsg);
        error.setTraceInfo(traceInfo);
        response.setError(error);
        return response;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ErrorResp getError() {
        return this.error;
    }

    public void setError(ErrorResp error) {
        this.error = error;
    }

    public PagingResp getPaging() {
        return this.paging;
    }

    public void setPaging(PagingResp paging) {
        this.paging = paging;
    }

    public String toJson() {
        return JSON.toJSONString(this);
    }

}

