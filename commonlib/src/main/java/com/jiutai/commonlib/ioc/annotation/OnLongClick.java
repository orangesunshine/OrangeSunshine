package com.jiutai.commonlib.ioc.annotation;

import android.view.View;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@EvenBase(listenerSetterMethodName = "setOnLongClickListener", listenerType = View.OnLongClickListener.class, listenerCallBackMethodName = "onLongClick")
public @interface OnLongClick {
    int[] value();
}
