package com.cucu.cucuadminpanel.presentation.navdrawer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.cucu.cucuadminpanel.application.Routes
import com.cucu.cucuadminpanel.data.models.promo.Promo
import com.cucu.cucuadminpanel.presentation.navdrawer.discounts.DiscountsScreen
import com.cucu.cucuadminpanel.presentation.navdrawer.products.view.ProductsScreen
import com.cucu.cucuadminpanel.presentation.navdrawer.promos.PromosScreen
import com.cucu.cucuadminpanel.presentation.navdrawer.sales.SalesScreen
import com.cucu.cucuadminpanel.presentation.navdrawer.stats.StatsScreen
import com.cucu.cucuadminpanel.presentation.products.detail.view.FabIcon
import com.cucu.cucuadminpanel.presentation.products.detail.view.TopBarNavigateBack

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavDrawerDestinationsController(mainNavController: NavHostController) {

    Scaffold(
        Modifier.fillMaxSize(),
        topBar = { TopBarNavigateBack(mainNavController){} },
        floatingActionButton = { NavDrawerFabController(mainNavController) }
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            SetContent(mainNavController)
        }
    }
}

@Composable
fun NavDrawerFabController(navController: NavHostController) {
    when (navController.currentDestination?.route) {
        Routes.Products.route -> {
            FabIcon(
                icon = Icons.Rounded.Add,
                onClick = { navController.navigate(Routes.AddProduct.route) }
            )
        }
        Routes.Promos.route -> {
            FabIcon(
                icon = Icons.Rounded.Add,
                onClick = {
                    navController.navigate(Routes.AddPromo.createRoute(Promo()))
                }
            )
        }
        /*Routes.Combos.route -> {
            FabIcon(
                icon = Icons.Rounded.Add,
                onClick = { navController.navigate(Routes.AddCombo.route) }
            )
        } */
        else -> {}
    }
}

@Composable
fun SetContent(mainNavController: NavHostController) {
    when(mainNavController.currentDestination?.route){
        Routes.Sales.route -> SalesScreen(mainNavController)
        Routes.Promos.route -> PromosScreen(mainNavController)
        Routes.Products.route -> ProductsScreen(mainNavController)
        //Routes.Combos.route -> CombosScreen(mainNavController)
        Routes.Discounts.route -> DiscountsScreen(mainNavController)
        Routes.Stats.route -> StatsScreen(mainNavController)
        else -> { }
    }
}

/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarNavigateBack(mainNavController: NavHostController) {

    val checkRoute = when (mainNavController.currentDestination?.route){
        Routes.ProductDetail.route -> "Detail"
        Routes.PurchaseDetail.route -> "Purchase"
        Routes.Category.route -> "Category"
        else -> { mainNavController.currentDestination?.route?.firstCharToUpperCase() }
    }

    //if (checkRoute != Routes.Cart.route.firstCharToUpperCase()) {
        Surface(shadowElevation = 3.dp) {
            TopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    navigationIconContentColor = Purple80,
                    titleContentColor = Purple80,
                    scrolledContainerColor = Purple40,
                ),
                title = { Text(text = "$checkRoute") },
                navigationIcon = {
                    IconButton(onClick = { mainNavController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        }
   // }
}*/
