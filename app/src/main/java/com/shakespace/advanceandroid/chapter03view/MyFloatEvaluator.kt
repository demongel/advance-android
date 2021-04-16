package com.shakespace.advanceandroid.chapter03view

import android.animation.FloatEvaluator
import android.util.Log
import com.shakespace.advanceandroid.global.TAG

/**
 * created by  shakespace
 * 2020/5/2  22:46
 */
class MyFloatEvaluator : FloatEvaluator() {
    override fun evaluate(fraction: Float, startValue: Number?, endValue: Number?): Float {
        Log.e(
            this.TAG,
            "evaluate: fraction = $fraction  startValue = $startValue endValue = $endValue   "
        )
        return super.evaluate(fraction, startValue, endValue)
    }
}