package com.xuefei.log.service;


import com.xuefei.log.model.Operator;

/**
 * 获取操作用户
 *
 * @version 1.0
 * @author: xuefei
 * @date: 2022/2/15 5:39 下午
 */
public interface OperatorGetService {

    /**
     * 获取操作人.
     *
     * @return
     */
    Operator getUser();

}
