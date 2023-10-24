package com.cucu.cucuadminpanel.presentation.products.detail.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cucu.cucuadminpanel.data.models.Product
import com.cucu.cucuadminpanel.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val repository: Repository,
): ViewModel() {

    var product = mutableStateOf(Product())

    fun setProduct(product: Product){
        this.product.value = product
    }

    fun getOldPrice(): Double? = product.value.newPrice

    fun updateProduct(product: Product) {
        viewModelScope.launch {
            repository.updateProduct(product)
        }
    }

    fun getCategories(): List<String> = repository.getCategories()
}