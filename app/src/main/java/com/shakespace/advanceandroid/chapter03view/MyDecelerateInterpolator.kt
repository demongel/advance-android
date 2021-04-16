package com.shakespace.advanceandroid.chapter03view

import android.util.Log
import android.view.animation.DecelerateInterpolator
import com.shakespace.advanceandroid.global.TAG

/**
 * created by  shakespace
 * 2020/5/2  23:03
 */
class MyDecelerateInterpolator : DecelerateInterpolator() {
    override fun getInterpolation(input: Float): Float {
        Log.e(this.TAG, "getInterpolation: ----$input ")
        return super.getInterpolation(input)
    }
}