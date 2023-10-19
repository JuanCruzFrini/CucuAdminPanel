package com.cucu.cucuadminpanel.data.network

import com.cucu.cucuadminpanel.application.Constants
import com.cucu.cucuadminpanel.data.models.promo.Promo
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class PromosDataSource @Inject constructor(
    val productsDataSource: ProductsDataSource,
    private val db: FirebaseFirestore
) {

    suspend fun getAllPromos(): List<Promo>{
        val promos = mutableListOf<Promo>()

        val fetch = db.collection(Constants.PROMOS_COLL).get().await()

        fetch.documents.forEach { document ->
            val promo = document.toObject(Promo::class.java)
            promo?.id = document.id
            promo?.let { promos.add(it) }
        }

      /*  promos.forEach {
            it.products?.forEach {
                cartProduct.product.id = cartProduct.productId
            }
        }*/

        return promos
    }

    suspend fun createPromo(promo: Promo) {
        db.collection(Constants.PROMOS_COLL).add(promo).await()
    }
}