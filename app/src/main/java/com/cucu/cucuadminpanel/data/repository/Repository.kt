package com.cucu.cucuadminpanel.data.repository

import android.net.Uri
import com.cucu.cucuadminpanel.data.fakedatasources.FakeDataSource
import com.cucu.cucuadminpanel.data.models.Product
import com.cucu.cucuadminpanel.data.models.promo.Promo
import com.cucu.cucuadminpanel.data.models.purchase.PurchaseReference
import com.cucu.cucuadminpanel.data.network.ProductsDataSource
import com.cucu.cucuadminpanel.data.network.PromosDataSource
import com.cucu.cucuadminpanel.data.network.SalesDataSource
import javax.inject.Inject

class Repository @Inject constructor(
    private val fakeDataSource: FakeDataSource,
    private val productsDataSource: ProductsDataSource,
    private val salesDataSource: SalesDataSource,
    private val promosDataSource: PromosDataSource,
) {

    //products
    suspend fun getAllProducts() = productsDataSource.getAllProducts()//fakeDataSource.productsFakeList
    suspend fun getAllDiscounts() = productsDataSource.getDiscounts()//fakeDataSource.productsFakeList.filter { it.isDiscount() }
    fun getCategories() = fakeDataSource.categoriesStringList()//productsDataSource.getCategories()
    suspend fun updateProduct(product: Product) = productsDataSource.updateProduct(product)
    suspend fun addProduct(product: Product, image:Uri) = productsDataSource.uploadImage(product,image)

    //sales
    suspend fun getSales() = salesDataSource.getSales()
    suspend fun getSaleById(purchaseRef: PurchaseReference?) = salesDataSource.getSaleById(purchaseRef)
    suspend fun cancelSale(purchaseRef: PurchaseReference?, cancelReason: String) = salesDataSource.cancelSale(purchaseRef, cancelReason)

    //promos
    suspend fun getAllPromos() = /*fakeDataSource.fakePromoList*/promosDataSource.getAllPromos()
    suspend fun updatePromo(promo: Promo) = promosDataSource.updatePromo(promo)//promosDataSource.getAllPromos()
    suspend fun createPromo(promo: Promo) = promosDataSource.createPromo(promo)
}