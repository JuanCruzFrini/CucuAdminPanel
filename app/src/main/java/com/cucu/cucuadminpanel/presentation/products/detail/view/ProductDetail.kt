package com.cucu.cucuadminpanel.presentation.products.detail.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.cucu.cucuadminpanel.R
import com.cucu.cucuadminpanel.application.Routes
import com.cucu.cucuadminpanel.application.calculateDiscountPercent
import com.cucu.cucuadminpanel.application.firstCharToUpperCase
import com.cucu.cucuadminpanel.application.textWithLineThrough
import com.cucu.cucuadminpanel.data.models.Product
import com.cucu.cucuadminpanel.presentation.products.detail.viewmodel.ProductDetailViewModel
import com.cucu.cucuadminpanel.ui.theme.Purple40
import com.cucu.cucuadminpanel.ui.theme.Purple80
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetail(
    mainNavController: NavHostController,
    product: Product?,
    viewModel: ProductDetailViewModel = hiltViewModel()
) {
    product?.let { viewModel.setProduct(it) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = { TopBarNavigateBack(mainNavController){} },
        floatingActionButton = {
            FabIcon(
                icon = Icons.Rounded.Edit,
                onClick = {
                    product?.let {
                        mainNavController.currentBackStackEntry?.savedStateHandle?.set(
                            key = "product",
                            value = it
                        )
                        mainNavController.navigate(Routes.EditProduct.createRoute(it))
                    }
                }
            )
        }
    ) { paddingValues ->
        Surface(modifier = Modifier.padding(paddingValues)) {
            DetailContent {
                scope.launch {
                    snackbarHostState.showSnackbar(it)
                }
            }
        }
    }
}

@Composable
fun FabIcon(onClick:() -> Unit, icon:ImageVector) {
    FloatingActionButton(onClick = { onClick() }) {
        Icon(imageVector = icon, contentDescription = "")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarNavigateBack(
    mainNavController: NavHostController,
    onDeleteClick:()-> Unit
) {

    val checkRoute = when (mainNavController.currentDestination?.route){
        Routes.ProductDetail.route -> "Detail"
        Routes.PurchaseDetail.route -> "Purchase"
        Routes.PromoDetail.route -> "Promotion"
        Routes.Category.route -> "Category"
        Routes.AddProduct.route -> "New Product"
        Routes.AddPromo.route -> "New Promo"
        Routes.EditProduct.route -> "Edit Product"
        Routes.EditPromo.route -> "Edit Promotion"
        Routes.ChoosePromoProducts.route -> "Add Product"
        else -> { mainNavController.currentDestination?.route?.firstCharToUpperCase() }
    }

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
                },
                actions = {
                    when (mainNavController.currentDestination?.route){
                        Routes.EditProduct.route, Routes.EditPromo.route -> {
                            IconButton(onClick = { onDeleteClick() }) {
                                Icon(imageVector = Icons.Rounded.Delete, contentDescription = "")
                            }
                        }
                        else -> { }
                    }
                }
            )
        }
}

@Composable
fun DetailContent(
    viewModel: ProductDetailViewModel = hiltViewModel(),
    showSnackBar:(String) -> Unit
) {
    val product = viewModel.product.value
    val detailStock by rememberSaveable { mutableStateOf(viewModel.product.value.stock) }

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())) {

        Text(text = product.name.toString(),
            Modifier
                .fillMaxWidth()
                .padding(16.dp))

        AsyncImage(
            model = product.img,
            contentScale = ContentScale.FillBounds,
            contentDescription = null,
            error = painterResource(id = R.drawable.ic_launcher_foreground),
            placeholder = painterResource(id = R.drawable.ic_launcher_background),
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )
        StatsBlock(product)

        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {

            PriceBlock(product)

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Stock disponible: " + detailStock.toString() + " unidades",
                textAlign = TextAlign.Center,
                color = Color.Gray
            )
            SubtitleText(text = "Descripción")
            Text(text = product.description.toString(), Modifier.fillMaxWidth(), color = Color.Gray)
        }
    }
}

@Composable
fun StatsBlock(product: Product) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                Purple40.copy(alpha = 0.3f),
                RoundedCornerShape(bottomEnd = 20.dp, bottomStart = 20.dp)
            )
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        StatsField(product.seenTimes.toString(), painterResource(id = R.drawable.eye_icon))
        StatsField(product.favTimes.toString(), painterResource(id = R.drawable.favorite_icon))
        StatsField(product.soldTimes.toString(), painterResource(id = R.drawable.bag_icon))
    }
}

@Composable
fun StatsField(text: String, painterResource: Painter) {
    ConstraintLayout {
        val (icon, number) = createRefs()

        Icon(
            modifier = Modifier.constrainAs(icon) {
                top.linkTo(parent.top)
            },
            painter = painterResource,
            contentDescription = "",
        )

        Text(
            modifier = Modifier.constrainAs(number) {
                top.linkTo(icon.bottom)
                start.linkTo(icon.start)
                end.linkTo(icon.end)
            },
            text = text, textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SubtitleText(text:String) {
    Text(text = text, fontSize = 20.sp)
}

@Composable
fun PriceBlock(product: Product?) {
    if (product?.isDiscount() == true){
        Column {
            Text(
                text = textWithLineThrough("$${product.oldPrice?.roundToInt()}"),
                fontSize = 16.sp,
                color = Color.Gray
            )
            Row(
                Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                TextPrice(product.newPrice?.roundToInt().toString())
                Text(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    text = "${calculateDiscountPercent(product)}% OFF",
                    fontSize = 24.sp,
                    color = Color.Green
                )
            }
        }
    } else {
        TextPrice(product?.newPrice?.roundToInt().toString())
    }
}

@Composable
fun TextPrice(price:String) {
    Text(
        text = "$${price}",
        fontSize = 32.sp,
        fontWeight = FontWeight.SemiBold
    )
}