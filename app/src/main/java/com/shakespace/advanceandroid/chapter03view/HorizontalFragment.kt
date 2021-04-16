package com.shakespace.advanceandroid.chapter03view

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.shakespace.advanceandroid.R
import kotlinx.android.synthetic.main.fragment_horizontal.*

class HorizontalFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_horizontal, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        for (i in 0..2) {

            val listView = ListView(requireContext())
            listView.adapter = ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                getDataList()
            )
            listView.setBackgroundColor(
                Color.rgb(
                    255 / (i + 1),
                    255 / (i * i + 1),
                    255 / (i * i * i * i + 1)
                )
            )
            horizontal_view.addView(listView)
        }
    }

    private fun getDataList(): MutableList<String> {
        val list = mutableListOf<String>()
        for (i in 0..80) {
            list.add(i.toString())
        }
        return list
    }
}