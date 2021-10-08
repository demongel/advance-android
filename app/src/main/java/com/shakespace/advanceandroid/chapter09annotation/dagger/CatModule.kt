package com.shakespace.advanceandroid.chapter09annotation.dagger

import dagger.Module
import dagger.Provides
import javax.inject.Named

/*
 * 约定俗成的是@Provides方法一般以provide为前缀，Moudle 类以Module为后缀，一个 Module 类中可以有多个@Provides方法。
 * @Module 是一个class ， 不是interface
 */
@Module
class CatModule {
    @Provides
    fun provideCat() = Cat()

//    @Provides
//    fun provideCow() = Cow()

    @Named("A")
    @Provides
    fun providePigA() = Pig("Pig A")

    @Named("B")
    @Provides
    fun providePigB() = Pig("Pig B")
}