package com.cucu.cucuadminpanel.presentation.products.add.viewmodel

import android.net.Uri
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
class AddProductViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {

    var isAdding by mutableStateOf(false)
    var succeedAdd by mutableStateOf<Boolean?>(null)

    fun addProduct(product: Product, image: Uri){
        viewModelScope.launch {
            isAdding = true
            try {
                succeedAdd = repository.addProduct(product, image)
                isAdding = false
            }catch (e:Exception){
                Log.d("Error add product", e.message.toString())
                isAdding = false
            }
        }
    }

    fun getCountries(): List<String> = repository.getCategories()
}