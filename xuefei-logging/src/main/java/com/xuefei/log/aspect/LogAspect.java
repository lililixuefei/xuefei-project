package com.xuefei.log.aspect;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.xuefei.log.context.LogRecordContext;
import com.xuefei.log.model.LogRecordOps;
import com.xuefei.log.model.LogRecordInfo;
import com.xuefei.log.model.MethodExecuteResult;
import com.xuefei.log.parse.LogRecordOperationSource;
import com.xuefei.log.parse.LogRecordValueParser;
import com.xuefei.log.service.OperatorGetService;
import com.xuefei.log.thread.LogRecordPoolHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @version 1.0
 * @author: xuefei
 * @date: 2021/10/12 3:07 下午
 */
@Component
@Aspect
@Slf4j
@RequiredArgsConstructor
public class LogAspect {

    private final LogRecordValueParser logRecordValueParser;

    private final OperatorGetService operatorGetService;

    private final ConfigurableEnvironment environment;

    private final LogRecordOperationSource logRecordOperationSource;

    private static final String INFO = "INFO";
    private static final String ERROR = "ERROR";

    private static final String APP_NAME = "app.name";

    ThreadLocal<Long> currentTime = new ThreadLocal<>();


    /**
     * 配置切入点
     */
    @Pointcut("@annotation(com.xuefei.log.annotation.LogRecord)")
    public void logPointcut() {
        // 该方法无方法体,主要为了让同类中其他方法使用此切入点
    }

    /**
     * 配置环绕通知,使用在方法logPointcut()上注册的切入点
     *
     * @param joinPoint join point for advice
     */
    @Around("logPointcut()")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        Object target = joinPoint.getTarget();
        Class<?> targetClass = AopProxyUtils.ultimateTargetClass(target);
        Object[] args = joinPoint.getArgs();
        LogRecordContext.putEmptySpan();
        Object result = null;
        Collection<LogRecordOps> operations = Lists.newArrayList();
        MethodExecuteResult methodExecuteResult = new MethodExecuteResult(true);

        try {
            // 解析日志注解信息到 LogRecordOps
            operations = logRecordOperationSource.computeLogRecordOperations(method, targetClass);
        } catch (Exception ex) {
            log.error("Log record parse before function exception.", ex);
        }
        try {
            currentTime.set(System.currentTimeMillis());
            result = joinPoint.proceed();
            methodExecuteResult.setConsumeTime(System.currentTimeMillis() - currentTime.get());
        } catch (Exception ex) {
            // 方法执行结果记录异常对象
            methodExecuteResult = new MethodExecuteResult(false, ex, ex.getMessage(), System.currentTimeMillis() - currentTime.get());
        }

        try {
            if (!CollectionUtils.isEmpty(operations)) {
                recordExecute(method, args, operations, targetClass, methodExecuteResult);
            }
        } catch (Exception ex) {
            log.error("Log record parse exception.", ex);
        } finally {
            currentTime.remove();
            LogRecordContext.clear();
        }

