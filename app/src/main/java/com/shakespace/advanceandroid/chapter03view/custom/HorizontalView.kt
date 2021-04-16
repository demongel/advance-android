package com.shakespace.advanceandroid.chapter03view.custom

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.VelocityTracker
import android.view.View
import android.view.ViewGroup
import android.widget.Scroller
import com.shakespace.advanceandroid.global.TAG
import kotlin.math.abs

/**
 * 1. 继承ViewGroup ， 添加三参构造，使用@JvmOverloads constructor 和 默认参数，不用重写三个构造函数
 * 2. 需要实现onLayout() , 先放空
 * 3. 重写onMeasure 处理不同宽高  通常需要处理wrap_content的情况
 * 4. 实现onLayout 计算每个子元素的位置
 * 5. 处理滑动冲突，通常在onInterceptTouchEvent 中处理
 * 6. 自身事件处理， 在onTouchEvent中
 * 7. 处理快速滑动和弹性滑动
 * 8. 处理快速滑动的终止
 */
class HorizontalView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {

    var lastInterceptX: Int = 0
    var lastInterceptY: Int = 0
    var lastX: Int = 0
    var lastY: Int = 0

    // 滑动需要
    private val scroller = Scroller(context)
    var currentIndex = 0

    var childWidth = 0

    // 快速滑动
    private val velocityTracker = VelocityTracker.obtain()

    /**
     * 设置子元素的位置
     * 1. 遍历子元素
     * 2. 设置子元素的位置，ltrb需要随着index变化而变化
     * 3. 根据需要，需要增加处理padding和margin
     */
    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var child: View
        var left = 0
        // MEMO 0..20 [0,20] , 0 until 20 [0,20)
        for (i in 0 until childCount) {
            child = getChildAt(i)
            if (child.visibility != View.GONE) {
                childWidth = child.measuredWidth
                child.layout(left, 0, left + child.measuredWidth, child.measuredHeight)
                left += child.measuredWidth
            }
        }
    }

    /**
     * 处理wrap_content的情况
     *
     * 重点是需要结合子元素进行测量
     * 根据宽高和子元素数量 关系，进行设置
     * 正常EXACTLY 不需要做过多处理
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        val firstChild = getChildAt(0)

        // 1. 测量子元素
        measureChildren(widthMeasureSpec, heightMeasureSpec)

        // 如果是exactly， 不需要特殊处理，就是具体值或者屏幕宽度 即widthSize和heightSize

        // 如果没有子元素，设置宽高为0
        if (childCount == 0) {
            setMeasuredDimension(0, 0)
        } else if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            // 如果宽高都是 AT_MOST ， 那么宽度设置为子元素宽度*子元素数量，高度为第一个子元素高度
            setMeasuredDimension(firstChild.measuredWidth * childCount, firstChild.measuredHeight)
        } else if (widthMode == MeasureSpec.AT_MOST) {
            // 如果只有宽度是AT_MOST
            setMeasuredDimension(firstChild.measuredWidth * childCount, heightSize)
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSize, firstChild.measuredHeight)
        }

    }

    /**
     * 处理滑动冲突
     * 采用从父类【即本类】拦截的方式
     */
    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        var intercept = false
        lastX = ev.x.toInt()
        lastY = ev.y.toInt()

        when (ev.action) {
            // 再次点击时 停止动画
            MotionEvent.ACTION_DOWN -> {
                intercept = false
                if (!scroller.isFinished) {
                    scroller.abortAnimation()
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val deltaX = lastX - lastInterceptX
                val deltaY = lastY - lastInterceptY
                intercept = abs(deltaX) > abs(deltaY)
            }
            MotionEvent.ACTION_UP -> {
                intercept = false
            }
            else -> ""
        }

        lastInterceptX = lastX
        lastInterceptY = lastY

        // 如果为true 事件由自身的onTouchEvent处理
        return intercept
    }

    /**
     * 拦截事件后 会走自己的onTouchEvent
     */
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x.toInt()
        val y = event.y.toInt()

        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                val deltaX = x - lastX
                scrollBy(-deltaX, 0)
            }
            MotionEvent.ACTION_UP -> {
                val distance = scrollX - currentIndex * childWidth
                // 判断滑动是否超过一半
                if (abs(distance) > childWidth / 2) {
                    if (distance > 0) {
                        currentIndex++
                    } else {
                        currentIndex--
                    }
                } else {
                    // 计算加速度
                    velocityTracker.computeCurrentVelocity(1000)
                    val xVelocity = velocityTracker.xVelocity
                    if (abs(xVelocity) > 50) {
                        if (xVelocity > 0) {
                            currentIndex--
                        } else {
                            currentIndex++
                        }
                    }
                }
                if (currentIndex < 0) {
                    currentIndex = 0
                }
                if (currentIndex > childCount - 1) {
                    currentIndex = childCount - 1
                }
                // 滑动到下一页或上一页
                smoothScrollTo(currentIndex * childWidth, 0)
                // 用完要重置
                velocityTracker.clear()
            }
        }
        lastX = x
        lastY = y
        return super.onTouchEvent(event)
    }

    /**
     * 需要配合computeScroll 使用
     */
    private fun smoothScrollTo(destX: Int, destY: Int) {
        scroller.startScroll(scrollX, scrollY, destX - scrollX, destY - scrollY, 1000)
        invalidate()
    }

    override fun computeScroll() {
        super.computeScroll()
        // 滑动是否完成
        if (scroller.computeScrollOffset()) {
            Log.e(this.TAG, "computeScroll: ${scroller.currX} ")
            scrollTo(scroller.currX, scroller.currY)
            postInvalidate()
        }
    }

}
