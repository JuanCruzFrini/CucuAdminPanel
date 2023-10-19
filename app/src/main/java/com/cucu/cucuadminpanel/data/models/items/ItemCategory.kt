package com.cucu.cucuadminpanel.data.models.items

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class ItemCategory(val category: String? = ""):Parcelable
