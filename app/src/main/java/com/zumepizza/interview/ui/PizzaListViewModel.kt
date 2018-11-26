package com.zumepizza.interview.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zumepizza.interview.data.API
import com.zumepizza.interview.model.Cart
import com.zumepizza.interview.model.Pizza

/**
 * Created by Joe Wang on 2018/11/22.
 */

class PizzaListViewModel: ViewModel() {
    var liveCart: MutableLiveData<Cart> = MutableLiveData()
    init {
        val defaultCart = Cart(0,0.0, HashMap())
        liveCart.value = defaultCart
    }
    fun getPizzaList(context: Context): MutableLiveData<List<Pizza>> {
        return API.getMenu(context)
    }
}