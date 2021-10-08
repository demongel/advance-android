package com.shakespace.advanceandroid.chapter09annotation.dagger.binds

import com.shakespace.advanceandroid.chapter09annotation.dagger.bindinstance.Mouse
import com.shakespace.advanceandroid.chapter09annotation.dagger.component.DaggerMainComponent
import javax.inject.Inject

class Hunter {
    @Inject
    lateinit var dragon1: Acient

    @Inject
    lateinit var dragon2: Acient

    //    @JvmSuppressWildcards
    @Inject
//    @set:Inject // 无效
    lateinit var animals: Set<@JvmSuppressWildcards People>

    @JvmSuppressWildcards
    @Inject
    lateinit var peoples: Map<Int, People>

    init {
        DaggerMainComponent.builder().mouse(Mouse()).build().inject(this)
    }

    fun print() {
        println("dragon1 is Dragon? ${dragon1 is Dragon} ")
        println("dragon2 is Dragon? ${dragon2 is Dragon} ")
        println(dragon1.hashCode())
        println(dragon2.hashCode())
        println(animals.toString())
        println(peoples.toString())
    }

    /**
    dragon1 is Dragon? true
    dragon2 is Dragon? true
    1338668845
    159413332
    [com.shakespace.advanceandroid.chapter09annotation.dagger.binds.Student@214c265e, com.shakespace.advanceandroid.chapter09annotation.dagger.binds.Worker@7ba4f24f]
    {1=com.shakespace.advanceandroid.chapter09annotation.dagger.binds.Student@20ad9418, 2=com.shakespace.advanceandroid.chapter09annotation.dagger.binds.Worker@31cefde0}
     */

}