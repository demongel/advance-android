package com.shakespace.advanceandroid.chapter09annotation.dagger.scope

import com.shakespace.advanceandroid.chapter09annotation.dagger.Animal
import com.shakespace.advanceandroid.chapter09annotation.dagger.annotation.BScope
import javax.inject.Inject

@BScope
class Horse @Inject constructor() : Animal() {
}