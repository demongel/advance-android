package com.shakespace.advanceandroid.chapter09annotation.dagger.scope

import com.shakespace.advanceandroid.chapter09annotation.dagger.bindinstance.Mouse
import com.shakespace.advanceandroid.chapter09annotation.dagger.component.DaggerMainComponent
import javax.inject.Inject

class Human {

//    @Inject
//    lateinit var monkey1: Monkey
//
//    @Inject
//    lateinit var monkey2: Monkey
//
//
//    @Inject
//    lateinit var horse1: Horse
//
//    @Inject
//    lateinit var horse2: Horse
//
//    @Inject
//    lateinit var horse3: Horse

    @Inject
    lateinit var cock: Cock

    @Inject
    lateinit var cock2: Cock

    init {
//        DaggerMainComponent.create().inject(this)
//        DaggerMainComponent.builder().bModule(BModule(Mouse())).build().inject(this)
        DaggerMainComponent.builder().mouse(Mouse()).build().inject(this)
    }

    //Monkey@1fb3ebeb -- Monkey@1fb3ebeb
    // 地址是一样的
    fun print() {
//        println("monkey1 : ${monkey1.hashCode()} -- monkey2 : ${monkey2.hashCode()} ")
//
//        println("horse1 : ${horse1.hashCode().toString(16)} -- horse2 : ${horse2.hashCode().toString(16)} -- horse3 : ${horse3.hashCode().toString(16)}")
//        cock : 1218025c -- cock2 : 1218025c
        println(
            "cock : ${cock.hashCode().toString(16)} -- " +
                    "cock2 : ${cock2.hashCode().toString(16)}"
        )
    }
}