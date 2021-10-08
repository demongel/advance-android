package com.shakespace.advanceandroid.chapter09annotation

fun main() {
    // 简单注解处理
    val annotations = World::class.java.annotations
    for (annotation in annotations){
        if(annotation is Man){
            println(annotation.name)
        }
    }
}