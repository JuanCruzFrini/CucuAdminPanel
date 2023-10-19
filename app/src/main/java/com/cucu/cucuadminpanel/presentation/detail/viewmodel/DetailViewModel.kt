package com.cucu.cucuadminpanel.presentation.detail.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cucu.cucuadminpanel.data.fakedatasources.FakeDataSource
import com.cucu.cucuadminpanel.data.models.Product
import com.cucu.cucuadminpanel.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val repository: Repository,
    val fakeDataSource: FakeDataSource
): ViewModel() {

    var product = mutableStateOf(Product())

    fun setProduct(product: Product){
        this.product.value = product
        increaseSeenTimes(product)
    }

    private fun increaseSeenTimes(product: Product) {
        viewModelScope.launch {
            repository.increaseSeenTimes(product)
        }
    }

   /* fun updateStock(newStock:String){
        viewModelScope.launch {
            try {
                product.value = product.value.copy(
                    stock = newStock.toInt()
                )
            } catch (e:Exception){
                Log.d("Error", "invalid input")
            }
        }
    }

    fun updateDescription(newDesc: String) {
        viewModelScope.launch {
            try {
                product.value = product.value.copy(
                    description = newDesc
                )
            } catch (e:Exception){
                Log.d("Error", "invalid input")
            }
        }
    }

    fun updateName(newName: String) {
        viewModelScope.launch {
            try {
                product.value = product.value.copy(
                    name = newName
                )
            } catch (e:Exception){
                Log.d("Error", "invalid input")
            }
        }
    }

    fun updatePrices(oldPrice: Double?, newPrice: String?) {
        viewModelScope.launch {
            try {
                product.value = product.value.copy(
                    newPrice = newPrice?.toDouble(),
                    oldPrice = oldPrice
                )
            } catch (e:Exception){
                Log.d("Error", "invalid input")
            }
        }
    }*/

    fun getOldPrice(): Double? = product.value.newPrice

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            repository.updateProduct(product)
        }
    }
}