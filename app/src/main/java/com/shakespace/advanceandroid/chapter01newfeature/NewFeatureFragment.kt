package com.shakespace.advanceandroid.chapter01newfeature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.shakespace.advanceandroid.R
import com.shakespace.advanceandroid.global.nav
import kotlinx.android.synthetic.main.fragment_new_feature_list.*


class NewFeatureFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_new_feature_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /**
         * 动画在nav扩展方法中传入，如果xml和代码都有定义，执行代码
         */
        tv_recyclerview.setOnClickListener {
            it.nav(R.id.action_newFeatureFragment_to_recyclerViewFragment)
        }

        tv_card_view.setOnClickListener {
            it.nav(R.id.action_newFeatureFragment_to_cardViewFragment)
        }

        tv_notification.setOnClickListener {
            it.nav(R.id.action_newFeatureFragment_to_notificationFragment)
        }

        tv_toolbar.setOnClickListener {
            it.nav(R.id.action_newFeatureFragment_to_toolbarFragment)
        }
    }


}
