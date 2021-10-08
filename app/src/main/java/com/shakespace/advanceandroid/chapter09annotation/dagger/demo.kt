package com.shakespace.advanceandroid.chapter09annotation.dagger

import com.shakespace.advanceandroid.chapter09annotation.dagger.binds.Hunter

fun main() {
//    val person = Person()
//    person.feed("grass")
//    person.getCats()
//    person.getPigName()

//    val human = Human()
//    human.print()
//
//    val person = Person()
//    person.print()

//    Human2::class.primaryConstructor?.apply {
//        this.isAccessible = true
//        val human2 = this.call()
//        println(human2.mouse)
//        human2.print()
//    }
    // 私有构造不能直接创建
    //val privateHuman: Human2 = Human2::class.createInstance()

    Hunter().print()
}