package com.jiutai.commonlib.eventbus;

import java.lang.reflect.Method;

public class SubscribeMethod {
    private Class<?> type;//参数类型
    private Method method;
    private ThreadMode threadMode;

    public SubscribeMethod(Class<?> type, Method method, ThreadMode threadMode) {
        this.type = type;
        this.method = method;
        this.threadMode = threadMode;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public ThreadMode getThreadMode() {
        return threadMode;
    }

    public void setThreadMode(ThreadMode threadMode) {
        this.threadMode = threadMode;
    }
}
