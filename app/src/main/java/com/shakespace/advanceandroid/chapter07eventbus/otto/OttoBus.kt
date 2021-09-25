package com.shakespace.advanceandroid.chapter07eventbus.otto

import com.squareup.otto.Bus

/**
 * created by  shakespace
 * 2021/9/25  23:48
 */
class OttoBus : Bus() {
    companion object {
        val instance: OttoBus by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            OttoBus()
        }
    }
}



