package com.shakespace.advanceandroid.chapter02material

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.shakespace.advanceandroid.R
import com.shakespace.advanceandroid.global.TAG
import kotlinx.android.synthetic.main.fragment_tab_layout.*

/**
 * A simple [Fragment] subclass.
 */
class TabLayoutFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tab_layout, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViewPager()
    }

    /**
     *
    app:tabIndicatorColor="@color/lightblue"   底部指示器的颜色
    app:tabMode="scrollable"                    可滚动
    app:tabRippleColor="@color/lightcoral"      点击时的背景色
    app:tabSelectedTextColor="@color/colorPrimary"   选中的文本颜色
    app:tabTextColor="@color/darkgray"              普通的文本颜色

     viewpager 的宽度最好是match_parent
     */
    private fun initViewPager() {
        val tabList =
            mutableListOf<String>("国内", "国际", "动漫", "购物", "娱乐", "游戏", "专题", "政治", "文化", "体育")
//        var fragments: MutableList<Fragment> = mutableListOf()
//        tabList.forEach {
//            tabs.addTab(tabs.newTab().setText(it))
//            fragments.add(NewsFragment.newInstance(it))
//        }

        val adapter = FragmentViewAdapter(tabList, this)
        viewpager.offscreenPageLimit = 3
        // 设置适配器
        viewpager.adapter = adapter
        // 关联tablayout 对于viewpager，使用setupWithViewPager
        // 使用Viewpager2，要用TabLayoutMediator
        /**
         * The TabLayoutMediator object also handles the task of generating page titles for the TabLayout object,
         * which means that the adapter class does not need to override getPageTitle():
         */
        TabLayoutMediator(tabs, viewpager) { tab, position ->
            tab.text = tabList[position]
            Log.e(this.TAG, "initViewPager: ${tab.text} + $position ")
        }.attach()

    }

}
