package com.shakespace.advanceandroid.chapter09annotation.dagger.bindinstance

import com.shakespace.advanceandroid.chapter09annotation.dagger.Animal
import dagger.Module
import dagger.Provides

@Module
class BModule {

    @Provides
    fun provideMouse(mouse: Mouse): Animal {
        return mouse
    }
}

/*
在module 构造传参
@Module
class BModule(val mouse: Mouse) {
    @Provides
    fun provideMouse() = mouse
}*/
