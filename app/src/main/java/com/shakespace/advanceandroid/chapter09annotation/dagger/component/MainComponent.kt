package com.shakespace.advanceandroid.chapter09annotation.dagger.component

import com.shakespace.advanceandroid.chapter09annotation.dagger.CatModule
import com.shakespace.advanceandroid.chapter09annotation.dagger.Person
import com.shakespace.advanceandroid.chapter09annotation.dagger.annotation.AScope
import com.shakespace.advanceandroid.chapter09annotation.dagger.annotation.BScope
import com.shakespace.advanceandroid.chapter09annotation.dagger.bindinstance.BModule
import com.shakespace.advanceandroid.chapter09annotation.dagger.bindinstance.Human2
import com.shakespace.advanceandroid.chapter09annotation.dagger.bindinstance.Mouse
import com.shakespace.advanceandroid.chapter09annotation.dagger.binds.DragonModule
import com.shakespace.advanceandroid.chapter09annotation.dagger.binds.Hunter
import com.shakespace.advanceandroid.chapter09annotation.dagger.scope.AModule
import com.shakespace.advanceandroid.chapter09annotation.dagger.scope.Human
import dagger.BindsInstance
import dagger.Component

@Component(modules = [CatModule::class, AModule::class, BModule::class, DragonModule::class])
@AScope
@BScope
interface MainComponent {
    fun inject(person: Person)

    fun inject(human: Human)

    fun inject(human2: Human2)

    fun inject(hunter: Hunter)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun mouse(mouse: Mouse): Builder

        fun build(): MainComponent
    }
}