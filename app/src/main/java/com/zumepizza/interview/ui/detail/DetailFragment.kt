package com.zumepizza.interview.ui.detail

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
import com.zumepizza.interview.R
import com.zumepizza.interview.data.ImageLruCache
import com.zumepizza.interview.databinding.DetailFragmentBinding
import com.zumepizza.interview.model.Pizza
import com.zumepizza.interview.ui.SimpleItemDecoration
import com.zumepizza.interview.ui.SimpleOneItemAdapter
import timber.log.Timber

class DetailFragment : Fragment() {

    companion object {
        fun newInstance() = DetailFragment()
    }

    lateinit var mPizza:Pizza
    private lateinit var viewModel: DetailViewModel
    private lateinit var mBinding: DetailFragmentBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.detail_fragment, container, false)
        mBinding.pizza = mPizza
        mBinding.setLifecycleOwner(viewLifecycleOwner)

        val requestQ = Volley.newRequestQueue(context)
        val imageLoader = ImageLoader(requestQ, ImageLruCache())
        val listener = ImageLoader.getImageListener(mBinding.imgDetail, R.mipmap.ic_launcher, R.mipmap.ic_launcher)
        Timber.d("image url = " + mPizza.assets!!.product_details_page[0].url)
        imageLoader.get(mPizza.assets!!.product_details_page[0].url, listener)

        val layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)

        mBinding.recycleToppings.layoutManager = layoutManager
        val adapter = SimpleOneItemAdapter(mPizza.toppings!!)
        mBinding.recycleToppings.adapter = adapter
        mBinding.recycleToppings.addItemDecoration(SimpleItemDecoration())


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
