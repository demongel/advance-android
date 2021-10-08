package com.shakespace.advanceandroid.chapter09annotation.dagger.bindinstance

import com.shakespace.advanceandroid.chapter09annotation.dagger.component.DaggerMainComponent
import javax.inject.Inject

class Human2 private constructor() {

    @Inject
    lateinit var mouse: Mouse

    @Inject
    lateinit var mouse2: Mouse

    init {
        // module传参数
//        DaggerMainComponent.builder().bModule(BModule(Mouse())).build().inject(this)
        DaggerMainComponent.builder().mouse(Mouse()).build().inject(this)
    }

    // mouse : 2bbf180e -- mouse2 : 2bbf180e , DaggerMainComponent 创建时注入
    fun print() {
        println(
            "mouse : ${mouse.hashCode().toString(16)} -- " +
                    "mouse2 : ${mouse2.hashCode().toString(16)}"
        )
    }
}