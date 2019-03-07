package com.jiutai.commonlib.eventbus;

import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class EventBus {
    private static volatile EventBus instance;
    private Map<Object, List<SubscribeMethod>> cache = new HashMap<>();
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private Executor executor = Executors.newCachedThreadPool();

    public static EventBus getDefault() {
        if (null == instance) {
            synchronized (EventBus.class) {
                if (null == instance) {
                    instance = new EventBus();
                }
            }
        }
        return instance;
    }

    public void register(Object obj) {
        if (null == obj) throw new NullPointerException("obj can not null!");
        List<SubscribeMethod> beans = cache.get(obj);
        if (null == beans) {
            beans = findSubscribeMethod(obj);
            if (!beans.isEmpty()) {
                cache.put(obj, beans);
            }
        }
    }

    private List<SubscribeMethod> findSubscribeMethod(Object obj) {
        if (null == obj) return null;
        List<SubscribeMethod> list = new ArrayList<>();
        Class<?> clazz = obj.getClass();
        while (clazz != null) {
            //判断当前是否是系统类，如果是，退出循环
            String name = clazz.getName();
            if (name.startsWith("java.") || name.startsWith("javax.") || name.startsWith("android.")) {
                break;
            }
            Method[] methods = clazz.getMethods();
            if (null != methods && methods.length > 0) {
                for (Method method : methods) {
                    if (null != method) {
                        Subscribe annotation = method.getAnnotation(Subscribe.class);
                        if (null != annotation) {
                            Class<?>[] parameterTypes = method.getParameterTypes();
                            if (null != parameterTypes && parameterTypes.length == 1) {
                                list.add(new SubscribeMethod(parameterTypes[0], method, annotation.threadMode()));
                            }
                        }
                    }
                }
            }
            clazz = clazz.getSuperclass();
        }
        return list;
    }

    public void post(final Object obj) {
        if (cache.isEmpty()) return;
        for (final Object key : cache.keySet()) {
            List<SubscribeMethod> beans = cache.get(key);
            if (null != beans) {
                for (SubscribeMethod bean : beans) {
                    //bean的类信息是obj类信息的同类或父类
                    if (null != bean && bean.getType().isAssignableFrom(obj.getClass())) {
                        final Method method = bean.getMethod();
                        if (null != method) {
                            ThreadMode threadMode = bean.getThreadMode();
                            if (Looper.myLooper() == Looper.getMainLooper()) {
                                //主线程
                                switch (threadMode) {
                                    case MAIN:
                                        invoke(method, key, obj);
                                        break;
                                    case BACKGROUND:
                                        executor.execute(new Runnable() {
                                            @Override
                                            public void run() {
                                                invoke(method, key, obj);
                                            }
                                        });
                                        break;
                                }
                            } else {
                                //子线程
                                switch (threadMode) {
                                    case MAIN:
                                        mHandler.post(new Runnable() {
                                            @Override
                                            public void run() {
                                                invoke(method, key, obj);
                                            }
                                        });
                                        break;
                                    case BACKGROUND:
                                        invoke(method, key, obj);
                                        break;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private void invoke(Method method, Object obj, Object params) {
        try {
            method.invoke(obj, params);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    public void unregister(Object obj) {
        if (null == obj) throw new NullPointerException("obj can not null!");
        if (cache.containsKey(obj)) {
            cache.remove(obj);
        }
    }
}
