package com.cucu.cucuadminpanel.presentation.products.edit

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.cucu.cucuadminpanel.R
import com.cucu.cucuadminpanel.application.Routes
import com.cucu.cucuadminpanel.data.models.Product
import com.cucu.cucuadminpanel.data.models.items.ItemCategory
import com.cucu.cucuadminpanel.presentation.products.add.view.Progress
import com.cucu.cucuadminpanel.presentation.products.add.view.SpinnerStrings
import com.cucu.cucuadminpanel.presentation.products.add.view.TextFieldCommon
import com.cucu.cucuadminpanel.presentation.products.detail.view.FabIcon
import com.cucu.cucuadminpanel.presentation.products.detail.view.TopBarNavigateBack
import com.cucu.cucuadminpanel.presentation.products.detail.viewmodel.ProductDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProductScreen(
    product: Product?,
    mainNavController: NavHostController,
    viewModel: ProductDetailViewModel = hiltViewModel()
) {
    product?.let { viewModel.setProduct(it) }
    val context = LocalContext.current

    val deleteState by rememberUpdatedState(newValue = viewModel.succeedDelete)
    val editState by rememberUpdatedState(newValue = viewModel.succeedEdit)
    val isDeleting = viewModel.isDeleting
    val isEditing = viewModel.isEditing

    if (deleteState){
        Toast.makeText(context, "Producto eliminado", Toast.LENGTH_SHORT).show()
        viewModel.succeedDelete = false
        mainNavController.popBackStack(Routes.Main.route, false)
    }

    if (editState){
        Toast.makeText(context, "Producto actualizado", Toast.LENGTH_SHORT).show()
        viewModel.succeedEdit = false
        mainNavController.popBackStack(Routes.Main.route, false)
    }

    val snackbarHostState = remember { SnackbarHostState() }

    val productVM = viewModel.product.value

    val oldPrice = viewModel.getOldPrice()
    var stock by rememberSaveable { mutableStateOf(productVM.stock.toString()) }
    var description by rememberSaveable { mutableStateOf(productVM.description.toString()) }
    var name by rememberSaveable { mutableStateOf(productVM.name.toString()) }
    var newPrice by rememberSaveable { mutableStateOf(productVM.newPrice?.toString()) }
    var category by rememberSaveable { mutableStateOf(productVM.category?.category.toString()) }
    var code by rememberSaveable { mutableStateOf(productVM.code.toString()) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopBarNavigateBack(mainNavController) {
                viewModel.deleteProduct(product?.id)
            }
        },
        floatingActionButton = {
            FabIcon(
                icon = Icons.Rounded.Check,
                onClick = {
                    product?.let {
                        val prod: Product = if (oldPrice != newPrice?.toDouble()){
                            it.copy(
                                name = name, oldPrice = oldPrice,
                                newPrice = newPrice?.toDouble(),
                                stock = stock.toInt(),
                                code = code.toLong(),
                                category = ItemCategory(category),
                                description = description,
                            )
                        } else {
                            it.copy(
                                name = name, stock = stock.toInt(),
                                code = code.toLong(),
                                category = ItemCategory(category),
                                description = description)
                        }
                        viewModel.updateProduct(prod)
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
                AsyncImage(
                    model = product?.img,
                    contentScale = ContentScale.FillBounds,
                    contentDescription = null,
                    error = painterResource(id = R.drawable.ic_launcher_foreground),
                    placeholder = painterResource(id = R.drawable.ic_launcher_background),
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                )

                TextFieldCommon(
                    value = newPrice,
                    label = "Precio",
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = { newPrice = it}
                )

                TextFieldCommon(
                    value = stock,
                    label = "Stock",
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = { stock = it}
                )

                TextFieldCommon(
                    value = code,
                    label = "Codigo",
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    onValueChange = { code = it}
                )

                SpinnerStrings(viewModel.getCategories(), "Categoria", category){ category = it }

                TextFieldCommon(value = description, label = "Descripcion", onValueChange = { description = it })
            }
            Box(Modifier.fillMaxWidth(), Alignment.Center) {
                Progress(isDeleting)
                Progress(isEditing)
            }
        }
    }
}