        if (methodExecuteResult.getThrowable() != null) {
            throw methodExecuteResult.getThrowable();
        }
        return result;
    }

    /**
     * 记录日志.
     *
     * @param method
     * @param args
     * @param operations
     * @param targetClass
     */
    private void recordExecute(Method method, Object[] args, Collection<LogRecordOps> operations, Class<?> targetClass, MethodExecuteResult methodExecuteResult) {
        for (LogRecordOps operation : operations) {
            try {
                // 获取需要解析的表达式
                List<String> spElTemplates = getSpElTemplates(operation);
                // 获取用户信息
                String operatorIdFromService = getOperatorIdFromServiceAndPutTemplate(operation, spElTemplates);
                // SpEl表达式解析
                Map<String, String> expressionValues = logRecordValueParser.processTemplate(spElTemplates, targetClass, method, args);
                // 构建日志记录信息
                LogRecordInfo logRecordInfo = initLogRecordInfo(method, args, targetClass, methodExecuteResult, operation, operatorIdFromService, expressionValues);
                // 强校验appKey,appKey不填,不给落库
                if (StringUtils.isBlank(logRecordInfo.getAppKey())) {
                    log.error("logRecordInfo appKey must required");
                    return;
                }
                // 日志信息落库
                LogRecordPoolHandler.record(logRecordInfo);
            } catch (Exception ex) {
                log.error("log record execute exception", ex);
            }
        }
    }


    /**
     * 初始化记录信息
     *
     * @param method
     * @param args
     * @param targetClass
     * @param methodExecuteResult
     * @param operation
     * @param operatorIdFromService
     * @param expressionValues
     * @return
     */
    private LogRecordInfo initLogRecordInfo(Method method, Object[] args, Class<?> targetClass, MethodExecuteResult methodExecuteResult, LogRecordOps operation, String operatorIdFromService, Map<String, String> expressionValues) {
        // 获取配置文件的appKey
        String appKey = environment.getProperty(APP_NAME);
        LogRecordInfo logRecordInfo = LogRecordInfo.builder()
                .appKey(expressionValues.get(operation.getAppKey()))
                .misId(expressionValues.get(operation.getMisId()))
                .operatorId(getRealOperatorId(operation, operatorIdFromService, expressionValues))
                .info(expressionValues.get(operation.getInfo()))
                .event(expressionValues.get(operation.getEvent()))
                .objId(expressionValues.get(operation.getObjId()))
                .objName(expressionValues.get(operation.getObjName()))
                .value(expressionValues.get(operation.getValue()))
                .business(expressionValues.get(operation.getBusiness()))
                .module(expressionValues.get(operation.getModule()))
                .platForm(expressionValues.get(operation.getPlatForm()))
                .addTime(System.currentTimeMillis())
                .methodName(targetClass.getName() + "." + method.getName() + "()")
                .logType(INFO)
                .consumeTime(methodExecuteResult.getConsumeTime())
                .appKey(getAppKey(operation, expressionValues, appKey))
                .build();
        // 方法执行结果存在表方法执行异常记录错误信息
        if (!methodExecuteResult.isSuccess()) {
            logRecordInfo.setLogType(ERROR);
            logRecordInfo.setExceptionDetail(methodExecuteResult.getErrorMsg());
        }
        // 方法参数记录
        if (Objects.nonNull(args) && args.length >= 1) {
            logRecordInfo.setParams(JSON.toJSONString(args));
        }
        // LogRecordContext 存储的数据权重最高
        Map<String, Object> variables = LogRecordContext.getVariables();
        if (variables != null && variables.size() > 0) {
            // LogRecordContext存储的值赋给日志记录对象
            objectAttributeAssignment(logRecordInfo, variables);
        }
        return logRecordInfo;
    }


    private void objectAttributeAssignment(Object object, Map<String, Object> variables) {
        if (Objects.isNull(object)) {
            log.error("objectAttributeAssignment object is null");
            return;
        }
        if (variables == null || variables.size() == 0) {
            log.error("objectAttributeAssignment variables is empty");
            return;
        }
        try {
            Class<?> objectClass = object.getClass();
            Field[] declaredFields = objectClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                declaredField.setAccessible(true);
                Class<?> type = declaredField.getType();
                String fieldName = declaredField.getName();
                Object value = variables.get(fieldName);
                if (Objects.isNull(value)) {
                    continue;
                }
                //将属性的首字符大写，方便构造set方法
                fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
                Method method = objectClass.getMethod("set" + fieldName, type);
                method.invoke(object, value);
            }
        } catch (Exception e) {
            log.error("objectAttributeAssignment exception", e);
        }
    }


    private String getRealOperatorId(LogRecordOps operation, String operatorIdFromService, Map<String, String> expressionValues) {
        return !StringUtils.isBlank(operatorIdFromService) ? operatorIdFromService : expressionValues.get(operation.getOperatorId());
    }

    private String getAppKey(LogRecordOps operation, Map<String, String> expressionValues, String appKey) {
        return !StringUtils.isBlank(expressionValues.get(operation.getAppKey())) ? expressionValues.get(operation.getAppKey()) : appKey;

    }


    private String getOperatorIdFromServiceAndPutTemplate(LogRecordOps operation, List<String> spElTemplates) {
        String realOperatorId = "";
        if (StringUtils.isEmpty(operation.getOperatorId())) {
            realOperatorId = operatorGetService.getUser().getOperatorId();
            if (StringUtils.isEmpty(realOperatorId)) {
                throw new IllegalArgumentException("LogRecord operator is null");
            }
        } else {
            spElTemplates.add(operation.getOperatorId());
        }

        return realOperatorId;
    }

    private List<String> getSpElTemplates(LogRecordOps operation) {
        return Lists.newArrayList(
                operation.getAppKey(),
                operation.getInfo(),
                operation.getOperatorId(),
                operation.getMisId(),
                operation.getEvent(),
                operation.getModule(),
                operation.getBusiness(),
                operation.getObjId(),
                operation.getObjName(),
                operation.getValue(),
                operation.getPlatForm());
    }
}
