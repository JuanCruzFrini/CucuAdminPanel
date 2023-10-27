package com.cucu.cucuadminpanel.presentation.navdrawer.stats

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.cucu.cucuadminpanel.application.Routes
import com.cucu.cucuadminpanel.application.calculateDiscountPercent
import com.cucu.cucuadminpanel.application.firstCharToUpperCase
import com.cucu.cucuadminpanel.data.models.Product
import com.cucu.cucuadminpanel.data.models.Stat
import kotlin.math.roundToInt

@Composable
fun StatsScreen(
    mainNavController: NavHostController,
    viewModel: StatsViewModel = hiltViewModel()
) {
    LazyColumn {
        item { StatsBox(viewModel.stat) }

        item {
            StatRow(viewModel.bestSellers, "Mas vendidos"){
                productDetailNavigator(mainNavController, it)
            }
        }
        item {
            StatRow(viewModel.mostWanted, "Mas favoritos"){
                productDetailNavigator(mainNavController, it)
            }
        }
        item {
            StatRow(viewModel.mostWatched, "Mas vistos"){
                productDetailNavigator(mainNavController, it)
            }
        }
    }
}

@Composable
fun StatsBox(stat: Stat) {
    Card(
        Modifier.padding(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.padding(16.dp)
        ) {
            StatBoxItem("Ventas totales", stat.totalSales.toString(), Modifier.weight(1f))
            StatBoxItem("Monto promedio", stat.averageAmount.toString(), Modifier.weight(1f))
            StatBoxItem("Promedio de productos", stat.averageQuantity.toString(), Modifier.weight(1f))
        }
    }
}

@Composable
fun StatBoxItem(title: String, data: String, modifier: Modifier) {
    ConstraintLayout(modifier) {
        val (titleRef, dataRef) = createRefs()

        Text(
            text = title,
            textAlign = TextAlign.Center,
            modifier = Modifier.constrainAs(titleRef) {
                top.linkTo(parent.top)
            },
            fontSize = 18.sp
        )
        Text(
            text = data,
            modifier = Modifier.constrainAs(dataRef) {
                top.linkTo(titleRef.bottom)
                start.linkTo(titleRef.start)
                end.linkTo(titleRef.end)
            }
        )
    }
}

private fun productDetailNavigator(
    navController: NavHostController,
    product: Product
) {
    navController.currentBackStackEntry?.savedStateHandle?.set("product", product)
    navController.navigate(Routes.ProductDetail.createRoute(product))
}

@Composable
fun StatRow(
    items:List<Product>,
    title:String,
    onItemClick: (Product) -> Unit
) {
    Card(
        Modifier.padding(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Text(title, Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp), fontSize = 30.sp)

        LazyRow(Modifier.padding(8.dp)) {
            items(items){
                ProductRowItem(it){ onItemClick(it) }
            }
        }
    }
}

@Composable
fun ProductRowItem(product: Product, onItemClick:(Product) -> Unit) {
    Card(
        modifier = Modifier
            .size(300.dp, 180.dp)
            .padding(8.dp)
            .clickable { onItemClick(product) },
        elevation = CardDefaults.cardElevation(2.dp),
        shape = RoundedCornerShape(20.dp),

        ) {
        Row{
            AsyncImage(
                model = product.img,
                contentDescription = "",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.weight(1.5f))
            Column(
                Modifier
                    .weight(2f)
                    .padding(8.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = product.name?.firstCharToUpperCase()!!,
                    fontSize = 20.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                )
                Text(
                    text = product.description?.firstCharToUpperCase()!!,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(2f),
                    fontWeight = FontWeight.Light
                )
                Row(
                    Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "$" + product.newPrice?.roundToInt().toString(),
                        fontSize = 28.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.SemiBold
                    )

                    if (product.isDiscount()) {
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "${calculateDiscountPercent(product)}% OFF",
                            color = Color.Green,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
            }
        }
    }
}

