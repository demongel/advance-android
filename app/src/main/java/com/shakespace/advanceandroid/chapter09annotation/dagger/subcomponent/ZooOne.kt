package com.shakespace.advanceandroid.chapter09annotation.dagger.subcomponent

import javax.inject.Inject

class ZooOne {

    @Inject
    lateinit var tiger: Tiger

    init {
        DaggerZooOneComponent.create().inject(this)
    }
}