package com.xuefei.log.service.impl;

import com.xuefei.log.model.Operator;
import com.xuefei.log.service.OperatorGetService;

/**
 * 默认实现
 *
 * @version 1.0
 * @author: xuefei
 * @date: 2022/2/15 5:40 下午
 */
public class DefaultOperatorGetServiceImpl implements OperatorGetService {

    @Override
    public Operator getUser() {
        return new Operator("-");
    }
}
