package com.cucu.cucuadminpanel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cucu.cucuadminpanel.application.Constants
import com.cucu.cucuadminpanel.application.Routes
import com.cucu.cucuadminpanel.application.arguedComposable
import com.cucu.cucuadminpanel.data.models.Product
import com.cucu.cucuadminpanel.data.models.promo.Promo
import com.cucu.cucuadminpanel.data.models.purchase.PurchaseReference
import com.cucu.cucuadminpanel.presentation.MainScreen
import com.cucu.cucuadminpanel.presentation.home.viewmodel.HomeViewModel
import com.cucu.cucuadminpanel.presentation.navdrawer.NavDrawerDestinationsController
import com.cucu.cucuadminpanel.presentation.navdrawer.promos.ChoosePromoProductsScreen
import com.cucu.cucuadminpanel.presentation.navdrawer.promos.add.view.AddPromoScreen
import com.cucu.cucuadminpanel.presentation.navdrawer.promos.detail.PromoDetail
import com.cucu.cucuadminpanel.presentation.navdrawer.promos.edit.view.EditPromoScreen
import com.cucu.cucuadminpanel.presentation.navdrawer.sales.detail.PurchaseDetail
import com.cucu.cucuadminpanel.presentation.products.add.view.AddProductScreen
import com.cucu.cucuadminpanel.presentation.products.detail.view.ProductDetail
import com.cucu.cucuadminpanel.presentation.products.edit.EditProductScreen
import com.cucu.cucuadminpanel.ui.theme.CucuAdminPanelTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel by viewModels<HomeViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition { viewModel.isLoading.value }
        }
        setContent {
            CucuAdminPanelTheme {
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
            composable(Routes.AddProduct.route){ AddProductScreen(navController) }

            arguedComposable<Promo>(
                route = Routes.AddPromo.route,
                argument = "promotion",
                navController = navController,
                content = { AddPromoScreen(it, navController) }
            )
            arguedComposable<Promo>(
                route = Routes.ChoosePromoProducts.route,
                argument = "promotion",
                navController = navController,
                content = { ChoosePromoProductsScreen(it, navController) }
            )

            arguedComposable<Product>(
                route = Routes.ProductDetail.route,
                argument = "product",
                navController = navController,
                content = { ProductDetail(navController, it) }
            )

            arguedComposable<Product>(
                route = Routes.EditProduct.route,
                argument = "product",
                navController = navController,
                content = { EditProductScreen(it, navController) }
            )

            arguedComposable<PurchaseReference>(
                route = Routes.PurchaseDetail.route,
                argument = "purchaseReference",
                navController = navController,
                content = { PurchaseDetail(it, navController) }
            )

            arguedComposable<Promo>(
                route = Routes.PromoDetail.route,
                argument = "promotion",
                navController = navController,
                content = { PromoDetail(it, navController) }
            )

            Constants.navDrawerRoutes.forEach {
                composable(it){ NavDrawerDestinationsController(navController) }
            }

            arguedComposable<Promo>(
                route = Routes.EditPromo.route,
                argument = "promotion",
                navController = navController,
                content = { EditPromoScreen(it, navController) }
            )
        }
    }
}