package com.shakespace.advanceandroid.chapterp03view.custom

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import com.shakespace.advanceandroid.R
import com.shakespace.advanceandroid.global.dpToPx

/**
 * created by  shakespace
 * 2020/7/2  23:30
 */
class InvalidTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.BLACK
        strokeWidth = dpToPx(3f).toFloat() // 控制线宽
    }
    private var bgColor: Int

    init {
        /**
         * 1. 用context 拿到TypeArray
         * 2. 获取属性 提供默认值
         * 3. 调用typeArray.recycle()
         */
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.InvalidTextView)
        bgColor = typeArray.getColor(R.styleable.InvalidTextView_bg_color, Color.RED)
        typeArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        /**
         * 支持 wrap_content  需要设置一个默认宽高
         *
         * textview  会先计算文本的宽高，来作为wrap_content的值
         * 如果是自定义，那么文本居中的效果需要自己处理
         */
//        val normalW = 240 + paddingLeft + paddingRight
//        val normalH = 180 + paddingTop + paddingBottom
//
//        setMeasuredDimension(
//            View.resolveSize(normalW, widthMeasureSpec),
//            View.resolveSize(normalH, heightMeasureSpec)
//        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        /**
         * 增加对padding 的处理
         */
        val h = height - paddingTop - paddingBottom
        val w = width - paddingRight - paddingLeft
        canvas?.drawLine(
            paddingLeft.toFloat(),
            (height + paddingTop - paddingBottom).toFloat() / 2,
            (w + paddingLeft).toFloat(),
            (height + paddingTop - paddingBottom).toFloat() / 2,
            paint
        )
        setBackgroundColor(bgColor)
    }
}