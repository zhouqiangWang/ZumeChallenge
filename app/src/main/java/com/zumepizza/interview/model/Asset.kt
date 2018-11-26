package com.zumepizza.interview.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Joe Wang on 2018/11/22.
 */

@Parcelize
data class Asset(
    val menu: List<AssetItem>,
    val product_details_page: List<AssetItem>
) : Parcelable