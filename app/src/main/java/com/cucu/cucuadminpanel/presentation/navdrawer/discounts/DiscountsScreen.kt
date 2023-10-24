package com.cucu.cucuadminpanel.presentation.navdrawer.discounts

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.cucu.cucuadminpanel.application.Routes
import com.cucu.cucuadminpanel.presentation.home.view.ProductItem
import com.cucu.cucuadminpanel.presentation.navdrawer.discounts.viewmodel.DiscountsViewModel

@Composable
fun DiscountsScreen(
    mainNavController: NavHostController,
    viewModel: DiscountsViewModel = hiltViewModel()
) {
   viewModel.getAllDiscounts()

    LazyColumn{
        items(viewModel.discounts){
            ProductItem(it){
                mainNavController.currentBackStackEntry?.savedStateHandle?.set(
                    key = "product",
                    value = it
                )
                mainNavController.navigate(Routes.ProductDetail.createRoute(it))
            }
        }
    }
}