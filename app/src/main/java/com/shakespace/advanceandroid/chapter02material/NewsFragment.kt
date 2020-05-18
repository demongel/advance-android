package com.shakespace.advanceandroid.chapter02material

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.shakespace.advanceandroid.R
import com.shakespace.advanceandroid.global.TAG
import kotlinx.android.synthetic.main.fragment_news.*

/**
 * A simple [Fragment] subclass.
 */
class NewsFragment : Fragment() {

    companion object {
        fun newInstance(title: String = ""): NewsFragment {
            val newsFragment = NewsFragment()
            if (title.isNotEmpty()) {
                newsFragment.arguments = Bundle().apply {
                    putString("title", title)
                }
            }
            return newsFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val title = arguments?.getString("title")
        title?.let {
            content.text = it
        }

        Log.e(this.TAG, "onActivityCreated: $title ")

    }

}
