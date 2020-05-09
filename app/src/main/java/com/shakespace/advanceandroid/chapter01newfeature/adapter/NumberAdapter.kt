package com.shakespace.advanceandroid.chapter01newfeature.adapter

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * created by  shakespace
 * 2020/5/6  21:12
 */
class NumberAdapter(private val list: MutableList<Int>, private val context: Context) :
    RecyclerView.Adapter<NumberAdapter.NumberViewHolder>() {

    var listener: OnRecyclerViewItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
        val view =
            LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return NumberViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        holder.textView.text = list[position].toString()
        holder.textView.gravity = Gravity.CENTER
    }


    inner class NumberViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        var textView: TextView = view as TextView

        init {
            textView.setOnClickListener {
                /**
                 * 注意@layoutPosition 和 adapterPosition 的区别， adapterPosition可能返回-1
                 */
                listener?.onClick(it, layoutPosition)
            }

            textView.setOnLongClickListener {
                listener?.onLongClick(it, layoutPosition) ?: false
            }

        }
    }

}
