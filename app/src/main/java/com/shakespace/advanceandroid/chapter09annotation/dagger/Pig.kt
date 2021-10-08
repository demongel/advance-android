package com.shakespace.advanceandroid.chapter09annotation.dagger

class Pig() : Animal() {

    lateinit var name: String

    constructor(name: String) : this() {
        this.name = name
    }

}