package com.shakespace.advanceandroid.chapter01newfeature

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.palette.graphics.Palette
import com.shakespace.advanceandroid.MainActivity
import com.shakespace.advanceandroid.R
import kotlinx.android.synthetic.main.fragment_toolbar.*

/**
 * A simple [Fragment] subclass.
 */
class ToolbarFragment : Fragment() {

    lateinit var actionBarDrawerToggle: ActionBarDrawerToggle
    lateinit var mango: Bitmap
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_toolbar, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        actionBarDrawerToggle = ActionBarDrawerToggle(
            requireActivity(),
            draw_layout,
            (requireActivity() as MainActivity).getToolbar(),
            R.string.drawer_open,
            R.string.drawer_close
        )
        actionBarDrawerToggle.syncState()
        draw_layout.addDrawerListener(actionBarDrawerToggle)

        mango = BitmapFactory.decodeResource(resources, R.drawable.mango)

        // Palette 用于提取图片颜色，来设置背景， 可以获得较为统一的效果
        val palette = Palette.from(mango).generate()
        palette.apply {
            content.background = vibrantSwatch?.rgb?.let {
                ColorDrawable(it)
            }
            (requireActivity() as MainActivity).getToolbar().background =
                lightVibrantSwatch?.rgb?.let {
                    ColorDrawable(it)
                }
            drawer.background = darkVibrantSwatch?.rgb?.let {
                ColorDrawable(it)
            }
        }

    }

    override fun onDestroy() {
        (requireActivity() as MainActivity).getToolbar().background =
            (ColorDrawable(ContextCompat.getColor(requireContext(), R.color.colorPrimary)))
        // 关闭指示器效果
        actionBarDrawerToggle.isDrawerIndicatorEnabled = false
        actionBarDrawerToggle.isDrawerSlideAnimationEnabled = false
        super.onDestroy()
    }

}
