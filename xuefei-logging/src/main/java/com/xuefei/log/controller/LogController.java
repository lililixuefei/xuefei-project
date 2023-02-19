package com.xuefei.log.controller;

import com.xuefei.log.service.ILogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;


/**
 * @version 1.0
 * @author: xuefei
 * @date: 2021/3/17 5:41 下午
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/logs")
public class LogController {

    @Resource
    private final ILogService logService;


}


