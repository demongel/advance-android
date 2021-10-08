package com.shakespace.advanceandroid.chapter09annotation.dagger.scope

import com.shakespace.advanceandroid.chapter09annotation.dagger.annotation.AScope
import com.shakespace.advanceandroid.chapter09annotation.dagger.annotation.BScope
import dagger.Module
import dagger.Provides
import dagger.Reusable

@Module
class AModule {

    @AScope
    @Provides
    fun provideSheep() = Sheep()

    @BScope
    @Provides
    fun provideMonkey() = Monkey()

    @Reusable
    @Provides
    fun provideCock() = Cock()
}