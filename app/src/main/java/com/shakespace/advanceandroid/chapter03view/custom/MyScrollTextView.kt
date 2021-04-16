package com.shakespace.advanceandroid.chapter03view.custom

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Scroller
import androidx.appcompat.widget.AppCompatTextView
import com.shakespace.advanceandroid.global.TAG

/**
 * created by  shakespace
 * 2020/5/20  23:18
 */
class MyScrollTextView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attributeSet, defStyleAttr) {

    var lastX: Int = 0
    var lastY: Int = 0

    val scroller = Scroller(context)

    init {
        Log.e(this.TAG, "init $scrollX:  ")
    }

    val gestureDetector = GestureDetector(context, object : GestureDetector.OnGestureListener {
        override fun onShowPress(e: MotionEvent?) {

        }

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            return true
        }

        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
//            Log.e(this.TAG, "onFling: ${velocityTracker.xVelocity}  $velocityX   $scrollX")
            // memo 调用fling 函数，限定最大最小范围
            scroller.fling(
                scrollX,
                0,
                -velocityX.toInt(),
                0,
                0,
                right - resources.displayMetrics.widthPixels,
                0,
                0
            )
            return true
        }

        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            scrollX += distanceX.toInt()
            if (scrollX < 0) {
                scrollX = 0
            }
            if (scrollX > (right - resources.displayMetrics.widthPixels)) {
                scrollX = right - resources.displayMetrics.widthPixels
            }
//            Log.e(TAG, "onScroll: ${e1?.action}  ${e2?.action}  $distanceX  $scrollX  $right ${dp2px(1000f)}" )
            scroller.startScroll(scrollX, 0, 0, 0, 0)
            // will call draw(xxx,xxx,xxx)? --> computeScroll() --> postInvalidate() -->...
//            invalidate()
            return true
        }

        override fun onLongPress(e: MotionEvent?) {

        }

    })

    override fun computeScroll() {
        super.computeScroll()
//        scroller.isFinished
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.currX, scroller.currY)
            postInvalidate()
            invalidate()
        }
    }

    /**
     * 在 onTouchEvent 中关联 gestureDetector.onTouchEvent(event)
     */
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return gestureDetector.onTouchEvent(event)
    }

    fun smoothScrollTo(destX: Int, dextY: Int) {
        val delta = destX - scrollX
        // smooth move to destX in 1000ms
        //  NOTE: startScroll only save some params , include finalX, finalY
        scroller.startScroll(scrollX, 0, delta, 0, 1000)
        // will call draw(xxx,xxx,xxx)? --> computeScroll() --> postInvalidate() -->...
        invalidate()
    }

}