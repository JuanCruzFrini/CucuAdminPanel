package com.cucu.cucuadminpanel.data.network.sales

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cucu.cucuadminpanel.application.Constants
import com.cucu.cucuadminpanel.data.models.purchase.Purchase
import com.cucu.cucuadminpanel.data.models.purchase.PurchaseReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class SalesPagingSource @Inject constructor() : PagingSource<QuerySnapshot, Purchase>() {
    override suspend fun load(params: LoadParams<QuerySnapshot>): LoadResult<QuerySnapshot, Purchase> {
        return try {
            val db = FirebaseFirestore.getInstance()

            val query = db.collection(Constants.PURCHASES_REFS_COLL)
                    .orderBy("date", Query.Direction.ASCENDING)
                    .limit(params.loadSize.toLong())

            val currentPage = params.key ?: query.get().await()
            val lastVisibleProduct = currentPage.documents[currentPage.documents.lastIndex]
            val nextPage:QuerySnapshot? =query.startAfter(lastVisibleProduct).get().await()

            val data = mutableListOf<Purchase>()

            currentPage.documents.forEach { document ->
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
                        purchase?.let { data.add(it) }
                    }
                }
            }

            LoadResult.Page(
                data = data,
                prevKey = null,
                nextKey = nextPage
            )
        } catch (e: Exception) {
            Log.d("Errorr", e.message.toString())
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<QuerySnapshot, Purchase>): QuerySnapshot? {
        return null
    }
}
