package com.cucu.cucuadminpanel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cucu.cucuadminpanel.application.Routes
import com.cucu.cucuadminpanel.application.parcelableTypeOf
import com.cucu.cucuadminpanel.data.models.Product
import com.cucu.cucuadminpanel.data.models.promo.Promo
import com.cucu.cucuadminpanel.data.models.purchase.PurchaseReference
import com.cucu.cucuadminpanel.presentation.MainScreen
import com.cucu.cucuadminpanel.presentation.add.AddScreen
import com.cucu.cucuadminpanel.presentation.detail.view.DetailScreen
import com.cucu.cucuadminpanel.presentation.edit.EditScreen
import com.cucu.cucuadminpanel.presentation.navdrawer.NavDrawerDestinationsController
import com.cucu.cucuadminpanel.presentation.navdrawer.promos.AddPromoScreen
import com.cucu.cucuadminpanel.presentation.navdrawer.promos.PromoDetail
import com.cucu.cucuadminpanel.presentation.navdrawer.sales.PurchaseDetail
import com.cucu.cucuadminpanel.ui.theme.CucuAdminPanelTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val navDrawerRoutes = listOf(
        Routes.Sales.route, Routes.Promos.route,
        Routes.Products.route, Routes.Combos.route,
        Routes.Discounts.route,
        Routes.Stats.route)

    //private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*installSplashScreen().apply {
            viewModel.getAllProducts()
            setKeepOnScreenCondition { viewModel.isLoading.value }
        }*/
        setContent {
            CucuAdminPanelTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SetNavigation()
                }
            }
        }
    }

    @Composable
    fun SetNavigation() {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = Routes.Main.route){
            composable(Routes.Main.route){ MainScreen(navController) }
            composable(Routes.AddProduct.route){ AddScreen(navController) }
            composable(Routes.AddPromo.route){ AddPromoScreen(navController) }

            composable(
                route = Routes.ProductDetail.route,
                arguments = listOf(navArgument("product"){ type = NavType.parcelableTypeOf<Product>()})
            ){
                val product = navController.previousBackStackEntry?.savedStateHandle?.get<Product>("product")
                DetailScreen(navController, product)
            }

            composable(
                route = Routes.EditProduct.route,
                arguments = listOf(navArgument("product"){ type = NavType.parcelableTypeOf<Product>()})
            ){
                val product = navController.previousBackStackEntry?.savedStateHandle?.get<Product>("product")
                EditScreen(product, navController)
            }

            composable(
                route = Routes.PurchaseDetail.route,
                arguments = listOf(navArgument("purchaseReference"){ type = NavType.parcelableTypeOf<PurchaseReference>() })
            ){
                val purchaseRef = navController.previousBackStackEntry?.savedStateHandle?.get<PurchaseReference>("purchaseReference")
                PurchaseDetail(purchaseRef, navController)
            }

            composable(
                route = Routes.PromoDetail.route,
                arguments = listOf(navArgument("promotion"){ type = NavType.parcelableTypeOf<Promo>() })
            ){
                val promo = navController.previousBackStackEntry?.savedStateHandle?.get<Promo>("promotion")
                PromoDetail(promo, navController)
            }



            navDrawerRoutes.forEach {
                composable(it){ NavDrawerDestinationsController(navController) }
            }
        }
    }
}

