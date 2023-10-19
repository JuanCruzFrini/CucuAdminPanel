package com.cucu.cucuadminpanel.data.models.promo

import android.os.Parcelable
import com.cucu.cucuadminpanel.data.models.cart.CartProduct
import com.google.firebase.firestore.Exclude
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class Promo(
    @get:Exclude
    var id:String? = "",
    val name:String?="",
    val description:String?="",
    val price:Int?=0,
    val stock:Int?=0,
    val products: List<CartProduct>? = emptyList()
):Parcelable
