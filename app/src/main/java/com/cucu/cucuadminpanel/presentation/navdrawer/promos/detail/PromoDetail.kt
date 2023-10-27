package com.cucu.cucuadminpanel.presentation.navdrawer.promos.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.cucu.cucuadminpanel.application.Routes
import com.cucu.cucuadminpanel.data.models.promo.Promo
import com.cucu.cucuadminpanel.presentation.navdrawer.promos.PromoProductsItem
import com.cucu.cucuadminpanel.presentation.products.detail.view.FabIcon
import com.cucu.cucuadminpanel.presentation.products.detail.view.SubtitleText
import com.cucu.cucuadminpanel.presentation.products.detail.view.TextPrice
import com.cucu.cucuadminpanel.presentation.products.detail.view.TopBarNavigateBack
import com.cucu.cucuadminpanel.ui.theme.Purple40

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PromoDetail(promo: Promo?, navController: NavHostController) {

    Scaffold(
        Modifier.fillMaxSize(),
        topBar = { TopBarNavigateBack(navController){} },
        floatingActionButton = { FabIcon(
            onClick = {
                promo?.let {
                    navController.currentBackStackEntry?.savedStateHandle?.set(
                        key = "promotion",
                        value = it
                    )
                    navController.navigate(Routes.EditPromo.createRoute(it))
                }
            },
            icon = Icons.Rounded.Edit
        )
        }
    ) {
        Surface(Modifier.padding(it)){
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    text = promo?.name.toString()
                )
                Divider(color = Purple40)
                /*LazyVerticalGrid(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Purple40.copy(alpha = .3f))
                        .padding(vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    columns = GridCells.FixedSize(100.dp)){
                    items(promo?.products ?: emptyList()){
                        PromoProductsItem(it)
                    }
                }*/
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Purple40.copy(alpha = .3f))
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)){
                    items(promo?.products ?: emptyList()){
                        PromoProductsItem(it){}
                    }

                }
                Divider(color = Purple40)
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .padding(16.dp)

                ) {
                    TextPrice(price = promo?.price.toString())

                    SubtitleText("Description")

                    Text(text = promo?.description.toString())
                }
            }
        }
    }
}