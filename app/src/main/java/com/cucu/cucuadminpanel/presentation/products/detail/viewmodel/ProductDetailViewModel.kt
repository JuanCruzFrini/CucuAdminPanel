package com.cucu.cucuadminpanel.presentation.products.detail.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cucu.cucuadminpanel.data.models.Product
import com.cucu.cucuadminpanel.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    val repository: Repository,
): ViewModel() {

    var product = mutableStateOf(Product())

    var succeedDelete by mutableStateOf(false)
    var isDeleting by mutableStateOf(false)

    var succeedEdit by mutableStateOf(false)
    var isEditing by mutableStateOf(false)

    fun setProduct(product: Product){
        this.product.value = product
    }

    fun getOldPrice(): Double? = product.value.newPrice

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            isEditing = true
            try {
                succeedEdit = repository.updateProduct(product)
                isEditing = false
            }catch (e:Exception){
                isEditing = false
            }
        }
    }

    fun getCategories(): List<String> = repository.getCategories()
    fun deleteProduct(productId: String?) {
        viewModelScope.launch {
            isDeleting = true
            try {
                succeedDelete = repository.deleteProduct(productId)
                isDeleting = false
            }catch (e:Exception){
                Log.d("Error update", e.message.toString())
                isDeleting = false
            }
        }
    }
}