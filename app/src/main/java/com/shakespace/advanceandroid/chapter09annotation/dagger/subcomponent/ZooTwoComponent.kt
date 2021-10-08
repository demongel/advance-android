package com.shakespace.advanceandroid.chapter09annotation.dagger.subcomponent

import dagger.Component

/**
 * 直接依赖于ZooOne,ZooOneComponent 要有可以提供依赖的方法 可以直接共用CModule提供的对象
 */
@Component(dependencies = [ZooOneComponent::class])
interface ZooTwoComponent {
    fun inject(zooTwo: ZooTwo)
}