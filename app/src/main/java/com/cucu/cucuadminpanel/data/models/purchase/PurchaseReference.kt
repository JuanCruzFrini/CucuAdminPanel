package com.cucu.cucuadminpanel.data.models.purchase

import android.os.Parcelable
import com.cucu.cucuadminpanel.application.NullableDateSerializer
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import java.util.Date

@Serializable
@Parcelize
data class PurchaseReference(
    val documentId:String?="",
    val uid:String?="",
    val amount: Int? = 0,
    val productsQuantity:Int? = 0,
    @ServerTimestamp @Serializable(with = NullableDateSerializer::class)
    val date: Date? = null
):Parcelable