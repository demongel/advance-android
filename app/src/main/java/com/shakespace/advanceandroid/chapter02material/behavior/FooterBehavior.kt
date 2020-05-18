package com.shakespace.advanceandroid.chapter02material.behavior

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.shakespace.advanceandroid.global.TAG

/**
 * created by  shakespace
 * 2020/5/18  0:00
 * 监听滑动状态
 */
class FooterBehavior(context: Context, attributeSet: AttributeSet) :
    CoordinatorLayout.Behavior<View>(context, attributeSet) {

    var directionChange = 0
    var inAnimator = false
    override fun onStartNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        directTargetChild: View,
        target: View,
        axes: Int,
        type: Int
    ): Boolean {
        /**
         * 返回true
         */
        Log.e(this.TAG, "onStartNestedScroll: $axes ")
        return (axes and ViewCompat.SCROLL_AXIS_VERTICAL) != 0
    }

    /**
     * 这里的target 是 RecyclerView ，实际上应该是CoordinatorLayout中 实现NestedScrollingChild2, NestedScrollingChild3 接口的view
     */
    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: View,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {
        // 往上 dy >0  ， 往下 dy < 0
        Log.e(this.TAG, "onNestedPreScroll: $dy ")
        if ((dy > 0 && directionChange < 0) || (dy < 0 && directionChange > 0)) {
            child.animate().cancel()
            directionChange = 0
        }
        directionChange += dy
        /**
         * 从显示到隐藏需要加一个判断
         * 1. 因为在动画过程中，依然是可见的，所以，hide可能再次执行，重复执行会导致上一次动画提前结束，调用end方法，于是看起来很快就消失
         * 2. 从隐藏到可见，同样执行多次，但是提前执行end后，变成可见，看起来似乎正常。
         */
        if (directionChange > child.height && child.visibility == View.VISIBLE && !inAnimator) {
            hide(child)
        } else if (directionChange < 0 && child.visibility == View.INVISIBLE) {
            show(child)
        }
    }

    /**
     * child 不能设置GONE，否则无法接受事件
     */
    fun hide(view: View) {
        Log.e(this.TAG, "hide: ${view.height} ")
        val animator = view.animate().translationY(view.height.toFloat())
            .setInterpolator(AccelerateDecelerateInterpolator())
            .setDuration(500)
        animator.setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                view.visibility = View.INVISIBLE
                inAnimator = false
            }
        })
        animator.start()
        inAnimator = true
    }

    fun show(view: View) {
        val animator = view.animate().translationY(0f)
            .setInterpolator(FastOutSlowInInterpolator())
            .setDuration(500)
        // 从隐藏到可见，正确的方式应该是动画一开始就可见
        animator.setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                view.visibility = View.VISIBLE
            }
        })
//        animator.setListener(object : AnimatorListenerAdapter() {
//            override fun onAnimationStart(animation: Animator?) {
//                super.onAnimationStart(animation)
//                Log.e(this.TAG, "onAnimationStart: ${view.visibility == View.VISIBLE} ")
//            }
//            override fun onAnimationEnd(animation: Animator?) {
//                Log.e(this.TAG, "onAnimationEnd:  ")
//                view.visibility = View.VISIBLE
//            }
//        })
//        animator.setUpdateListener {
//            Log.e(this.TAG, "show: ${view.visibility == View.VISIBLE} ")
//        }
        animator.start()
    }
}