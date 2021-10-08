package com.shakespace.advanceandroid.chapter07eventbus

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.shakespace.advanceandroid.R
import com.shakespace.advanceandroid.chapter07eventbus.otto.OttoBus
import com.shakespace.advanceandroid.chapter08rxjava.rxbus.RxBus
import com.shakespace.advanceandroid.global.nav
import kotlinx.android.synthetic.main.fragment_event_one.*
import kotlinx.android.synthetic.main.fragment_event_one.tv_eventtwo
import kotlinx.android.synthetic.main.fragment_event_two.*
import org.greenrobot.eventbus.EventBus


class EventTwoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_two, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_eventtwo.setOnClickListener {
            EventBus.getDefault().post(MessageEvent("Message from Page2"))
            Navigation.findNavController(tv_eventtwo).popBackStack()
        }

        tv_stick_event.setOnClickListener {
            EventBus.getDefault().postSticky(MessageEvent("黏性事件"))
            Navigation.findNavController(tv_eventtwo).popBackStack()
        }

        tv_otto_event.setOnClickListener {
            OttoBus.instance.post(MessageEvent("OTTO事件"))
            Navigation.findNavController(tv_eventtwo).popBackStack()
        }

        tv_rxbus.setOnClickListener {
            RxBus.getInstance().post(MessageEvent("RxBus事件"))
            Navigation.findNavController(tv_eventtwo).popBackStack()
        }
    }


}