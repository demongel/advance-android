package com.shakespace.advanceandroid.chapter09annotation.dagger

import com.shakespace.advanceandroid.chapter09annotation.dagger.bindinstance.Mouse
import com.shakespace.advanceandroid.chapter09annotation.dagger.component.DaggerMainComponent
import com.shakespace.advanceandroid.chapter09annotation.dagger.scope.Cock
import com.shakespace.advanceandroid.chapter09annotation.dagger.scope.Monkey
import com.shakespace.advanceandroid.chapter09annotation.dagger.scope.Sheep
import dagger.Lazy
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Provider

class Person {
//    @Inject
//    lateinit var dog: Dog

//    @Inject
//    lateinit var cat: Cat

    /**
     * 延迟加载，调用
     */
    @Inject
    lateinit var lazyCow: Lazy<Cow>

    /**
     * 每次提供新的对象
     */
    @Inject
    lateinit var provideCat: Provider<Cat>

    /**
     * 在kotlin中使用@Name给属性注入时需要添加@field，不然注解不生效。
     */
    @Inject
    @field:Named("B")
    lateinit var pig: Pig

    @Inject
    lateinit var monkey: Monkey

    @Inject
    lateinit var sheep: Sheep

    @Inject
    lateinit var cock: Cock

    init {
//        DaggerMainComponent.create().inject(this)
//        DaggerMainComponent.builder().bModule(BModule(Mouse())).build().inject(this)
        DaggerMainComponent.builder().mouse(Mouse()).build().inject(this)
    }

    fun feed(food: String) {
        lazyCow.get().eat(food)
//        cow.eat(food)
    }

    fun getCats(): List<Cat> {
        val list = mutableListOf<Cat>()
        repeat(4) { list.add(provideCat.get()) }
        println(list.size)
        return list
    }

    fun getPigName() {
        println(pig.name)
    }

    fun print(){
        println(cock.hashCode().toString(16))
    }
}