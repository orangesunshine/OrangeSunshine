package com.jiutai.commonlib.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface EvenBase {
    //set方法名称   setOnClickListener
    String listenerSetterMethodName();

    //方法参数类型    OnClickListener.class
    Class<?> listenerType();

    //方法回调  onClick
    String listenerCallBackMethodName();
}
