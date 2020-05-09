package com.shakespace.advanceandroid.chapter01newfeature

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.shakespace.advanceandroid.R
import com.shakespace.advanceandroid.chapter01newfeature.adapter.NumberAdapter
import com.shakespace.advanceandroid.chapter01newfeature.adapter.OnRecyclerViewItemClickListener
import com.shakespace.advanceandroid.chapter01newfeature.itemdecoration.GradientItemDecoration
import kotlinx.android.synthetic.main.fragment_recycler_view.*

/**
 * A simple [Fragment] subclass.
 */
class RecyclerViewFragment : Fragment() {

    lateinit var data: MutableList<Int>
    lateinit var adapter: NumberAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 需要设置该选项，否则不会调用 onCreateOptionsMenu
        setHasOptionsMenu(true);

        createList()
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recycler_view, container, false)
    }

    private fun createList() {
        data = mutableListOf()
        for (i in 100..200) {
            data.add(i)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter = NumberAdapter(data, requireContext())
        recycler_view.adapter = adapter

        // 分隔线
//        recycler_view.addItemDecoration(
//            DividerItemDecoration(
//                requireContext(),
//                DividerItemDecoration.VERTICAL
//            )
//        )

        recycler_view.addItemDecoration(GradientItemDecoration())
        recycler_view.itemAnimator = DefaultItemAnimator()
        adapter.listener = object : OnRecyclerViewItemClickListener {
            override fun onClick(view: View, position: Int) {
                Snackbar.make(view, data[position].toString(), Snackbar.LENGTH_SHORT).show()
            }

            override fun onLongClick(view: View, position: Int): Boolean {
                AlertDialog.Builder(requireContext()).apply {
                    setTitle("确认删除么？")
                    setNegativeButton("取消", null)
                    setPositiveButton("确定") { _, _ ->
                        data.removeAt(position)
                        adapter.notifyItemRemoved(position)
                    }
                }.show()
                return true
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.linear -> {
                recycler_view.layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
            R.id.grid -> {
                recycler_view.layoutManager =
                    GridLayoutManager(requireContext(), 4)
            }
            R.id.stag -> {
                recycler_view.layoutManager =
                    StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
