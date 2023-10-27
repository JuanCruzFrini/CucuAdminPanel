package com.cucu.cucuadminpanel.data.network.sales

import com.cucu.cucuadminpanel.application.Constants
import com.cucu.cucuadminpanel.data.models.Notification
import com.cucu.cucuadminpanel.data.models.purchase.Purchase
import com.cucu.cucuadminpanel.data.models.purchase.PurchaseReference
import com.cucu.cucuadminpanel.data.models.purchase.PurchaseState
import com.cucu.cucuadminpanel.data.network.ProductsDataSource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SalesDataSource @Inject constructor(
    private val db: FirebaseFirestore,
    private val productsDataSource: ProductsDataSource
) {

    suspend fun getSales(): List<Purchase> {
        val purchases = mutableListOf<Purchase>()

        val fetch = db.collection(Constants.PURCHASES_REFS_COLL).get().await()

        fetch.documents.forEach { document ->
            val purchaseRef = document.toObject(PurchaseReference::class.java)
            purchaseRef?.let { ref ->
                if (ref.uid != null && ref.documentId != null) {
                    val purchaseDoc = db.collection(Constants.USERS_COLL)
                        .document(ref.uid)
                        .collection(Constants.PURCHASES_COLL)
                        .document(ref.documentId)
                        .get().await()

                    val purchase = purchaseDoc.toObject(Purchase::class.java)
                    purchase?.id = ref.documentId
                    purchase?.userId = ref.uid
                    purchase?.let { purchases.add(it) }
                }
            }
        }

        return purchases.sortedByDescending { it.date }
    }

    suspend fun getSaleById(purchaseRef: PurchaseReference?): Purchase {
        var purchase = Purchase()

        val purchaseDoc = db
            .collection(Constants.USERS_COLL)
            .document(purchaseRef?.uid.toString())
            .collection(Constants.PURCHASES_COLL)
            .document(purchaseRef?.documentId.toString())
            .get().await()

        purchaseDoc.toObject(Purchase::class.java)?.let { purchase = it }
        purchase.id = purchaseDoc.id

        purchase.products?.forEach { cartProduct ->
            cartProduct.product.id = cartProduct.productId
        }

        return purchase
    }

    suspend fun cancelSale(purchaseRef: PurchaseReference?, cancelReason: String){
        purchaseRef?.let { ref ->
            db.collection(Constants.USERS_COLL)
                .document(ref.uid.toString())
                .collection(Constants.PURCHASES_COLL)
                .document(ref.documentId.toString())
                .update("state", PurchaseState.Cancelled().description)
                .await()

            notifyUserAboutPurchaseStateChange(ref, cancelReason)
            reloadStock(getSaleById(purchaseRef))
        }
    }

    private suspend fun notifyUserAboutPurchaseStateChange(
        purchaseRef: PurchaseReference,
        cancelReason: String? = ""
    ) {
        db.collection(Constants.USERS_COLL)
            .document(purchaseRef.uid.toString())
            .collection(Constants.NOTIFICATIONS_COLL)
            .add(
                Notification(
                    title = "Hemos cancelado tu compra",
                    content = cancelReason,
                    purchaseId = purchaseRef.documentId
                )
            ).await()
    }

    private fun reloadStock(purchase: Purchase) {
        purchase.products?.forEach { cartProduct ->
            cartProduct.productId?.let { id ->

                CoroutineScope(Dispatchers.IO).launch {
                    var newStock:Int? = 0

                    async {
                        val product = productsDataSource.getProductById(id)
                        newStock = product.stock?.plus(cartProduct.quantity!!)
                    }.await()

                    db.collection(Constants.PRODUCTS_COLL).document(id)
                        .update("stock", newStock).await()
                }
            }
        }
    }
}