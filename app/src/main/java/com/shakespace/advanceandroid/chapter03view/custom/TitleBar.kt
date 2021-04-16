package com.shakespace.advanceandroid.chapter03view.custom

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import com.shakespace.advanceandroid.R
import com.shakespace.advanceandroid.global.TAG

/**
 * created by  shakespace
 * 2020/9/6  21:57
 */
class TitleBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    ConstraintLayout(context, attrs, defStyleAttr) {

    init {
        LayoutInflater.from(context).inflate(R.layout.title_bar, this, true)
        /**
         * 也可以读取属性
         */
    }


    override fun onFinishInflate() {
        super.onFinishInflate()

        val leftIcon: ImageView = this.findViewById(R.id.left_icon)
        val rightIcon: ImageView = this.findViewById(R.id.right_icon)
        val title: TextView = this.findViewById(R.id.title)


        leftIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.apple))
        rightIcon.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.strawberry))
        leftIcon.setOnClickListener {
            Log.e(this.TAG, "onFinishInflate:  click left")
        }

        rightIcon.setOnClickListener {
            Log.e(this.TAG, "onFinishInflate: click right ")
        }

        title.setOnClickListener {
            Log.e(this.TAG, "onFinishInflate: cick title ")
        }
    }

}