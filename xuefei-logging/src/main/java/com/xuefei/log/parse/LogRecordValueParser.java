package com.xuefei.log.parse;

import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.expression.AnnotatedElementKey;
import org.springframework.expression.EvaluationContext;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Map;

/**
 * Log record value parser
 *
 * @version 1.0
 * @author: xuefei
 * @date: 2021/11/2 2:26 下午
 */
public class LogRecordValueParser {


    private final LogRecordExpressionEvaluator expressionEvaluator = new LogRecordExpressionEvaluator();


    public Map<String, String> processTemplate(Collection<String> templates, Class<?> targetClass, Method method, Object[] args) {
        Map<String, String> expressionValues = Maps.newHashMap();
        EvaluationContext evaluationContext = expressionEvaluator.createEvaluationContext(method, args, targetClass);
        for (String expressionTemplate : templates) {
            if (StringUtils.isBlank(expressionTemplate)) {
                continue;
            }
            if (expressionTemplate.contains("#") || expressionTemplate.contains("(")) {
                AnnotatedElementKey annotatedElementKey = new AnnotatedElementKey(method, targetClass);
                String value = expressionEvaluator.parseExpression(expressionTemplate, annotatedElementKey, evaluationContext);
                expressionValues.put(expressionTemplate, value);
            } else {
                expressionValues.put(expressionTemplate, expressionTemplate);
            }
        }
        return expressionValues;
    }
}
