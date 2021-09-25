package com.shakespace.advanceandroid.chapter07eventbus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.shakespace.advanceandroid.R
import com.shakespace.advanceandroid.chapter07eventbus.otto.OttoBus
import com.shakespace.advanceandroid.global.nav
import kotlinx.android.synthetic.main.fragment_event_one.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.lang.Exception


/**
 * 两个问题
 * 1. NavController 每次nav都会重绘视图，跳转时会走onDestroyView，回来时会走onCreateView，可以通过保存rootView解决
 * 2. lateinit 的对象，没有初始化时 不能做判空，依然会抛出变量未初始化的错误
 * 3. ktx-extension 获取控件结合nav有不少问题，在跳转过程中修改控件，会有不少意想不到的情况
 */
class EventOneFragment : Fragment() {

    var rootView: View? = null
    var tv_eventOne: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_event_one, container, false)
            tv_eventOne = rootView?.findViewById(R.id.tv_eventone)
        }
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tv_subscripe.setOnClickListener {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this)
                Toast.makeText(requireContext(), "注册成功", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "已注册", Toast.LENGTH_SHORT).show()
            }
        }

        tv_eventtwo.setOnClickListener {
            it.nav(R.id.action_eventOneFragment_to_eventTwoFragment)
        }

        tv_subscripe_otto.setOnClickListener {
            try {
                OttoBus.instance.register(this)
                Toast.makeText(requireContext(), "注册OTTO成功", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(requireContext(), e.message, Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @com.squareup.otto.Subscribe
    fun onOttoEventReceived(messageEvent: MessageEvent) {
        tv_eventOne?.text = messageEvent.message
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventReceived(messageEvent: MessageEvent) {
        tv_eventOne?.text = messageEvent.message
    }

    /**
     * 黏性事件
     */
    @Subscribe(threadMode = ThreadMode.POSTING, sticky = true)
    fun onStickEventReceived(messageEvent: MessageEvent) {
        tv_eventOne?.text = messageEvent.message
    }

}