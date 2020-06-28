package com.shakespace.advanceandroid.chapterp03view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView

/**
 * created by  shakespace
 * 2020/5/20  23:18
 */
class OffsetView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attributeSet, defStyleAttr) {

    var lastX: Int = 0
    var lastY: Int = 0

    /**
     * 通过硬件加速，经历一次自下而上的遍历，一直到ViewRootImpl 的scheduleTraversals
     * refer ： https://www.cnblogs.com/muouren/p/11750156.html
     */
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.x.toInt()
                lastY = event.y.toInt()
            }

            MotionEvent.ACTION_MOVE -> {
                val offsetX = event.x.toInt() - lastX
                val offsetY = event.y.toInt() - lastY
                offsetLeftAndRight(offsetX)
                offsetTopAndBottom(offsetY)
            }
        }
        return true
    }
}