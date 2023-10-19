package com.cucu.cucuadminpanel.presentation.navdrawer.sales

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cucu.cucuadminpanel.data.models.purchase.Purchase
import com.cucu.cucuadminpanel.data.models.purchase.PurchaseReference
import com.cucu.cucuadminpanel.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SalesViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    var sales by mutableStateOf<List<Purchase>>(emptyList())
    var sale by mutableStateOf(Purchase())

    fun getSales(){
        viewModelScope.launch {
            try {
                sales = repository.getSales()
            } catch (e:Exception){
                Log.d("Error", "${e.message}")
            }
        }
    }

    fun getSaleById(purchaseRef: PurchaseReference?){
        viewModelScope.launch {
            try {
                sale = repository.getSaleById(purchaseRef)
            } catch (e:Exception){
                Log.d("Error", "${e.message}")
            }
        }
    }

    fun cancelSale(purchaseRef: PurchaseReference?, cancelReason: String) {
        viewModelScope.launch {
            try {
                repository.cancelSale(purchaseRef, cancelReason)
            } catch (e:Exception){
                Log.d("Error", "${e.message}")
            }
        }
    }
}