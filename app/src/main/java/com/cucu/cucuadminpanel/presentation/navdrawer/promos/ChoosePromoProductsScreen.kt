package com.cucu.cucuadminpanel.presentation.navdrawer.promos

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.cucu.cucuadminpanel.application.Routes
import com.cucu.cucuadminpanel.data.models.cart.CartProduct
import com.cucu.cucuadminpanel.data.models.promo.Promo
import com.cucu.cucuadminpanel.presentation.home.view.ProductItem
import com.cucu.cucuadminpanel.presentation.navdrawer.promos.edit.EditPromoViewModel
import com.cucu.cucuadminpanel.presentation.products.detail.view.TopBarNavigateBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChoosePromoProductsScreen(
    promo: Promo?,
    navController: NavHostController,
    viewModel: EditPromoViewModel = hiltViewModel()
) {
    viewModel.getAllProducts()

    Scaffold(
        topBar = { TopBarNavigateBack(navController) }
    ) {
        LazyColumn (Modifier.padding(it)){
            items(viewModel.products){
                ProductItem(it){
                    val newList = promo?.products?.toMutableList()
                    newList?.add(CartProduct(productId = it.id, product = it, quantity = 1))
                    val updatedPromo = promo?.copy(products = newList)

                    checkDestination(navController, updatedPromo)
                }
            }
        }

    }
}

fun checkDestination(navController: NavHostController, updatedPromo: Promo?) {
    navController.currentBackStackEntry?.savedStateHandle?.set(
        key = "promotion",
        value = updatedPromo!!
    )
    if (navController.previousBackStackEntry?.destination?.route == Routes.EditPromo.route){
        navController.navigate(Routes.EditPromo.createRoute(updatedPromo!!))
    } else {
        navController.navigate(Routes.AddPromo.createRoute(updatedPromo!!))
    }
}
