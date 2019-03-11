package com.jiutai.commonlib.ioc.annotation;

import android.text.TextUtils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

public class ListenerInvocationHanlder implements InvocationHandler {
    private Object target;
    private HashMap<String, Method> cache = new HashMap<>();

    public ListenerInvocationHanlder(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String name = method.getName();
        if (!TextUtils.isEmpty(name)) {
            method = cache.get(name);
            if (null != method) {
                method.setAccessible(true);
                return method.invoke(target, args);
            }
        }
        return null;
    }

    public void add(String methodName, Method method) {
        cache.put(methodName, method);
    }
}
