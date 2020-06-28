package com.shakespace.advanceandroid.chapterp03view

import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.animation.Animation
import android.view.animation.TranslateAnimation
import android.widget.LinearLayout
import android.widget.Scroller
import androidx.fragment.app.Fragment
import com.shakespace.advanceandroid.R
import com.shakespace.advanceandroid.global.TAG
import com.shakespace.advanceandroid.global.dp2px
import kotlinx.android.synthetic.main.fragment_view_move.*

/**
 * A simple [Fragment] subclass.
 */
class ViewMoveFragment : Fragment() {

    var screenWidth: Int = 0
    var screenHeight: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        screenHeight = resources.displayMetrics.heightPixels
        screenWidth = resources.displayMetrics.widthPixels
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_move, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.e(this.TAG, "onActivityCreated: ${my_scroll_text_view.scrollX} ")
        super.onActivityCreated(savedInstanceState)
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.move_view, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val widthPixels = resources.displayMetrics.widthPixels
        when (item.itemId) {
            R.id.params -> {
                val layoutParams = demo.layoutParams as LinearLayout.LayoutParams
                if ((demo.right + 50) > screenWidth) {
                    layoutParams.leftMargin = 0
                } else {
                    layoutParams.leftMargin = demo.left + 200
                }
                demo.layoutParams = layoutParams
            }
            R.id.anim -> {
                val translateAnimation = TranslateAnimation(
                    0f, 500f,
                    0f, 600f
                ).apply {
                    duration = 1000
//                    fillAfter = true
                    repeatMode = Animation.REVERSE
                }
                demo.startAnimation(translateAnimation)
            }
            R.id.animator -> {
                // MEMO 如果属性值是float ，使用ofInt 也没有效果
                val trans = ObjectAnimator.ofFloat(demo, "translationX", 0f, 200f)
                trans.duration = 500
                trans.start()
            }
            /**
             * scrollTo : 移动内容到指定坐标，坐标是以当前view为参考。
             * scrollBy： 可以理解为当前内容不动，移动View的画布【也可以当做移动屏幕来理解】
             *
             * 实际上是在重绘时 减去了 mScrollX 和 mScrollY ，本质上做画布的滚动，而不是view本身的移动
             */
            R.id.scroll_to -> {
//                demo.scrollTo(400,400)
                scroll.scrollTo(0, 0)
            }
            R.id.scroll_by -> {
                scroll.scrollBy(200, 0)
            }

            R.id.scroller -> {
                if (my_scroll_view.scrollX > (my_scroll_view.dp2px(1000F) - screenWidth)) {
                    my_scroll_view.smoothScrollTo(0, 0)
                } else {
                    my_scroll_view.smoothScrollTo(my_scroll_view.scrollX + 400, 0)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
