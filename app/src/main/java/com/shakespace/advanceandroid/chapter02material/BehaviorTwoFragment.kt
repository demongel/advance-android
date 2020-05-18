package com.shakespace.advanceandroid.chapter02material

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.shakespace.advanceandroid.MainActivity
import com.shakespace.advanceandroid.R
import com.shakespace.advanceandroid.chapter01newfeature.adapter.NumberAdapter
import kotlinx.android.synthetic.main.fragment_behavior_two.*
import kotlinx.android.synthetic.main.fragment_coordinator.toolbar

/**
 * A simple [Fragment] subclass.
 */
class BehaviorTwoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_behavior_two, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as MainActivity).setToolbar(toolbar)
        val data = mutableListOf<Int>()
        for (i in 100..200) {
            data.add(i)
        }
        recycler_view.adapter = NumberAdapter(data, requireContext())
        recycler_view.addItemDecoration(DividerItemDecoration(requireContext(),LinearLayoutManager.VERTICAL))
    }

    override fun onDestroy() {
        (requireActivity() as MainActivity).setToolbar(null)
        super.onDestroy()
    }

}
