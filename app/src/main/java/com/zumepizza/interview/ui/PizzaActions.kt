package com.zumepizza.interview.ui

import com.zumepizza.interview.model.Pizza

/**
 * Created by Joe Wang on 2018/11/25.
 */

interface PizzaActions{
    fun openPizzaDetial(pizza: Pizza)
    fun addToChart(pizza: Pizza)
}