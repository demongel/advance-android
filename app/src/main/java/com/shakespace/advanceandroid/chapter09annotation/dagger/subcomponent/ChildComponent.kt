package com.shakespace.advanceandroid.chapter09annotation.dagger.subcomponent

import dagger.Subcomponent

/**
 * 子类关联BikeModule 可以拿到Bike
 */
@Subcomponent(modules = [BikeModule::class])
interface ChildComponent {
    fun inject(child: Child)

    // 使用方式1 SubComponent 必须显式地声明 Subcomponent.Builder，parentComponent 需要用 Builder 来创建 ChildComponent
    @Subcomponent.Builder
    interface Builder {
        fun build(): ChildComponent
    }
}