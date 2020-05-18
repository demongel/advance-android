package com.shakespace.advanceandroid.chapter02material

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.shakespace.advanceandroid.MainActivity
import com.shakespace.advanceandroid.R
import com.shakespace.advanceandroid.global.TAG
import kotlinx.android.synthetic.main.fragment_drawer.*

/**
 * A simple [Fragment] subclass.
 */
class DrawerFragment : Fragment() {

    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_drawer, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val tabList =
            mutableListOf<String>("国内", "国际", "动漫", "购物", "娱乐", "游戏", "专题", "政治", "文化", "体育")
        val adapter = FragmentViewAdapter(tabList, this)
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
        }.attach()


        // drawer
        actionBarDrawerToggle = ActionBarDrawerToggle(
            requireActivity(),
            drawer,
            (requireActivity() as MainActivity).getToolbar(),
            R.string.drawer_open,
            R.string.drawer_close
        )
        actionBarDrawerToggle.syncState()
        drawer.addDrawerListener(actionBarDrawerToggle)


        nav_view.setNavigationItemSelectedListener {
            Log.e(this.TAG, "onActivityCreated: ${it.title} ")
            drawer.closeDrawers()
            true
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // 关闭指示器效果
        actionBarDrawerToggle.isDrawerIndicatorEnabled = false
        actionBarDrawerToggle.isDrawerSlideAnimationEnabled = false
    }

}
