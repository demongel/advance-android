package com.shakespace.advanceandroid.chapterp03view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.shakespace.advanceandroid.R
import com.shakespace.advanceandroid.global.TAG
import com.shakespace.advanceandroid.global.nav
import kotlinx.android.synthetic.main.fragment_view.*
import kotlinx.android.synthetic.main.fragment_view_move.*

/**
 * A simple [Fragment] subclass.
 */
class ViewFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        tv_move.setOnClickListener {
            it.nav(R.id.action_viewFragment_to_viewMoveFragment)
        }

        tv_move.setOnTouchListener { v, event ->
            Log.e(this.TAG, "onActivityCreated: ontouch ")
            false
        }

        tv_animator.setOnClickListener {
            it.nav(R.id.action_viewFragment_to_animatorFragment)
        }

        tv_horizontal.setOnClickListener {
            it.nav(R.id.action_viewFragment_to_horizontalFragment)
        }
    }

}
