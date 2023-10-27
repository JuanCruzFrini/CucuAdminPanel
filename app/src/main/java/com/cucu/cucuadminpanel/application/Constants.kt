package com.cucu.cucuadminpanel.application

import com.cucu.cucuadminpanel.data.models.purchase.PurchaseStateDescription

object Constants {

    //Collections
    const val PRODUCTS_COLL = "products"
    const val PURCHASES_COLL = "purchases"
    const val PURCHASES_REFS_COLL = "purchases_references"
    const val USERS_COLL = "users"
    const val NOTIFICATIONS_COLL = "notifications"
    const val PROMOS_COLL = "promotions"

    //Product parameters
    const val CATEGORY = "category"
    const val PRODUCT_ID = "productId"
    const val DATE = "date"
    const val QUANTITY = "quantity"
    const val SEEN_TIMES = "seenTimes"
    const val SOLD_TIMES = "soldTimes"
    const val FAV_TIMES = "favTimes"

    val purchaseStates = listOf(
        PurchaseStateDescription("1- Pago pendiente:","El pago no ha sido realizado, o fue efectuado pero la tienda aún no ha verificado que se haya efectuado correctamente"),
        PurchaseStateDescription("2- Pago pendiente de confirmacion:","La tienda está verificado el pago"),
        PurchaseStateDescription("3- Pago confirmado:","El pago ha sido verificado con exito"),
        PurchaseStateDescription("4- Preparando productos:","La tienda está está preparando tu compra para que luego puedas retirarla"),
        PurchaseStateDescription("5- Listo para retirar:","Tu compra está preparada para que la retires en la tienda"),
        PurchaseStateDescription("6- Entregado:","Haz retirado tu compra de la tienda"),
        PurchaseStateDescription("7- Cancelado:","Tu compra ha sido cancelada, esto puede ocurrir por parte de la tienda por falta de stock, o bien puedes cancelarla antes de efectuar el pago"),
    )
}