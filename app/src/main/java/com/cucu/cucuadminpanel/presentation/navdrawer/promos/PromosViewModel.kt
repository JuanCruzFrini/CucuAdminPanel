package com.cucu.cucuadminpanel.presentation.navdrawer.promos

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cucu.cucuadminpanel.data.models.promo.Promo
import com.cucu.cucuadminpanel.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PromosViewModel @Inject constructor(
    private val repository: Repository
): ViewModel() {

    var promos by mutableStateOf<List<Promo>>(emptyList())

    fun getAllPromos(){
        viewModelScope.launch {
            try {
                promos = repository.getAllPromos()
            } catch (e:Exception){
                Log.d("Error", e.message.toString())
            }
        }
    }

}