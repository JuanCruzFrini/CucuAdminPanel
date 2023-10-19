package com.cucu.cucuadminpanel.application

import android.net.Uri
import com.cucu.cucuadminpanel.data.models.Product
import com.cucu.cucuadminpanel.data.models.promo.Promo
import com.cucu.cucuadminpanel.data.models.purchase.PurchaseReference
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement

sealed class Routes(val route:String) {
    //mainScreen
    object Main: Routes("main")

    object ProductDetail: Routes("product_detail/{product}"){
        //Encodeamos el objeto a Json para que sea valido en la URL
        //En este caso tambien hay que encodearlo dentro de una Uri por que el objeto contiene
        //un parametro que es una url y hace que falle el la URL
        fun createRoute(product: Product) = "product_detail/${Uri.encode(Json.encodeToJsonElement(product).toString())}"
    }

    object EditProduct: Routes("edit_product/{product}"){
        fun createRoute(product: Product) = "edit_product/${Uri.encode(Json.encodeToJsonElement(product).toString())}"
    }

    //navDrawer
    object Products: Routes("products")
    object Sales: Routes("sales")
    object Promos: Routes("promos")
    object Combos: Routes("combos")
    object Stats: Routes("stats")
    object Discounts: Routes("discounts")

    object AddProduct: Routes("add_product")
    object AddPromo: Routes("add_promo")

    object PromoDetail: Routes("promo_detail/{promotion}"){
        fun createRoute(promo: Promo) = "promo_detail/${Uri.encode(Json.encodeToJsonElement(promo).toString())}"
    }

    /*object PurchaseDetail:Routes("purchase_detail/{purchase}"){
        fun createRoute(purchase: Purchase) = "purchase_detail/${Uri.encode(Json.encodeToJsonElement(purchase).toString())}"
    }*/

    /*object PurchaseDetail: Routes("purchase_detail/{purchaseId}"){
        fun createRoute(purchaseId: String) = "purchase_detail/$purchaseId"
    }*/

    object PurchaseDetail: Routes("purchase_detail/{purchaseReference}"){
        fun createRoute(purchaseReference: PurchaseReference) = "purchase_detail/${Uri.encode(Json.encodeToJsonElement(purchaseReference).toString())}"
    }

     object Category: Routes("category/{category}"){
         fun createRoute(category: String) = "category/$category"
     }
}