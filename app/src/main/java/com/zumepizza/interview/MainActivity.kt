package com.zumepizza.interview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import timber.log.Timber
import androidx.lifecycle.ViewModelProviders
import com.zumepizza.interview.ui.PizzaListViewModel
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {

    lateinit var pizzaListVM: PizzaListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.tag("Zumepizza")
        setContentView(R.layout.activity_main)

        pizzaListVM = ViewModelProviders.of(this).get(PizzaListViewModel::class.java)

        Timber.d("begin subscribeListDataObserver")
        subscribeListDataObserver()
    }

    private fun subscribeListDataObserver() {
        pizzaListVM.getPizzaList(this).observe(this, Observer {
            pizzaList ->
            Timber.d("pizzaList num = " + pizzaList.size)
        })
    }
}
