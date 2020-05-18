package com.shakespace.advanceandroid.chapter02material

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.shakespace.advanceandroid.R
import kotlinx.android.synthetic.main.fragment_text_input.*

/**
 * A simple [Fragment] subclass.
 */
class TextInputFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_text_input, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /**
         * 设置Error ， edittext就是展开的状态，清空才能收回
         */
        name_input.error="用户名错误"
    }

}
