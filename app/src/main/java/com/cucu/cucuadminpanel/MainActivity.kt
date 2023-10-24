package com.cucu.cucuadminpanel

import android.os.Bundle
import android.os.Parcelable
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
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
import com.cucu.cucuadminpanel.presentation.navdrawer.NavDrawerDestinationsController
import com.cucu.cucuadminpanel.presentation.navdrawer.promos.ChoosePromoProductsScreen
import com.cucu.cucuadminpanel.presentation.navdrawer.promos.add.AddPromoScreen
import com.cucu.cucuadminpanel.presentation.navdrawer.promos.detail.PromoDetail
import com.cucu.cucuadminpanel.presentation.navdrawer.promos.edit.EditPromoScreen
import com.cucu.cucuadminpanel.presentation.navdrawer.sales.PurchaseDetail
import com.cucu.cucuadminpanel.presentation.products.add.AddScreen
import com.cucu.cucuadminpanel.presentation.products.detail.view.DetailScreen
import com.cucu.cucuadminpanel.presentation.products.edit.EditScreen
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
            //composable(Routes.AddPromo.route){ AddPromoScreen(navController) }
            //composable(Routes.ChoosePromoProducts.route){ ChoosePromoProductsScreen(navController) }

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
                content = { DetailScreen(navController, it) }
            )

            arguedComposable<Product>(
                route = Routes.EditProduct.route,
                argument = "product",
                navController = navController,
                content = { EditScreen(it, navController) }
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

            navDrawerRoutes.forEach {
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

inline fun <reified T : Parcelable> NavGraphBuilder.arguedComposable(
     route:String,
     argument:String,
     navController: NavHostController,
     crossinline content: @Composable (T?) -> Unit
) {
    this.composable(
        route = route,
        arguments = listOf(navArgument(argument){ type = NavType.parcelableTypeOf<T>() })
    ) {
        val value = navController.previousBackStackEntry?.savedStateHandle?.get<T>(argument)
        content(value)
    }
}

