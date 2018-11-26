package com.zumepizza.interview.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley
import com.zumepizza.interview.R
import com.zumepizza.interview.data.ImageLruCache
import com.zumepizza.interview.databinding.ItemPizzaBinding
import com.zumepizza.interview.model.Pizza
import timber.log.Timber

/**
 * Created by Joe Wang on 2018/11/22.
 */
class PizzaListAdapter(
    private val pizzaActions: PizzaActions,
    private val lifecycleOwner: LifecycleOwner
): ListAdapter<Pizza, PizzaViewHolder> (PizzaDiff) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PizzaViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemPizzaBinding = DataBindingUtil.inflate(inflater, R.layout.item_pizza, parent, false)
        return PizzaViewHolder(binding, lifecycleOwner, parent.context, pizzaActions)
    }

    override fun onBindViewHolder(holder: PizzaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

}

object PizzaDiff: DiffUtil.ItemCallback<Pizza>() {
    override fun areItemsTheSame(oldItem: Pizza, newItem: Pizza): Boolean {
        return oldItem.name === newItem.name
    }

    override fun areContentsTheSame(oldItem: Pizza, newItem: Pizza): Boolean {
        return newItem == oldItem
    }

}

class PizzaViewHolder(
    private val binding: ItemPizzaBinding,
    private val lifecycleOwner: LifecycleOwner,
    private val context: Context,
    private val pizzaActions: PizzaActions
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(pizza: Pizza) {
        Timber.d("pizza = " + pizza)
        binding.pizza = pizza
        if (pizza.isTitle) {
            binding.itemPizzaAll.visibility = View.GONE
            binding.tvCategoryName.visibility = View.VISIBLE
        } else {
            binding.itemPizzaAll.visibility = View.VISIBLE
            binding.tvCategoryName.visibility = View.GONE
            binding.price.setText("$" + pizza.price)
            if (pizza.classifications?.spicy != true) {
                binding.tagSpicy.visibility = View.GONE
            }
            if (pizza.classifications?.vegetarian != true) {
                binding.tagVege.visibility = View.GONE
            }
            if (pizza.classifications?.gluten_free != true) {
                binding.tagGl.visibility = View.GONE
            }
            Timber.d("url = " + pizza.assets!!.menu[0].url)
            val requestQ = Volley.newRequestQueue(context)
            val imageLoader = ImageLoader(requestQ, ImageLruCache())
            binding.pizzaCover.setDefaultImageResId(R.mipmap.ic_launcher)
            binding.pizzaCover.setErrorImageResId(R.mipmap.ic_launcher)
            binding.pizzaCover.setImageUrl(pizza.assets!!.menu[0].url, imageLoader)

            binding.itemPizzaAll.setOnClickListener({
                v -> pizzaActions.openPizzaDetial(pizza)
                Toast.makeText(context, "pizza Item clicked", Toast.LENGTH_SHORT)
            })
        }
        binding.setLifecycleOwner(lifecycleOwner)
        binding.executePendingBindings()
    }
}

