package com.infoholdcity.baselibrary.annotation;

import android.support.annotation.Keep;

import java.lang.annotation.*;

@Keep
@Documented
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface TimeOut {
    long value();
}
