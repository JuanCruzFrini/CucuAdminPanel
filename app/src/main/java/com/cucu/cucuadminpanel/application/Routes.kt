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

    //products routes
    object Products: Routes("products")
    object AddProduct: Routes("add_product")
    object ProductDetail: Routes("product_detail/{product}"){
        //Encodeamos el objeto a Json para que sea valido en la URL
        //En este caso tambien hay que encodearlo dentro de una Uri por que el objeto contiene
        //un parametro que es una url y hace que falle el la URL
        fun createRoute(product: Product) = "product_detail/${Uri.encode(Json.encodeToJsonElement(product).toString())}"
    }
    object EditProduct: Routes("edit_product/{product}"){
        fun createRoute(product: Product) = "edit_product/${Uri.encode(Json.encodeToJsonElement(product).toString())}"
    }

    object Sales: Routes("sales")
    object Combos: Routes("combos")
    object Stats: Routes("stats")
    object Discounts: Routes("discounts")

    //Promos routes
    object Promos: Routes("promos")
    //object AddPromo: Routes("add_promo")
    object AddPromo: Routes("add_promo/{promotion}"){
        fun createRoute(promo: Promo) = "add_promo/${Uri.encode(Json.encodeToJsonElement(promo).toString())}"
    }

    //object ChoosePromoProducts: Routes("choose_promo_products")
    object PromoDetail: Routes("promo_detail/{promotion}"){
        fun createRoute(promo: Promo) = "promo_detail/${Uri.encode(Json.encodeToJsonElement(promo).toString())}"
    }
    object EditPromo: Routes("edit_promo/{promotion}"){
        fun createRoute(promotion: Promo) = "edit_promo/${Uri.encode(Json.encodeToJsonElement(promotion).toString())}"
    }
    object ChoosePromoProducts: Routes("choose_promo_products/{promotion}"){
        fun createRoute(promotion: Promo) = "choose_promo_products/${Uri.encode(Json.encodeToJsonElement(promotion).toString())}"
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