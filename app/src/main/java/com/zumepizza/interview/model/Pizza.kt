package com.zumepizza.interview.model

/**
 * Created by Joe Wang on 2018/11/22.
 */

data class Pizza(
    val id: Int?,
    val isTitle: Boolean,
    val name: String,
    val price: String,
    var num: Int,
    var total: Double,
    val menu_description: String,
    val classifications: Classification?,
    val assets: Asset?,
    val toppings: List<Topping>?
) {
  constructor(isTitle: Boolean, name: String):
      this(0, isTitle, name, "0.0", 0, 0.0, "", null, null, null)
}