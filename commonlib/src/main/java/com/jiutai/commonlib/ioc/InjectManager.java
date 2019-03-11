package com.jiutai.commonlib.ioc;

import android.app.Activity;
import android.view.View;

import com.jiutai.commonlib.ioc.annotation.ContentLayout;
import com.jiutai.commonlib.ioc.annotation.EvenBase;
import com.jiutai.commonlib.ioc.annotation.InjectView;
import com.jiutai.commonlib.ioc.annotation.ListenerInvocationHanlder;
import com.jiutai.commonlib.ioc.annotation.OnClick;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class InjectManager {
    private static final String viewFindMethod = "findViewById";
    private static final String setContentMethod = "setContentView";

    public static void inject(Activity activity) {
        if (null == activity) throw new NullPointerException("activity not be null!");
        injectLayout(activity);
        injectViews(activity);
        injectEvents(activity);
    }

    private static void injectLayout(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        if (null != clazz) {
            ContentLayout annotation = clazz.getAnnotation(ContentLayout.class);
            if (null != annotation) {
                int value = annotation.value();
                try {
                    Method method = clazz.getMethod(setContentMethod, int.class);
                    if (null != method) {
                        method.invoke(activity, value);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void injectViews(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        if (null != clazz) {
            Field[] fields = clazz.getDeclaredFields();
            if (null != fields) {
                for (Field field : fields) {
                    if (null != field) {
                        InjectView annotation = field.getAnnotation(InjectView.class);
                        if (null != annotation) {
                            int value = annotation.value();
                            try {
                                Method method = clazz.getMethod(viewFindMethod, int.class);
                                if (null != method) {
                                    Object view = method.invoke(activity, value);
                                    field.setAccessible(true);
                                    field.set(activity, view);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    private static void injectEvents(Activity activity) {
        Class<? extends Activity> clazz = activity.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        if (null != methods) {
            for (Method method : methods) {
                eventMethod(activity, method);
            }
        }
    }

    private static void eventMethod(final Activity activity, final Method method) {
        if (null != method) {
            OnClick annotation = method.getAnnotation(OnClick.class);
            if (null != annotation) {
                int[] v1 = annotation.value();
                Class<? extends Annotation> annotationType = annotation.annotationType();
                if (null != annotationType) {
                    EvenBase evenBase = annotationType.getAnnotation(EvenBase.class);
                    if (null != evenBase) {
                        try {
                            Method value = annotationType.getDeclaredMethod("value");
                            String callBack = evenBase.listenerCallBackMethodName();
                            String setter = evenBase.listenerSetterMethodName();
                            Class<?> type = evenBase.listenerType();
                            int[] viewIds = (int[]) value.invoke(annotation);
//                            View.OnClickListener listener = staticImp(activity, method);
                            Object listener = dynamicImp(activity, method, callBack, type);
                            for (int viewId : viewIds) {
                                View view = activity.findViewById(viewId);
                                Method setMethod = view.getClass().getMethod(setter, type);
                                if (null != setMethod) {
                                    setMethod.invoke(view, listener);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }

    private static Object dynamicImp(final Activity activity, final Method method, String callBack, Class<?> type) {
        ListenerInvocationHanlder hanlder = new ListenerInvocationHanlder(activity);
        hanlder.add(callBack, method);
        return Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, hanlder);
    }

    private static View.OnClickListener staticImp(final Activity activity, final Method method) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    method.setAccessible(true);
                    method.invoke(activity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
}
