package com.cucu.cucuadminpanel.data.models.cart

import android.os.Parcelable
import com.cucu.cucuadminpanel.data.models.Product
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable

@Serializable
@Parcelize
data class CartProduct(
    var documentId:String?="",
    var productId:String?="",
    @Contextual
    var product: Product = Product(),
    var quantity:Int?=0
) : Parcelable
