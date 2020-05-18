package com.shakespace.advanceandroid.chapter02material.behavior

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.ImageButton
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.shakespace.advanceandroid.R
import com.shakespace.advanceandroid.global.TAG
import com.shakespace.advanceandroid.global.statusBarHeight

class ImageButtonBehavior(
    context: Context,
    attrs: AttributeSet?
) : CoordinatorLayout.Behavior<ImageButton>(context, attrs) {
    private var maxScrollDistance = 0
    private var maxChildWidth = 0f
    //计算出头像的最小宽度
    //计算出toolbar的高度
    //计算出状态栏的高度
    //计算出头像居右的距离
    private val minChildWidth: Float = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP, 32f, context.resources
            .displayMetrics
    )
    private val toolbarHeight: Int = context.resources
        .getDimensionPixelSize(R.dimen.abc_action_bar_default_height_material)
    private val statusBarHeight: Int = context.statusBarHeight
    private var appbarStartPoint = 0
    private val marginRight: Int = context.resources.getDimensionPixelSize(R.dimen.text_margin)

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: ImageButton,
        dependency: View
    ): Boolean {
//确定依赖关系，这里我们用作头像的ImageButton相依赖的是AppBarLayout，也就是ImageButton跟着AppBarLayout的变化而变化。
        return dependency is AppBarLayout
    }

    private var startX = 0
    private var startY = 0
    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: ImageButton,
        dependency: View
    ): Boolean {
        // toobar 56dp, statusbar 28dp
        //这里的dependency就是布局中的AppBarLayout，child即显示的头像
        /**
         * 大致流程：
         * 1.计算AppBarLayout的变化范围，0~ dependency.bottom ，记录最初的dependency.bottom
         * 2. 获得child的起始坐标
         * 3. 计算变化差值： 最初的bottom-当前的bottom
         * 4.
         */
        Log.e(
            this.TAG,
            "onDependentViewChanged: ${dependency.top} - ${dependency.scrollY} -- ${dependency.height}  -- ${dependency.bottom} "
        )
        if (dependency.bottom <= 0) child.visibility = View.GONE else child.visibility =
            View.VISIBLE

        if (maxScrollDistance == 0) { //也就是第一次进来时，计算出AppBarLayout的最大垂直变化距离
            maxScrollDistance = dependency.bottom - toolbarHeight
        }

        //计算出appbar的开始的y坐标
        if (appbarStartPoint == 0) appbarStartPoint = dependency.bottom
        //计算出头像的宽度
        if (maxChildWidth == 0f) maxChildWidth =
            Math.min(child.width, child.height).toFloat()
        //计算出头像的起始x坐标
        if (startX == 0) startX = (dependency.width / 2 - maxChildWidth / 2).toInt()
        //计算出头像的起始y坐标
        if (startY == 0) startY =
            (dependency.bottom - maxScrollDistance / 2 - maxChildWidth / 2 - toolbarHeight / 2).toInt()
        //计算出appbar已经变化距离的百分比，起始位置y减去当前位置y，然后除以最大距离
        val expandedPercentageFactor =
            (appbarStartPoint - dependency.bottom) * 1.0f /
                    (maxScrollDistance * 1.0f)
        //根据上面计算出的百分比，计算出头像应该移动的y距离,通过百分比乘以最大距离
        val moveY =
            expandedPercentageFactor * (maxScrollDistance - (appbarStartPoint - startY - toolbarHeight / 2 - minChildWidth / 2))
        //根据上面计算出的百分比，计算出头像应该移动的y距离
        val moveX =
            expandedPercentageFactor * (startX + maxChildWidth - marginRight - minChildWidth)
        Log.e(this.TAG, "onDependentViewChanged: $expandedPercentageFactor -- $startX -- $maxChildWidth -- $marginRight -- $minChildWidth ")
        //更新头像的位置
        child.x = startX + moveX
        child.y = startY - moveY
        //计算出当前头像的宽度
        val nowWidth =
            maxChildWidth - (maxChildWidth - minChildWidth) * expandedPercentageFactor
        //更新头像的宽高
        val params =
            child.layoutParams as CoordinatorLayout.LayoutParams
        params.width = nowWidth.toInt()
        params.height = params.width
        child.layoutParams = params
        return true
    }

}