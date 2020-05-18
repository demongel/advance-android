package com.shakespace.advanceandroid.chapter02material

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.shakespace.advanceandroid.MainActivity
import com.shakespace.advanceandroid.R
import com.shakespace.advanceandroid.chapter01newfeature.RecyclerViewFragment
import com.shakespace.advanceandroid.global.TAG
import kotlinx.android.synthetic.main.fragment_coordinator.*
import kotlinx.android.synthetic.main.fragment_tab_layout.tabs
import kotlinx.android.synthetic.main.fragment_tab_layout.viewpager

/**
 * A simple [Fragment] subclass.
 *  refer：https://blog.csdn.net/willway_wang/article/details/96720903
 * scroll：
 *  Child View 伴随着滚动事件而滚出或滚进屏幕。注意两点：
 *  第一点，如果使用了其他值，必定要使用这个值才能起作用；
 *  第二点：如果在这个child View前面的任何其他Child View没有设置这个值，那么这个Child View的设置将失去作用。
 *  向上滑动时，头部完全收回，scrollview才滑动，向下滑动时，scrollview先滑动，到顶后，头部展开。
 *
 *  scroll|enterAlways
 *      向上滑动和scroll一样，向下滑动时，头部先展开，完全展开后，再滚动scrollview，也称作快速返回
 *
 *  scroll|enterAlways|enterAlwaysCollapsed
 *       向下滑动时，头部先展开，展开到最小高度，scrollview开始滚动，到顶，后头部继续展开，直到完全展开
 *The collapsed height is defined by the view's minimum height
 *
 *  snap
 *      类似viewpager，根据滑动范围，自动展开或者收回。
 *
 *scroll|exitUntilCollapsed
 *      向上滑动时，先将头部缩小到最小宽度，再滑动scrollview
 *
 * enter : 表示向下滑动，头部进入
 * exit ： 向上滑动，头部退出
 *
 * 通常使用 ：scroll|enterAlways|enterAlwaysCollapsed|snap
 *
 * snapMargins
 *      需要和snap一起使用，吸附的时候，以margin的上下边缘为基准。
 *
 * 有时候需要 配合使用android:fitsSystemWindows="true"
 */

/**
 *  CoordinatorLayout 是一个ViewGroup
 *  AppBarLayout 是一个LinerLayout
 *  CollapsingToolbarLayout 是一个FrameLayout
 * 其他属性
 *  1. 内部可以使用锚点 anchor
 *  2.
 *  app:collapsedTitleGravity="center"      // 缩小后标题的位置
 *  app:contentScrim="@color/colorPrimaryDark"  // 缩小后toolbar的背景
 *  app:expandedTitleGravity="left|bottom"   //标题展开时的位置
 *  app:expandedTitleMarginStart="8dp"
 *  app:expanded    // 是否展开
 */
class CoordinatorFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coordinator, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as MainActivity).setToolbar(toolbar)

        val tabList =
            mutableListOf<String>("国内", "国际", "动漫", "购物", "娱乐", "游戏", "专题", "政治", "文化", "体育")

        val adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = tabList.size

            override fun createFragment(position: Int): Fragment = RecyclerViewFragment()

        }
        // 设置适配器
        viewpager.adapter = adapter
        TabLayoutMediator(tabs, viewpager) { tab, position ->
            tab.text = tabList[position]
            Log.e(this.TAG, "initViewPager: ${tab.text} + $position ")
        }.attach()

    }

    override fun onDestroy() {
        (requireActivity() as MainActivity).setToolbar(null)
        super.onDestroy()
    }

}
