package com.xuefei.log.parse;

import com.google.common.collect.Maps;
import com.xuefei.log.util.ApplicationContextUtil;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.expression.*;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Log record expression evaluator.
 *
 * @version 1.0
 * @author: xuefei
 * @date: 2021/11/2 2:26 下午
 */
@Service
public class LogRecordExpressionEvaluator extends CachedExpressionEvaluator {


    private Map<ExpressionKey, Expression> expressionCache = Maps.newConcurrentMap();

    private final Map<AnnotatedElementKey, Method> targetMethodCache = Maps.newConcurrentMap();

    public String parseExpression(String conditionExpression, AnnotatedElementKey methodKey, EvaluationContext evalContext) {
        Object value = getExpression(this.expressionCache, methodKey, conditionExpression).getValue(evalContext, Object.class);
        return value == null ? "" : value.toString();
    }

    public EvaluationContext createEvaluationContext(Method method, Object[] args, Class<?> targetClass) {
        Method targetMethod = getTargetMethod(targetClass, method);
        // 将ioc容器设置到上下文中
        ApplicationContext applicationContext = ApplicationContextUtil.getApplicationContext();
        MethodBasedEvaluationContext evaluationContext = new MethodBasedEvaluationContext(applicationContext, targetMethod, args, getParameterNameDiscoverer());
        evaluationContext.addPropertyAccessor(new BeanFactoryAccessor());
        return evaluationContext;
    }

    private Method getTargetMethod(Class<?> targetClass, Method method) {
        AnnotatedElementKey methodKey = new AnnotatedElementKey(method, targetClass);
        Method targetMethod = this.targetMethodCache.get(methodKey);
        if (targetMethod == null) {
            targetMethod = AopUtils.getMostSpecificMethod(method, targetClass);
            this.targetMethodCache.put(methodKey, targetMethod);
        }
        return targetMethod;
    }

}
