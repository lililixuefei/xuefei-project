package com.xuefei.log.context;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * @version 1.0
 * @author: xuefei
 * @date: 2022/2/15 10:33 上午
 */
public class LogRecordContext {

    private static final InheritableThreadLocal<Stack<Map<String, Object>>> variableMapStack = new InheritableThreadLocal<>();

    public static void putVariable(String name, String value) {
        if (variableMapStack.get() == null) {
            Stack<Map<String, Object>> stack = new Stack<>();
            variableMapStack.set(stack);
        }
        Stack<Map<String, Object>> mapStack = variableMapStack.get();
        if (mapStack.size() == 0) {
            variableMapStack.get().push(new HashMap<>());
        }
        variableMapStack.get().peek().put(name, value);
    }

    public static Object getVariable(String key) {
        Map<String, Object> variableMap = variableMapStack.get().peek();
        return variableMap.get(key);
    }

    public static Map<String, Object> getVariables() {
        Stack<Map<String, Object>> mapStack = variableMapStack.get();
        return mapStack.peek();
    }

    public static void clear() {
        if (variableMapStack.get() != null) {
            variableMapStack.get().pop();
        }
    }

    /**
     * 日志使用方不需要使用到这个方法
     * 每进入一个方法初始化一个 span 放入到 stack中，方法执行完后 pop 掉这个span
     */
    public static void putEmptySpan() {
        Stack<Map<String, Object>> mapStack = variableMapStack.get();
        if (mapStack == null) {
            Stack<Map<String, Object>> stack = new Stack<>();
            variableMapStack.set(stack);
        }
        variableMapStack.get().push(new HashMap());

    }
}
