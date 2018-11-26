package com.zumepizza.interview.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Joe Wang on 2018/11/22.
 */

@Parcelize
data class Classification(
    val vegetarian: Boolean,
    val gluten_free: Boolean,
    val spicy: Boolean
) : Parcelable