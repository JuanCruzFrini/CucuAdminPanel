package com.cucu.cucuadminpanel.presentation.home.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cucu.cucuadminpanel.data.models.Product
import com.cucu.cucuadminpanel.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    /*private val getAllProducts: GetAllProductsUseCase,*/
    private val repository: Repository
    //private val fakeDataSource: FakeDataSource
) : ViewModel() {

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val isLoading = _isLoading.asStateFlow()

    private val _productsList: MutableLiveData<List<Product>> = MutableLiveData()
    val productsList: LiveData<List<Product>> = _productsList

    fun getAllProducts(){
        viewModelScope.launch {
            _isLoading.value = true
            try {
                //val list = getAllProducts.invoke()
                _productsList.postValue(repository.getAllProducts()/*list*/)
                _isLoading.value = false
            } catch (e: Exception) {
                Log.e("Error", e.message.toString())
                _isLoading.value = false
            }
        }
    }

/*
    fun getPurchasesRefs(){
        viewModelScope.launch {
            try {
                val purchasesRefs = repository.getPurchases()
                //_productsList.postValue(list)
                Log.d("purchases", purchasesRefs.toString())
            } catch (e: Exception) {
                Log.e("Error", e.message.toString())
            }
        }
    }
*/
}