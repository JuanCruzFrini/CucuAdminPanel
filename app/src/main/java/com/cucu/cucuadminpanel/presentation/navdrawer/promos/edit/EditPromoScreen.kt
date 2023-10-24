package com.cucu.cucuadminpanel.presentation.navdrawer.promos.edit

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.cucu.cucuadminpanel.application.Routes
import com.cucu.cucuadminpanel.data.models.cart.CartProduct
import com.cucu.cucuadminpanel.data.models.promo.Promo
import com.cucu.cucuadminpanel.presentation.navdrawer.promos.PromoProductsItem
import com.cucu.cucuadminpanel.presentation.products.add.TextFieldCommon
import com.cucu.cucuadminpanel.presentation.products.detail.view.FabIcon
import com.cucu.cucuadminpanel.presentation.products.detail.view.TopBarNavigateBack
import com.cucu.cucuadminpanel.ui.theme.Purple40
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPromoScreen(
    promo: Promo?,
    navController: NavHostController,
    viewModel:EditPromoViewModel = hiltViewModel()
) {
    promo?.let { viewModel.setPromotion(it) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val promoMutableState = viewModel.promo
    var products by remember { mutableStateOf(promoMutableState.products) }
    var stock by rememberSaveable { mutableStateOf(promoMutableState.stock.toString()) }
    var description by rememberSaveable { mutableStateOf(promoMutableState.description.toString()) }
    var name by rememberSaveable { mutableStateOf(promoMutableState.name.toString()) }
    var price by rememberSaveable { mutableStateOf(promoMutableState.price?.toString()) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = { TopBarNavigateBack(navController) },
        floatingActionButton = {
            FabIcon(
                icon = Icons.Rounded.Check,
                onClick = {
                    promo?.let {
                        updatePromo(
                            it, stock,
                            description,
                            name, price,
                            products, viewModel,
                            navController
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(state = rememberScrollState())
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(16.dp)
            ) {
                TextFieldCommon(value = name, label = "Nombre", onValueChange = { name = it })

                Divider(color = Purple40)
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Purple40.copy(alpha = .3f))
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)){
                    items(products  ?: emptyList()){
                        PromoProductsItem(it, true){ productId ->
                            onItemClick(products, scope, snackbarHostState, productId){ newList ->
                                products = newList
                            }
                        }
                    }
                    item {
                        FabIcon(
                            onClick = {
                                addProducts(
                                    stock, description,
                                    name, promo?.price,
                                    price, products,
                                    navController
                                )
                            },
                            icon = Icons.Rounded.Add)
                    }
                }
                Divider(color = Purple40)

                TextFieldCommon(
                    value = price,
                    label = "Precio",
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = { price = it}
                )

                TextFieldCommon(
                    value = stock,
                    label = "Stock",
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = { stock = it}
                )

                TextFieldCommon(value = description, label = "Descripcion", onValueChange = { description = it })
            }
        }
    }

}

private fun updatePromo(
    it: Promo,
    stock: String,
    description: String,
    name: String,
    price: String?,
    products: List<CartProduct>?,
    viewModel: EditPromoViewModel,
    navController: NavHostController
) {
    val prom: Promo = it.copy(
            stock = stock.toInt(),
            description = description,
            name = name,
            oldPrice = it.price,
            price = price?.toInt(),
            products = products
    )

    viewModel.updatePromo(prom)
    navController.popBackStack(Routes.Promos.route, false)
}

fun onItemClick(
    products: List<CartProduct>?,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    productId: String,
    onNewList:(List<CartProduct>) -> Unit
) {

    if (products?.size!! <= 2) {
        scope.launch {
            snackbarHostState.showSnackbar("La promocion debe contener al menos dos productos")
        }
    } else {
        val newList = products.filter { it.product.id != productId }
        onNewList(newList)
    }
}

fun addProducts(
    stock: String,
    description: String,
    name: String,
    oldPrice: Int? = 0,
    price: String?,
    products: List<CartProduct>?,
    navController: NavHostController
) {
    val updatedPromo = Promo(
        stock = stock.toInt(),
        description = description,
        name = name,
        oldPrice = oldPrice,
        price = price?.toInt(),
        products = products
    )
    navController.currentBackStackEntry?.savedStateHandle?.set(
        key = "promotion",
        value = updatedPromo
    )
    navController.navigate(Routes.ChoosePromoProducts.createRoute(updatedPromo))
}