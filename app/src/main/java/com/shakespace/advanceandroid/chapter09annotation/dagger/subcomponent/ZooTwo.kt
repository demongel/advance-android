package com.shakespace.advanceandroid.chapter09annotation.dagger.subcomponent

import javax.inject.Inject

class ZooTwo {

    @Inject
    lateinit var tiger: Tiger

    init {
        DaggerZooTwoComponent.builder().zooOneComponent(DaggerZooOneComponent.create()).build()
            .inject(this)
    }
}