package com.xuefei.log.exception;

/**
 * @version 1.0
 * @author: xuefei
 * @date: 2022/2/23 11:38 上午
 */
public class RpcInvokeException extends RuntimeException {
    public RpcInvokeException() {
    }

    public RpcInvokeException(String message) {
        super(message);
    }

    public RpcInvokeException(String message, Throwable cause) {
        super(message, cause);
    }

    public RpcInvokeException(Throwable cause) {
        super(cause);
    }

    protected RpcInvokeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
