package com.cucu.cucuadminpanel.data.models.purchase

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class PurchaseReference(
    val documentId:String?="",
    val uid:String?="",
):Parcelable