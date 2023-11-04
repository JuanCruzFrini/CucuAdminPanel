package com.cucu.cucuadminpanel.presentation.navdrawer.promos.edit.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cucu.cucuadminpanel.data.models.Product
import com.cucu.cucuadminpanel.data.models.promo.Promo
import com.cucu.cucuadminpanel.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditPromoViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    var promo by mutableStateOf(Promo())

    var products by mutableStateOf<List<Product>>(emptyList())

    var succeedDelete by mutableStateOf(false)
    var isDeleting by mutableStateOf(false)

    fun setPromotion(promo: Promo){
        this.promo = promo
        Log.d("promo", promo.toString())
    }
    fun getAllProducts(){
        viewModelScope.launch {
            try {
                products = repository.getAllProducts()
            } catch (e:Exception){
                Log.d("Error", e.message.toString())
            }
        }
    }

    fun updatePromo(promo: Promo){
        viewModelScope.launch {
            try {
                repository.updatePromo(promo)
            }catch (e:Exception){
                Log.d("Error update", e.message.toString())
            }
        }
    }

    fun deletePromo(promoId: String?) {
        viewModelScope.launch {
            isDeleting = true
            try {
                isDeleting = false
                succeedDelete = repository.deletePromo(promoId)
            }catch (e:Exception){
                isDeleting = false
                Log.d("Error update", e.message.toString())
            }
        }
    }

}