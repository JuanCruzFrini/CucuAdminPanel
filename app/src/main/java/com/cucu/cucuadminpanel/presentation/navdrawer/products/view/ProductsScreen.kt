package com.cucu.cucuadminpanel.presentation.navdrawer.products.view

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.cucu.cucuadminpanel.presentation.home.view.ProductsList

@Composable
fun ProductsScreen(mainNavController: NavHostController) {
    ProductsList(mainNavController)
}