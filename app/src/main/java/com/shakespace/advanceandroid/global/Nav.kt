package com.shakespace.advanceandroid.global

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.shakespace.advanceandroid.R

/**
 * created by  shakespace
 * 2020/5/6  20:48
 */
val commonOptions = NavOptions.Builder().apply {
    setEnterAnim(R.anim.act_enter)
    setExitAnim(R.anim.act_exit)
    setPopEnterAnim(R.anim.act_back_enter)
    setPopExitAnim(R.anim.act_back_exit)
}.build()

fun View.nav(@IdRes action: Int, bundle: Bundle? = null, navOptions: NavOptions = commonOptions) {
    Navigation.findNavController(this).navigate(action, bundle, navOptions)
}
