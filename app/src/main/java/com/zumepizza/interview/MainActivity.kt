package com.zumepizza.interview

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        startActivity(intent)
    }

    override fun addToChart(pizza: Pizza) {
        TODO(
            "not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    lateinit var pizzaListVM: PizzaListViewModel
    lateinit var mRecyclerView: RecyclerView
    lateinit var mLayoutManager: RecyclerView.LayoutManager
    lateinit var mAdapter: PizzaListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag("Zumepizza")
        setContentView(R.layout.activity_main)

        mRecyclerView = findViewById(R.id.recycle_view)
        mLayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        mRecyclerView.layoutManager = mLayoutManager

        mAdapter = PizzaListAdapter(this, this)
        mRecyclerView.adapter = mAdapter

        pizzaListVM = ViewModelProviders.of(this).get(PizzaListViewModel::class.java)

        Timber.d("begin subscribeListDataObserver")
        subscribeListDataObserver()
    }

    private fun subscribeListDataObserver() {
        pizzaListVM.getPizzaList(this).observe(this, Observer {
            pizzaList ->
            Timber.d("pizzaList num = " + pizzaList.size)
            mAdapter.submitList(pizzaList)
        })
    }
}
