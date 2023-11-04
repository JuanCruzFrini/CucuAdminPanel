package com.cucu.cucuadminpanel.presentation.navdrawer.stats.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cucu.cucuadminpanel.data.models.Product
import com.cucu.cucuadminpanel.data.models.Stat
import com.cucu.cucuadminpanel.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StatsViewModel @Inject constructor(
    val repository: Repository
): ViewModel() {

    var stat by mutableStateOf(Stat())
    var bestSellers by mutableStateOf<List<Product>>(listOf())
    var mostWanted by mutableStateOf<List<Product>>(listOf())
    var mostWatched by mutableStateOf<List<Product>>(listOf())

    init {
        getStat()
        getBestSellers()
        getMostWanted()
        getMostWatched()
    }

    private fun getStat(){
        viewModelScope.launch {
            try {
                stat = repository.getStats()
            } catch (e:Exception){
                Log.d("Error", e.message.toString())
            }
        }
    }
    private fun getBestSellers(){
        viewModelScope.launch {
            try {
                bestSellers = repository.getBestSellers()
            } catch (e:Exception){
                Log.d("Error", e.message.toString())
            }
        }
    }

    private fun getMostWanted(){
        viewModelScope.launch {
            try {
                mostWanted = repository.getMostWanted()
            } catch (e:Exception){
                Log.d("Error", e.message.toString())
            }
        }
    }
    private fun getMostWatched(){
        viewModelScope.launch {
            try {
                mostWatched = repository.getMostWatched()
            } catch (e:Exception){
                Log.d("Error", e.message.toString())
            }
        }
    }
}