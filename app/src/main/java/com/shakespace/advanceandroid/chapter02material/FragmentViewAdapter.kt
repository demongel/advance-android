package com.shakespace.advanceandroid.chapter02material

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * created by  shakespace
 * 2020/5/16  12:39
 */
class FragmentViewAdapter(
    val dataList: MutableList<String>,
    val fm: Fragment
) :
    FragmentStateAdapter(fm) {
    override fun getItemCount(): Int = dataList.size


    override fun createFragment(position: Int): Fragment =
        NewsFragment.newInstance(dataList[position])
}
