package com.cucu.cucuadminpanel.data.repository

import android.net.Uri
import com.cucu.cucuadminpanel.application.Constants
import com.cucu.cucuadminpanel.data.fakedatasources.FakeDataSource
import com.cucu.cucuadminpanel.data.models.Product
import com.cucu.cucuadminpanel.data.models.promo.Promo
import com.cucu.cucuadminpanel.data.models.purchase.PurchaseReference
import com.cucu.cucuadminpanel.data.network.ProductsDataSource
import com.cucu.cucuadminpanel.data.network.PromosDataSource
import com.cucu.cucuadminpanel.data.network.StatsDataSource
import com.cucu.cucuadminpanel.data.network.sales.SalesDataSource
import javax.inject.Inject

class Repository @Inject constructor(
    private val fakeDataSource: FakeDataSource,
    private val productsDataSource: ProductsDataSource,
    private val salesDataSource: SalesDataSource,
    private val promosDataSource: PromosDataSource,
    private val statsDataSource: StatsDataSource
) {

    //products
    suspend fun getAllProducts() = productsDataSource.getAllProducts()//fakeDataSource.productsFakeList
    suspend fun getAllDiscounts() = productsDataSource.getDiscounts()//fakeDataSource.productsFakeList.filter { it.isDiscount() }
    suspend fun updateProduct(product: Product):Boolean = productsDataSource.updateProduct(product)
    suspend fun addProduct(product: Product, image:Uri):Boolean = productsDataSource.uploadImage(product,image)
    suspend fun deleteProduct(productId: String?):Boolean = productsDataSource.deleteProduct(productId)

    //categories
    fun getCategories() = fakeDataSource.categoriesStringList()//productsDataSource.getCategories()

    //sales
    suspend fun getSales() = salesDataSource.getSales()
    suspend fun getSaleById(purchaseRef: PurchaseReference?) = salesDataSource.getSaleById(purchaseRef)
    suspend fun cancelSale(purchaseRef: PurchaseReference?, cancelReason: String) = salesDataSource.cancelSale(purchaseRef, cancelReason)

    //promos
    suspend fun getAllPromos() = /*fakeDataSource.fakePromoList*/promosDataSource.getAllPromos()
    suspend fun updatePromo(promo: Promo) = promosDataSource.updatePromo(promo)//promosDataSource.getAllPromos()
    suspend fun createPromo(promo: Promo) = promosDataSource.createPromo(promo)
    suspend fun deletePromo(promoId: String?):Boolean = promosDataSource.deletePromo(promoId)
    //Stats
    suspend fun getStats() = statsDataSource.getStat()
    suspend fun getBestSellers() = statsDataSource.getProductsBy(Constants.SOLD_TIMES)
    suspend fun getMostWanted() = statsDataSource.getProductsBy(Constants.FAV_TIMES)
    suspend fun getMostWatched() = statsDataSource.getProductsBy(Constants.SEEN_TIMES)
}