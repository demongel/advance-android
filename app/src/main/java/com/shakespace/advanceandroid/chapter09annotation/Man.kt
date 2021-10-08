package com.shakespace.advanceandroid.chapter09annotation

@Retention(AnnotationRetention.RUNTIME)
annotation class Man(val name: String = "Tom", val age: Int = 18)
