package com.shakespace.advanceandroid

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.transition.ChangeBounds
import androidx.transition.TransitionManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        toolbar.setTitle(R.string.app_name)
    }

    fun getToolbar() = toolbar

    fun setToolbar(tb: Toolbar?) {
        /**
         * refer https://blog.csdn.net/u010126792/article/details/86012113
         * 场景转换动画
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TransitionManager.beginDelayedTransition(main_root, ChangeBounds())
        }
        if (tb == null) {
            toolbar.visibility = View.VISIBLE
            setSupportActionBar(toolbar)
        } else {
            toolbar.visibility = View.GONE
            setSupportActionBar(tb)
        }
    }
}
