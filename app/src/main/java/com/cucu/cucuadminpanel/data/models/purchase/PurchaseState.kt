package com.cucu.cucuadminpanel.data.models.purchase

import android.os.Parcelable
import com.cucu.cucuadminpanel.application.NullableDateSerializer
import com.google.firebase.firestore.ServerTimestamp
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import java.util.Date

@Parcelize
@Serializable
sealed class PurchaseState(
    val description:String? = "",
    @ServerTimestamp @Serializable(with = NullableDateSerializer::class)
    val date: Date? = null
) : Parcelable {

    @Parcelize
    @Serializable
    class PendingPayment : PurchaseState("Pago pendiente")
    @Parcelize
    @Serializable
    class PaymentPendingConfirmation : PurchaseState("Pago pendiente de confirmacion")
    @Parcelize
    @Serializable
    class PaymentConfirmed : PurchaseState("Pago confirmado")
    @Parcelize
    @Serializable
    class PreparingProducts : PurchaseState("Preparando productos")
    @Parcelize
    @Serializable
    class ReadyForPickup : PurchaseState("Listo para retirar")
    @Parcelize
    @Serializable
    class Delivered : PurchaseState("Entregado")
    @Parcelize
    @Serializable
    class Cancelled : PurchaseState("Cancelado")
}