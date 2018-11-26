package com.zumepizza.interview.ui

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.zumepizza.interview.model.Topping

/**
 * Created by Joe Wang on 2018/11/25.
 */
class SimpleOneItemAdapter(private val list: List<Topping>) : RecyclerView.Adapter<SimpleOneItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_1, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindText(list[position].name)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindText(text: String){
            itemView.findViewById<TextView>(android.R.id.text1).setText(text)
        }
    }
}

class SimpleItemDecoration: RecyclerView.ItemDecoration(){
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State) {
        outRect.set(1,1,1,1)
    }
}