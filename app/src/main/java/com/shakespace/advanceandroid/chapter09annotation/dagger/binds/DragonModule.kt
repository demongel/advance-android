package com.shakespace.advanceandroid.chapter09annotation.dagger.binds

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntKey
import dagger.multibindings.IntoMap
import dagger.multibindings.IntoSet

@Module
abstract class DragonModule {

    /**
     * 如果没有其他提供Dragon 的方法，例如Inject构造函数，那么会造成循环引用错误
     */
    @Binds
    abstract fun provideDragon(dragon: Dragon): Acient

//    @Binds
//    @IntoSet
//    abstract fun bindTiger(tiger: Tiger): Animal
//
//    @Binds
//    @IntoSet
//    abstract fun bindCat(cat: Cat): Animal

    @Binds
    @IntoSet
    abstract fun bindStudent(student: Student): People

    @Binds
    @IntoSet
    abstract fun bindWorker(worker: Worker): People

    @Binds
    @IntoMap
    @IntKey(1)
    abstract fun bindStudent2(student: Student): People

    @Binds
    @IntoMap
    @IntKey(2)
    abstract fun bindWorker2(worker: Worker): People
}