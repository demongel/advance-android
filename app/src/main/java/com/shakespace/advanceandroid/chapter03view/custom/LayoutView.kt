package com.shakespace.advanceandroid.chapter03view.custom

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatTextView
import com.shakespace.advanceandroid.global.TAG

/**
 * created by  shakespace
 * 2020/5/20  23:18
 */
class LayoutView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attributeSet, defStyleAttr) {

    var lastX: Int = 0
    var lastY: Int = 0

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                lastX = event.x.toInt()
                lastY = event.y.toInt()
            }

            MotionEvent.ACTION_MOVE -> {
                val offsetX = event.x.toInt() - lastX
                val offsetY = event.y.toInt() - lastY
                Log.e(this.TAG, "onTouchEvent: $left , $right , $top , $bottom ")
                layout(left + offsetX, top + offsetY, right + offsetX, bottom + offsetY)
            }
        }

        return true
    }
}