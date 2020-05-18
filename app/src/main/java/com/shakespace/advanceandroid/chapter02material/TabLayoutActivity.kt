package com.shakespace.advanceandroid.chapter02material

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.shakespace.advanceandroid.R
import com.shakespace.advanceandroid.global.TAG
import kotlinx.android.synthetic.main.fragment_tab_layout.*

class TabLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tab_layout)

        val tabList =
            mutableListOf<String>("国内", "国际", "动漫", "购物", "娱乐", "游戏", "专题", "政治", "文化", "体育")

        val adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = tabList.size

            override fun createFragment(position: Int): Fragment =
                NewsFragment.newInstance(tabList[position])

        }
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
