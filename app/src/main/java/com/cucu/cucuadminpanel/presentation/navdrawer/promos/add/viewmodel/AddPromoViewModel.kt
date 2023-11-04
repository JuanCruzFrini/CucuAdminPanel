package com.cucu.cucuadminpanel.presentation.navdrawer.promos.add.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cucu.cucuadminpanel.data.models.promo.Promo
import com.cucu.cucuadminpanel.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddPromoViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    val promo by mutableStateOf(Promo())

    fun createPromo(promo: Promo) {
        viewModelScope.launch {
            repository.createPromo(promo)
        }
    }
}
