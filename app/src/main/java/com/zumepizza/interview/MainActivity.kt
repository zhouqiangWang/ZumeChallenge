package com.zumepizza.interview

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zumepizza.interview.databinding.ActivityMainBinding
import com.zumepizza.interview.model.Cart
import com.zumepizza.interview.model.Pizza
import com.zumepizza.interview.ui.PizzaActions
import com.zumepizza.interview.ui.PizzaListAdapter
import com.zumepizza.interview.ui.PizzaListViewModel
import timber.log.Timber

class MainActivity : AppCompatActivity(), PizzaActions {
    override fun openPizzaDetial(pizza: Pizza) {
        val intent = Intent()
        intent.setClass(this, Detail::class.java)
        intent.putExtra("pizza", pizza)
        startActivityForResult(intent, 1)
    }

    override fun addToChart(pizza: Pizza) {
        val oldCart = pizzaListVM.liveCart.value!!
        val newCart: Cart = Cart(oldCart.amount+1, oldCart.totalPrice + pizza.price.toDouble(), oldCart.details)

        if (newCart.details.containsKey(pizza.name)) {
            newCart.details[pizza.name] = newCart.details[pizza.name]!! + 1
        } else {
            newCart.details[pizza.name] = 1
        }

        Timber.d("amount = " + newCart)
        pizzaListVM.liveCart.value = newCart
    }

    lateinit var pizzaListVM: PizzaListViewModel
    lateinit var mRecyclerView: RecyclerView
    lateinit var mLayoutManager: RecyclerView.LayoutManager
    lateinit var mAdapter: PizzaListAdapter
    lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag("Zumepizza")
        setContentView(R.layout.activity_main)

        pizzaListVM = ViewModelProviders.of(this).get(PizzaListViewModel::class.java)

        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        mainBinding.cart = pizzaListVM.liveCart.value

        mRecyclerView = findViewById(R.id.recycle_view)
        mLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        mRecyclerView.layoutManager = mLayoutManager

        mAdapter = PizzaListAdapter(this, this)
        mRecyclerView.adapter = mAdapter


        Timber.d("begin subscribeListDataObserver")
        subscribeListDataObserver()
    }

    private fun subscribeListDataObserver() {
        pizzaListVM.getPizzaList(this).observe(this, Observer {
            pizzaList ->
            Timber.d("pizzaList num = " + pizzaList.size)
            mAdapter.submitList(pizzaList)
        })
        pizzaListVM.liveCart.observe(this, Observer {
            cart ->
            if (cart.amount > 0) {
                mainBinding.frameCart.visibility = View.VISIBLE
                mainBinding.tvTotal.text = cart.amount.toString()
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(resultCode == 2) {
            Timber.d("resultCode = %d, amount = %d", resultCode, data!!.getIntExtra("amount", 0))
            val oldCart = pizzaListVM.liveCart.value!!
            val newCart: Cart = Cart(oldCart)
            val addAmount = data.getIntExtra("amount", 0)
            val addTotal = data.getDoubleExtra("totalPrice", 0.0)
            val addName = data.getStringExtra("name")

            newCart.amount += addAmount
            newCart.totalPrice += addTotal

            if (newCart.details.containsKey(addName)) {
                newCart.details[addName] = newCart.details[addName]!! + 1
            } else {
                newCart.details[addName] = 1
            }
            pizzaListVM.liveCart.value = newCart
        } else {
            Timber.d("requestCode = %d, resultCode = %d", requestCode, resultCode)
        }
    }
}
