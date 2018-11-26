package com.zumepizza.interview.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Joe Wang on 2018/11/25.
 */

@Parcelize
data class Cart(
    var amount: Int,
    var totalPrice: Double,
    var details: MutableMap<String, Int>
) : Parcelable {
    constructor(cart: Cart) : this(cart.amount, cart.totalPrice, cart.details)
}