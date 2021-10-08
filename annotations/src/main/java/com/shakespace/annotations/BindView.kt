package com.shakespace.annotations

@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FIELD)
annotation class BindView(val value: Int = 1)