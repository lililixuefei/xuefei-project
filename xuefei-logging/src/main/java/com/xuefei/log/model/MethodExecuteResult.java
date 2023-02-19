package com.xuefei.log.model;

import lombok.*;

/**
 * 方法执行结果.
 *
 * @version 1.0
 * @author: xuefei
 * @date: 2021/11/2 2:26 下午
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@RequiredArgsConstructor
public class MethodExecuteResult {

    /**
     * 是否成功
     */
    @NonNull
    private boolean success;

    /**
     * 异常
     */
    private Throwable throwable;

    /**
     * 错误日志
     */
    private String errorMsg;

    /**
     * 执行耗时
     */
    private Long consumeTime;

}
