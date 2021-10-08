package com.shakespace.advanceandroid.chapter09annotation.dagger.subcomponent

import dagger.Component

@Component(modules = [TigerModule::class])
interface ZooOneComponent {
    fun inject(zooOne: ZooOne)

    // for ZooTwoComponent
    fun provideTiger(): Tiger
}