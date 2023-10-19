package com.cucu.cucuadminpanel.presentation.navdrawer.promos

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.cucu.cucuadminpanel.data.models.promo.Promo

@Composable
fun PromoDetail(promo: Promo?, navController: NavHostController) {

    Text(text = promo.toString())
}