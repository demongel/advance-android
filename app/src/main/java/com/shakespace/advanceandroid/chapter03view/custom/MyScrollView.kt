package com.shakespace.advanceandroid.chapter03view.custom

import android.content.Context
import android.util.AttributeSet
import android.widget.Scroller
import androidx.appcompat.widget.AppCompatTextView

/**
 * created by  shakespace
 * 2020/5/20  23:18
 */
class MyScrollView @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attributeSet, defStyleAttr) {

    var lastX: Int = 0
    var lastY: Int = 0

    val scroller = Scroller(context)

    override fun computeScroll() {
        super.computeScroll()
//        scroller.isFinished
        if (scroller.computeScrollOffset()) {
            scrollTo(scroller.currX, scroller.currY)
            postInvalidate()
        }
    }

    /**
     * 如果这里没有 改变 scrollX 的值，就需要调用 invalidate() 去重绘
     * 如果改变了 scrollX 的值 就不用， 在页面刷新时，
     */
    fun smoothScrollTo(destX: Int, dextY: Int) {
        val delta = destX - scrollX
//        scrollX -=1
        // smooth move to destX in 1000ms
        //  NOTE: startScroll only save some params , include finalX, finalY
        scroller.startScroll(scrollX, 0, delta, 0, 1000)
        // will call draw(xxx,xxx,xxx)? --> computeScroll() --> postInvalidate() -->...
        invalidate()
    }

}