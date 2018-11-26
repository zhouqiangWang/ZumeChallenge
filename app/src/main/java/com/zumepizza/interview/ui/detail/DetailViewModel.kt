package com.zumepizza.interview.ui.detail

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zumepizza.interview.model.Pizza

class DetailViewModel : ViewModel() {
    private var mPizza:MutableLiveData<Pizza> = MutableLiveData()

    fun setPizza(pizza: Pizza) {
        mPizza.value = pizza
    }

    fun getPizza(): MutableLiveData<Pizza> {
        return mPizza
    }
}
