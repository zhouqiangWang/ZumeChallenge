package com.zumepizza.interview.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zumepizza.interview.R
import com.zumepizza.interview.model.Pizza

/**
 * Created by Joe Wang on 2018/11/22.
 */
class PizzaListAdapter(): ListAdapter<Pizza, PizzaViewHolder> (PizzaDiff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PizzaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return PizzaViewHolder(inflater.inflate(R.layout.abc_action_bar_title_item, parent, false))
    }

    override fun onBindViewHolder(holder: PizzaViewHolder, position: Int) {
        if (holder is PizzaViewHolder) {
            holder.bind()
        }
    }

}

object PizzaDiff: DiffUtil.ItemCallback<Pizza>() {
    override fun areItemsTheSame(oldItem: Pizza, newItem: Pizza): Boolean {
        return (oldItem.isTitle == newItem.isTitle) && (oldItem.name === newItem.name)
    }

    override fun areContentsTheSame(oldItem: Pizza, newItem: Pizza): Boolean {
        return newItem == oldItem
    }

}

class PizzaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    init {
        val title: TextView = itemView.findViewById(R.id.recycle_view)
    }
    fun bind() {

    }
}
