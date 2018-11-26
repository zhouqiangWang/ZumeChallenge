package com.zumepizza.interview.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Joe Wang on 2018/11/22.
 */

@Parcelize
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
) : Parcelable {
  constructor(isTitle: Boolean, name: String):
      this(0, isTitle, name, "0.0", 0, 0.0, "", null, null, null)
}