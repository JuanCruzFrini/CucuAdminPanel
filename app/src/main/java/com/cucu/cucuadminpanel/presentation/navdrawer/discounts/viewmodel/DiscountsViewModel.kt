package com.cucu.cucuadminpanel.presentation.navdrawer.discounts.viewmodel

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
class DiscountsViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {

    var discounts by mutableStateOf<List<Product>>(emptyList())

    fun getAllDiscounts(){
        viewModelScope.launch {
            discounts = repository.getAllDiscounts()
        }
    }
}