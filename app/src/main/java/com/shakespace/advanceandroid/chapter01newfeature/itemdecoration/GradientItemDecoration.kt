package com.shakespace.advanceandroid.chapter01newfeature.itemdecoration

import android.graphics.*
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.shakespace.advanceandroid.global.TAG
import com.shakespace.advanceandroid.global.dp2px


/**
 * created by  shakespace
 * 2020/5/6  22:06
 * refer ：https://blog.csdn.net/harvic880925/java/article/details/82959754
 */
class GradientItemDecoration : RecyclerView.ItemDecoration() {

    /**
     * 主要就是重写三个相关的方法 ，旧的三个方法已经废弃，增加了RecyclerView.State 的判断
     */

    var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    /**
     * 显示在最上层
     */
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        val manager = parent.layoutManager
        val childCount = parent.childCount
        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val index = parent.getChildAdapterPosition(child)
            val left = manager!!.getLeftDecorationWidth(child)
            if (i % 5 == 0) {
                c.drawCircle(
                    (parent.width / 2).toFloat(),
                    (child.top + child.height / 2).toFloat(),
                    (child.height / 8).toFloat(),
                    paint
                )
                // index 是当前显示在屏幕上的位置，是固定的
                Log.e(this.TAG, "onDrawOver: ${child.height * index}  ${child.top} ")
            }
        }
    }

    /**
    Canvas c: 是指通过getItemOffsets撑开的空白区域所对应的画布，通过这个canvas对象，可以在getItemOffsets所撑出来的区域任意绘图

    getItemOffsets是针对每个Item都会走一次，也就是说每个Item的outRect都可以不同，但是onDraw和onDrawOver所整个ItemDecoration只执行一次的，
    并不是针对Item的，所以我们需要在onDraw和onDrawOver中绘图时，一次性将所有Item的ItemDecoration绘制完成

    RecyclerView.LayoutManager manager = parent.getLayoutManager();
    int left = manager.getLeftDecorationWidth(child);
    int top = manager.getTopDecorationHeight(child);
    int right = manager.getRightDecorationWidth(child);
    int bottom = manager.getBottomDecorationHeight(child);

    在超出getItemOffsets函数所设定的outRect范围的部分将是不可见的。
    因为在整个绘制流程中，是
    先调用ItemDecoration的onDraw函数，
    然后再调用Item的onDraw函数，
    最后调用ItemDecoration的onDrawOver函数。
    所以在ItemDecoration的onDraw函数中绘制的内容，当超出边界时，会被Item所覆盖。
    但是因为最后才调用ItemDecoration的OnDrawOver函数，所以在onDrawOver中绘制的内容就不受outRect边界的限制，可以覆盖Item的区域显示
     */
    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        val childCount = parent.childCount
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight
        val shader = LinearGradient(
            0f, 0f, parent.width.toFloat(), 0f, intArrayOf(
                Color.parseColor("#faf84d"), Color.parseColor("#003449"),
                Color.parseColor("#808080"),
                Color.parseColor("#CC423C")
            ), floatArrayOf(0.0f, 0.6f, 0.8f, 1.0f), Shader.TileMode.CLAMP
        )
        paint.shader = shader
        for (i in 0 until childCount - 1) {
            val view = parent.getChildAt(i)
            val top = view.bottom.toFloat()
            val bottom: Float = view.bottom + parent.dp2px(1.0f).toFloat()
            c.drawRect(left.toFloat(), top, right.toFloat(), bottom, paint)
        }
    }

    /**
    getItemOffsets的主要作用就是给item的四周加上边距，实现的效果类似于margin，将item的四周撑开一些距离，在撑开这些距离后，我们就可以利用上面的onDraw函数，
    在这个距离上进行绘图了。在了解了getItemOffsets的作用之后，我们来看看这个函数本身：
    getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state)

    Rect outRect：这个是最难理解的部分，outRect就是表示在item的上下左右所撑开的距离，后面详细讲解。
    View view:是指当前Item的View对象
    RecyclerView parent： 是指RecyclerView 本身
    RecyclerView.State state:通过State可以获取当前RecyclerView的状态，也可以通过State在RecyclerView各组件间传递参数，具体的文档，大家可以参考：https://developer.android.com/reference/android/support/v7/widget/RecyclerView.State

    outRect这个参数，outRect 中的 top、left、right、bottom四个点，并不是普通意义的坐标点，而是指的在Item上、左、右、下各撑开的距离，这个值默认是0
     */
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
//        outRect.left=50;
//        outRect.right=100;
//        outRect.bottom=1;
        super.getItemOffsets(outRect, view, parent, state)
    }
}