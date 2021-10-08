package com.shakespace.advanceandroid.chapter09annotation.dagger.subcomponent

import dagger.Component
import dagger.Module
import dagger.Provides
import javax.inject.Inject

class Parent {
    @Inject
    lateinit var car: Car
}

class Child {
    @Inject
    lateinit var car: Car

    @Inject
    lateinit var bike: Bike

    init {
//        SubComponent 编译时不会生成 DaggerChildComponent，需要通过 parentComponent 的获取 SubComponent.Builder 方法获取 ChildComponent 实例
        DaggerParentComponent.builder().build().childComponent().build().inject(this)
//        DaggerParentComponent.create().childComponent2().inject(this)
    }
}

class Car
class Bike

/**
 * 父类关联了CarModule 可以拿到Car
 */
@Component(modules = [CarModule::class])
interface ParentComponent {
    fun inject(parent: Parent)

    // 方法1 用来创建childComponent
    fun childComponent(): ChildComponent.Builder

//    fun childComponent2(): ChildComponent
}


// 在CarModule上添加subComponents , 官方教程表示需要，但是目前测下来删了也可以注入
@Module(subcomponents = [ChildComponent::class])
//@Module
class CarModule {
    @Provides
    fun provideCar() = Car()
}

@Module
class BikeModule {
    @Provides
    fun provideBike() = Bike()
}