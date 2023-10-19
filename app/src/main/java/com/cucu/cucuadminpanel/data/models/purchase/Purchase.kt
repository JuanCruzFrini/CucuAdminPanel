package com.cucu.cucuadminpanel.data.models.purchase

import android.os.Parcelable
import com.cucu.cucuadminpanel.application.NullableDateSerializer
import com.cucu.cucuadminpanel.data.models.cart.CartProduct
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import java.util.Date
import kotlin.math.roundToInt

@Parcelize
@Serializable
data class Purchase(
    @get:Exclude
    var id:String? = "",
    var userId:String? = "",
    val products:List<CartProduct>? = emptyList(),
    val state: String? = PurchaseState.PendingPayment().description,
    @ServerTimestamp @Serializable(with = NullableDateSerializer::class)
    val date:Date? = null
) : Parcelable {

    fun getAmount(): Int {
        var totalPrice = 0.00
        products?.forEach { cart ->
            val totalPerProduct = cart.product.newPrice!! * cart.quantity!!
            totalPrice += totalPerProduct
        }
        return totalPrice.roundToInt()
    }

    fun getQuantity(): Int {
        var totalQuantity = 0
        products?.forEach{ cart ->
            totalQuantity += cart.quantity!!
        }
        return totalQuantity
    }
}