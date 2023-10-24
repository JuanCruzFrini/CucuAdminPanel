package com.cucu.cucuadminpanel.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavHostController
import com.cucu.cucuadminpanel.application.Routes
import com.cucu.cucuadminpanel.presentation.home.view.NavDrawer
import com.cucu.cucuadminpanel.presentation.home.view.ProductsList
import com.cucu.cucuadminpanel.presentation.home.view.TopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(mainNavController: NavHostController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    ModalNavigationDrawer(
        modifier = Modifier.wrapContentWidth(),
        drawerState = drawerState,
        drawerContent = { NavDrawer(drawerState, mainNavController) }
    ) {
        MainContent(drawerState, mainNavController)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainContent(drawerState: DrawerState, mainNavController: NavHostController) {
    //Expandir topappbar al scrollear
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        floatingActionButton = { FabCreateNewProduct(mainNavController) },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { TopBar(scrollBehavior, drawerState) }
    ) { innerPadding ->
        Surface(Modifier.padding(innerPadding)) {
            ProductsList(mainNavController)
        }
    }
}

@Composable
fun FabCreateNewProduct(mainNavController: NavHostController) {
    FloatingActionButton(onClick = { mainNavController.navigate(Routes.AddProduct.route) }) {
        Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add new product")
    }
}