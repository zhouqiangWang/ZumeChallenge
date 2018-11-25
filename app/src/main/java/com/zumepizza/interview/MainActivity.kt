package com.zumepizza.interview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import timber.log.Timber
import androidx.lifecycle.ViewModelProviders
import com.zumepizza.interview.ui.PizzaListViewModel
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.zumepizza.interview.ui.PizzaListAdapter

class MainActivity : AppCompatActivity() {

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

        mAdapter = PizzaListAdapter(this)
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
