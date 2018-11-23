package com.zumepizza.interview.ui

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.zumepizza.interview.data.API
import com.zumepizza.interview.model.Pizza

/**
 * Created by Joe Wang on 2018/11/22.
 */

class PizzaListViewModel: ViewModel() {
    var liveData: MutableLiveData<Pizza> = MutableLiveData()
    fun getPizzaList(context: Context): MutableLiveData<List<Pizza>> {
        return API.getMenu(context)
    }
}