package com.shakespace.advanceandroid.chapter01newfeature.adapter

import android.view.View

/**
 * created by  shakespace
 * 2020/5/6  21:50
 */
interface OnRecyclerViewItemClickListener {
    fun onClick(view: View, position: Int)

    fun onLongClick(view: View, position: Int):Boolean
}