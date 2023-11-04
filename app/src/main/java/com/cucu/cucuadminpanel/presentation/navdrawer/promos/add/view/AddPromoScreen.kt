package com.cucu.cucuadminpanel.presentation.navdrawer.promos.add.view

import android.widget.Toast
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.cucu.cucuadminpanel.application.Routes
import com.cucu.cucuadminpanel.data.models.cart.CartProduct
import com.cucu.cucuadminpanel.data.models.promo.Promo
import com.cucu.cucuadminpanel.presentation.navdrawer.promos.PromoProductsItem
import com.cucu.cucuadminpanel.presentation.navdrawer.promos.add.viewmodel.AddPromoViewModel
import com.cucu.cucuadminpanel.presentation.navdrawer.promos.edit.view.addProducts
import com.cucu.cucuadminpanel.presentation.navdrawer.promos.edit.view.onItemClick
import com.cucu.cucuadminpanel.presentation.products.add.view.TextFieldCommon
import com.cucu.cucuadminpanel.presentation.products.detail.view.FabIcon
import com.cucu.cucuadminpanel.presentation.products.detail.view.TopBarNavigateBack
import com.cucu.cucuadminpanel.ui.theme.Purple40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddPromoScreen(
    promo:Promo?,
    navController: NavHostController,
    viewModel: AddPromoViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var products by remember { mutableStateOf<List<CartProduct>>(listOf()) }
    var stock by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var name by rememberSaveable { mutableStateOf("") }
    var price by rememberSaveable { mutableStateOf("") }

    promo?.let {
        products = it.products!!
        stock = it.stock.toString()
        description = it.description.toString()
        name = it.name.toString()
        price = it.price.toString()
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = { TopBarNavigateBack(navController){} },
        floatingActionButton = {
            FabIcon(
                icon = Icons.Rounded.Check,
                onClick = {
                    if (products.size < 2){
                        Toast.makeText(context, "La promocion debe contener al menos dos productos", Toast.LENGTH_SHORT).show()
                    } else if (stock.isEmpty() || name.isEmpty() || price.isEmpty() || description.isEmpty() || products.isEmpty()) {
                        Toast.makeText(context, "Debes completar todos los campos", Toast.LENGTH_SHORT).show()
                    } else {
                        createPromo(
                            stock = stock,
                            description = description,
                            name = name, price = price,
                            products = products, viewModel = viewModel,
                            navController = navController
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
                    items(products){
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
                                    promo?.id,
                                    stock = stock,
                                    description = description,
                                    name = name,
                                    price = price,
                                    products = products,
                                    navController = navController
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

fun createPromo(
    stock: String,
    description: String,
    name: String,
    price: String,
    products: List<CartProduct>,
    viewModel: AddPromoViewModel,
    navController: NavHostController
) {
    val prom = Promo(
        stock = stock.toInt(),
        description = description,
        name = name,
        price = price.toInt(),
        products = products
    )
    viewModel.createPromo(prom)
    navController.popBackStack(Routes.Promos.route, false)
}
