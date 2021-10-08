package com.shakespace.advanceandroid.chapter09annotation.dagger.scope

import com.shakespace.advanceandroid.chapter09annotation.dagger.Animal
import com.shakespace.advanceandroid.chapter09annotation.dagger.annotation.BScope
import javax.inject.Inject

@BScope
class Cock @Inject constructor() : Animal() {
}