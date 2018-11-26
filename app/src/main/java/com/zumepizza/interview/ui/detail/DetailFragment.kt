package com.zumepizza.interview.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.Volley
import com.zumepizza.interview.MainActivity
import com.zumepizza.interview.R
import com.zumepizza.interview.data.ImageLruCache
import com.zumepizza.interview.databinding.DetailFragmentBinding
import com.zumepizza.interview.model.Pizza
import com.zumepizza.interview.ui.PizzaListViewModel
import com.zumepizza.interview.ui.SimpleItemDecoration
import com.zumepizza.interview.ui.SimpleOneItemAdapter
import timber.log.Timber
import kotlin.math.max
import kotlin.math.min

class DetailFragment : Fragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }

    lateinit var mPizza:Pizza
    private lateinit var viewModel: DetailViewModel
    private lateinit var mBinding: DetailFragmentBinding
    private var mAmount: Int = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.detail_fragment, container, false)
        mBinding.pizza = mPizza
        mBinding.setLifecycleOwner(viewLifecycleOwner)

        val requestQ = Volley.newRequestQueue(context)
        val imageLoader = ImageLoader(requestQ, ImageLruCache())
        val listener = ImageLoader.getImageListener(mBinding.imgDetail, R.mipmap.zume_pizza_logo, R.mipmap.zume_pizza_logo)
        Timber.d("image url = " + mPizza.assets!!.product_details_page[0].url)
        imageLoader.get(mPizza.assets!!.product_details_page[0].url, listener)

        val layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)

        mBinding.recycleToppings.layoutManager = layoutManager
        val adapter = SimpleOneItemAdapter(mPizza.toppings!!)
        mBinding.recycleToppings.adapter = adapter
        mBinding.recycleToppings.addItemDecoration(SimpleItemDecoration())

        mBinding.scrollView.smoothScrollTo(0, 20)

        // ADD TO ORDER feature
        mBinding.btnBuy.setOnClickListener {
            it ->
            Timber.d("ADD TO ORDER")
            val resultIntent = Intent()
            resultIntent.putExtra("amount", mAmount)
            resultIntent.putExtra("totalPrice", mPizza.price.toDouble() * mAmount)
            resultIntent.putExtra("name", mPizza.name)
            activity!!.setResult(2, resultIntent)
            activity?.finish()
        }

        mBinding.btnBuy.setText("ADD " + mAmount + " TO ORDER")
        // plus minus buttons
        mBinding.btnMinus.setOnClickListener {
            v ->
            mAmount = max(0, mAmount-1)
            mBinding.tvAmount.text = mAmount.toString()
            mBinding.btnBuy.setText("ADD " + mAmount + " TO ORDER")
        }
        mBinding.btnPlus.setOnClickListener {
            v ->
            mAmount ++
            mBinding.tvAmount.text = mAmount.toString()
            mBinding.btnBuy.setText("ADD " + mAmount + " TO ORDER")
        }
        if (mPizza.classifications?.spicy != true) {
            mBinding.tagSpicy.visibility = View.GONE
        }
        if (mPizza.classifications?.vegetarian != true) {
            mBinding.tagVege.visibility = View.GONE
        }
        if (mPizza.classifications?.gluten_free != true) {
            mBinding.tagGl.visibility = View.GONE
        }

        mBinding.executePendingBindings()

        return mBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        // TODO: Use the ViewModel
        viewModel.setPizza(mPizza)

        viewModel.getPizza().observe(this, Observer {
            it ->
            mBinding.pizza = it

            mBinding.tvPrice.setText("$" + it.price)
            mBinding.executePendingBindings()
        })
    }

}
