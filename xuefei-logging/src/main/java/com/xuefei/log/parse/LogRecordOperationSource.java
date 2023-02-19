package com.xuefei.log.parse;

import com.xuefei.log.annotation.LogRecord;
import com.xuefei.log.model.LogRecordOps;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;

/**
 * 日志记录操作解析.
 *
 * @version 1.0
 * @author: xuefei
 * @date: 2021/11/2 2:26 下午
 */
public class LogRecordOperationSource {

    public Collection<LogRecordOps> computeLogRecordOperations(Method method, Class<?> targetClass) {
        if (!Modifier.isPublic(method.getModifiers())) {
            return null;
        }

        Method specificMethod = ClassUtils.getMostSpecificMethod(method, targetClass);
        specificMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);

        return parseLogRecordAnnotations(specificMethod);
    }

    private Collection<LogRecordOps> parseLogRecordAnnotations(Method specificMethod) {
        // getAllMergedAnnotations 获取所有的 @LogRecord 注解,包含组合注解
        Collection<LogRecord> logRecordAnnotations = AnnotatedElementUtils.getAllMergedAnnotations(specificMethod, LogRecord.class);
        Collection<LogRecordOps> ret = null;
        if (!logRecordAnnotations.isEmpty()) {
            ret = new ArrayList(1);
            for (LogRecord logRecord : logRecordAnnotations) {
                ret.add(parseLogRecordAnnotation(logRecord));
            }
        }
        return ret;
    }

    private LogRecordOps parseLogRecordAnnotation(LogRecord logRecord) {
        LogRecordOps recordOps = LogRecordOps.builder()
                .appKey(logRecord.appKey())
                .operatorId(logRecord.operator())
                .objName(logRecord.objName())
                .platForm(logRecord.platForm())
                .misId(logRecord.misId())
                .info(logRecord.info())
                .business(logRecord.business())
                .module(logRecord.module())
                .value(logRecord.value())
                .event(logRecord.event())
                .objId(logRecord.objId())
                .build();
        return recordOps;
    }


}
