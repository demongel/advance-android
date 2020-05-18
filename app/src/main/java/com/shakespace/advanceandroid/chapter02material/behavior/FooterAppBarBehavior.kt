package com.shakespace.advanceandroid.chapter02material.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

/**
 * created by  shakespace
 * 2020/5/18  0:00
 * 监听滑动状态
 */
class FooterAppBarBehavior(context: Context, attributeSet: AttributeSet) :
    CoordinatorLayout.Behavior<View>(context, attributeSet) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        // 依赖于什么控件，此处依赖AppBarLayout
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: View,
        dependency: View
    ): Boolean {
        // 随着所依赖的空间某些属性值的变化而变化
        child.translationY = abs(dependency.y)
        return true
    }
}