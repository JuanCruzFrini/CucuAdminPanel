package com.cucu.cucuadminpanel.presentation.add

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cucu.cucuadminpanel.data.models.Product
import com.cucu.cucuadminpanel.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {

    fun addProduct(product: Product, image: Uri){
        viewModelScope.launch {
            try {
                repository.addProduct(product, image)
            }catch (e:Exception){
                Log.d("Error add product", e.message.toString())
            }
        }
    }
}