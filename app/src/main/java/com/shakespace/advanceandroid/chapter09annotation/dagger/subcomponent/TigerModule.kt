package com.shakespace.advanceandroid.chapter09annotation.dagger.subcomponent

import dagger.Module
import dagger.Provides

@Module
class TigerModule {
    @Provides
    fun provideTiger() = Tiger()
}