package com.shakespace.advanceandroid.chapter09annotation.dagger

open class Animal {
    open fun eat(food: String) {
        println("${this.javaClass.simpleName} eat $food")
    }
}