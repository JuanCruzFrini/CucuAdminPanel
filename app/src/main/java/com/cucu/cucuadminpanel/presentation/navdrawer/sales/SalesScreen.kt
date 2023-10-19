package com.cucu.cucuadminpanel.presentation.navdrawer.sales

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.cucu.cucuadminpanel.application.Routes
import com.cucu.cucuadminpanel.data.models.cart.CartProduct
import com.cucu.cucuadminpanel.data.models.purchase.Purchase
import com.cucu.cucuadminpanel.data.models.purchase.PurchaseReference

@Composable
fun SalesScreen(
    mainNavController: NavHostController,
    viewModel: SalesViewModel = hiltViewModel()
) {
    viewModel.getSales()
    LazyColumn {
        items(viewModel.sales){
            ItemSale(it, mainNavController)
        }
    }
}

@Composable
fun ItemSale(sale: Purchase, navController: NavHostController) {

    var paddingStart = 0.dp
    var listToDraw: List<CartProduct>
    var drawNumber: Boolean

    sale.products.let { productList ->
        when {
            productList!!.size > 3 -> {
                listToDraw = productList.subList(0,3)
                drawNumber = true
            }
            else -> {
                listToDraw = productList
                drawNumber = false
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .clickable {
                    sale.id?.let { id ->
                        val purchaseReference = PurchaseReference(id, sale.userId)
                        navController.currentBackStackEntry?.savedStateHandle?.set(
                            key = "purchaseReference",
                            value = purchaseReference
                        )
                        navController.navigate(Routes.PurchaseDetail.createRoute(purchaseReference))
                    }
                },
            elevation = CardDefaults.cardElevation(3.dp),
        ) {
            Row(Modifier.padding(8.dp)) {
                Box(Modifier.weight(1.5f)) {
                    listToDraw.forEach { cart ->

                        AsyncImage(
                            modifier = Modifier
                                .size(150.dp, 150.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .padding(start = paddingStart),
                            model = cart.product.img,
                            contentScale = ContentScale.FillBounds,
                            contentDescription = null
                        )

                        paddingStart += 30.dp
                    }

                    if (drawNumber) {
                        Text(
                            text = "${productList.size - listToDraw.size}+",
                            fontSize = 24.sp,
                            color = Color.DarkGray,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .align(Alignment.BottomEnd)
                                .border(2.dp, Color.DarkGray, RoundedCornerShape(10.dp))
                                .background(Color.White, RoundedCornerShape(10.dp))
                                .padding(4.dp)
                        )
                    }
                }

                Column(
                    modifier = Modifier.padding(start = 8.dp).weight(2f),
                    verticalArrangement = Arrangement.SpaceEvenly
                ) {
                    Text(
                        text = "$${sale.getAmount()}",
                        fontSize = 28.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        text = "${productList.size} productos",
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.Light
                    )
                    Text(
                        text = sale.state!!,
                        fontSize = 20.sp,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                    )
                }

            }
        }
    }
}
