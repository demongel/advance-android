package com.shakespace.advanceandroid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.shakespace.advanceandroid.global.nav
import kotlinx.android.synthetic.main.fragment_home.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initListener()
    }

    private fun initListener() {

        tv_chapter01.setOnClickListener {
            it.nav(R.id.action_homeFragment_to_new_feature)
        }

        tv_chapter02.setOnClickListener {
            it.nav(R.id.action_homeFragment_to_material)
        }

        tv_chapter03.setOnClickListener {
            it.nav(R.id.action_homeFragment_to_graph_03_view)
        }

        tv_chapter07.setOnClickListener {
            it.nav(R.id.graph_07_event_bus)
        }

        tv_chapter10.setOnClickListener{
            it.nav(R.id.action_homeFragment_to_graph_mvp)
        }

        tv_biometric.setOnClickListener {
            it.nav(R.id.action_homeFragment_to_biometricFragment)
        }
    }


}
