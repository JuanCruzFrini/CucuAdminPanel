package com.cucu.cucuadminpanel.data.network

import com.cucu.cucuadminpanel.application.Constants
import com.cucu.cucuadminpanel.data.models.Product
import com.cucu.cucuadminpanel.data.models.Stat
import com.cucu.cucuadminpanel.data.models.purchase.PurchaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StatsDataSource @Inject constructor(
    private val db: FirebaseFirestore
) {
    suspend fun getProductsBy(field:String): List<Product>{
        val result = mutableListOf<Product>()

        val fetch = db.collection(Constants.PRODUCTS_COLL)
            .orderBy(field, Query.Direction.DESCENDING)
            .limit(20)
            .get()
            .await()

        fetch.documents.forEach { documnet ->
            val product = documnet.toObject(Product::class.java)
            product?.id = documnet.id
            product?.let { result.add(it) }
        }

        return result
    }

    suspend fun getStat(): Stat {
        val collRef =  db.collection(Constants.PURCHASES_REFS_COLL)

        val sales = collRef.get().await().size()
        var totalAmount = 0.0
        var totalQuant = 0.0

        collRef.get().await().documents.forEach {
            val sale = it.toObject(PurchaseReference::class.java)
            sale?.amount?.let { totalAmount += it }
            sale?.productsQuantity?.let { totalQuant += it }
        }

        val averageAmount = "%.2f".format(totalAmount / sales).toFloat()
        val averageQuantity = "%.2f".format(totalQuant / sales).toFloat()

        val stat = Stat(
            totalSales = sales,
            averageAmount = averageAmount,
            averageQuantity = averageQuantity,
        )

        return stat
    }
}