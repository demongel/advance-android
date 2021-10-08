package com.shakespace.advanceandroid.chapter09annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Swordman {
    String name();

    int age();
}
