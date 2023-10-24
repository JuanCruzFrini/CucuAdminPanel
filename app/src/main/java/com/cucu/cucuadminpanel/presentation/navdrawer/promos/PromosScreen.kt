package com.cucu.cucuadminpanel.presentation.navdrawer.promos

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.cucu.cucuadminpanel.data.models.promo.Promo
import com.cucu.cucuadminpanel.ui.theme.Purple40
import com.cucu.cucuadminpanel.ui.theme.Purple80

@Composable
fun PromosScreen(mainNavController: NavHostController) {
    PromosList(mainNavController)
}

@Composable
fun PromosList(
    navController: NavHostController,
    viewModel: PromosViewModel = hiltViewModel()
) {
    viewModel.getAllPromos()
    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        items(viewModel.promos){
            PromoItem(it, navController)
        }
    }
}

@Composable
fun PromoItem(promo: Promo, navController: NavHostController) {

    promo.products?.let { products ->
        val take = if (products.size >= 3) 3 else products.size

        Card(
            elevation = CardDefaults.cardElevation(2.dp),
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
                .clickable {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = "promotion",
                        value = promo
                    )
                    navController.navigate(Routes.PromoDetail.createRoute(promo))
                }
        ) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Purple40.copy(alpha = .3f))
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(products.take(take)){ PromoProductsItem(it){} }

                if (products.size > 3){
                    item {
                        Text(fontSize = 20.sp, text = "+ ${products.size - take}")
                    }
                }
            }

            Divider(color = Purple80)

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                text = promo.description.toString(),
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                text = "$" + promo.price.toString(),
                fontWeight = FontWeight.SemiBold,
                fontSize = 28.sp,
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
fun PromoProductsItem(product: CartProduct, isEditMode: Boolean = false, onRemoveProduct:(String)-> Unit) {
    Surface(shadowElevation = 2.dp, shape = RoundedCornerShape(20.dp)) {
            Column(
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .size(100.dp, height = 120.dp)
                    .background(Purple80.copy(0.3f), RoundedCornerShape(20.dp))
            ) {
                Box() {
                    AsyncImage(
                        modifier = Modifier.height(80.dp).width(100.dp)
                            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                            .background(Color.Black),
                        model = product.product.img, contentDescription = "")
                    if (isEditMode){
                        Text(
                            text = "X",
                            color = Color.Red,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.align(Alignment.TopStart)
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(topStart = 8.dp, bottomEnd = 8.dp)
                                )
                                .padding(8.dp)
                                .clickable { onRemoveProduct(product.product.id!!) }
                        )
                    }
                    Text(
                        text = product.quantity.toString(),
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.align(Alignment.BottomEnd)
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(
                                    topStart = 8.dp,
                                    topEnd = 8.dp,
                                    bottomStart = 8.dp
                                )
                            ).padding(8.dp)
                    )
                }
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textAlign = TextAlign.Center,
                    text = product.product.name.toString(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis)
            }
    }
}