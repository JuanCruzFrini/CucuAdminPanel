package com.cucu.cucuadminpanel.data.network

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import com.cucu.cucuadminpanel.application.Constants
import com.cucu.cucuadminpanel.application.getBitmapFromUri
import com.cucu.cucuadminpanel.data.models.Product
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.tasks.await
import java.io.ByteArrayOutputStream
import javax.inject.Inject

class ProductsDataSource @Inject constructor(
    @ApplicationContext private val context: Context,
    private val db: FirebaseFirestore,
    private val storage:FirebaseStorage
) {

    suspend fun getAllProducts(): List<Product> {
        val products = mutableListOf<Product>()

        val fetch = db.collection(Constants.PRODUCTS_COLL).get().await()

        fetch.documents.forEach{ document ->
            val product = document.toObject(Product::class.java)
            product?.id = document.id
            product?.let { products.add(it) }
        }

        return products
    }

    suspend fun getDiscounts(): List<Product> {
        val products = mutableListOf<Product>()

        val fetch = db.collection(Constants.PRODUCTS_COLL).get().await()

        fetch.documents.forEach{ document ->
            val product = document.toObject(Product::class.java)
            product?.id = document.id

            if (product!= null && product.isDiscount()) products.add(product)
        }

        return products
    }

    suspend fun getProductById(productId: String): Product {
        var product = Product()

        val document = db.collection(Constants.PRODUCTS_COLL).document(productId).get().await()

        val productObject = document.toObject(Product::class.java)
        productObject?.let {
            product = it
            product.id = document.id
        }

        return product
    }

    private suspend fun addNewProduct(product: Product){
        db.collection(Constants.PRODUCTS_COLL).add(product).await()
    }

    suspend fun uploadImage(product: Product, image:Uri?){
        getBitmapFromUri(context, image!!)?.let { bitmap ->
            val productsRef = storage.reference.child(Constants.PRODUCTS_COLL)
            //Reduce the weight of the image to upload
            val baos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos)

            //Upload the compressed image
            val task = productsRef.child(image.lastPathSegment.toString()).putBytes(baos.toByteArray()).await()

            val imageUri = task.storage.downloadUrl.await()
            product.img = imageUri.toString()
            addNewProduct(product)
        }
    }

    /*suspend fun getCategories(): List<ItemCategory> {
        val categories = mutableListOf<ItemCategory>()

        val fetch = db.collection(Constants.CATEGORIES_COLL)
            .orderBy(Constants.CATEGORY, Query.Direction.ASCENDING)
            .get()
            .await()

        fetch.documents.forEach{ document ->
            val category = document.toObject(ItemCategory::class.java)
            category?.let { categories.add(it) }
        }

        return categories
    }*/

    suspend fun updateProduct(product: Product) {
        product.id?.let { id ->
            db.collection(Constants.PRODUCTS_COLL)
                .document(id)
                .update(
                    mapOf(
                        "name" to product.name,
                        "newPrice" to product.newPrice,
                        "oldPrice" to product.oldPrice,
                        "stock" to product.stock,
                        "description" to product.description,
                        "category" to product.category,
                        "code" to product.code,
                    )
                ).await()
        }
    }
}