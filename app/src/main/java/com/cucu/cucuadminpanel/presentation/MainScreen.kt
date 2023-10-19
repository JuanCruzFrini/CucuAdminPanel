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
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
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

    val navController = rememberNavController()

    val navBackStackEntryState = navController.currentBackStackEntryAsState()
    Scaffold(
        floatingActionButton = { FabCreateNewProduct(mainNavController) },
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = { TopBar(scrollBehavior, drawerState)/*TopAppBarController(scrollBehavior, drawerState, navBackStackEntryState, mainNavController)*/ }
    ) { innerPadding ->
        Surface(Modifier.padding(innerPadding)) {
            ProductsList(mainNavController)
        }
       /* NavHost(
            navController,
            startDestination = Routes.Home.route,
            Modifier.padding(innerPadding)
        ) {
            composable(Routes.Home.route) { ProductsList(mainNavController) }
        }*/
    }
}

@Composable
fun FabCreateNewProduct(mainNavController: NavHostController) {
    FloatingActionButton(onClick = { mainNavController.navigate(Routes.AddProduct.route) }) {
        Icon(imageVector = Icons.Rounded.Add, contentDescription = "Add new product")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarController(
    scrollBehavior: TopAppBarScrollBehavior,
    drawerState: DrawerState,
    navBackStackEntryState: State<NavBackStackEntry?>,
    mainNavController: NavHostController
) {
    return when (navBackStackEntryState.value?.destination?.route){
        //Routes.Profile.route -> TopBarProfile(scrollBehavior, drawerState, mainNavController)
        Routes.PurchaseDetail.route -> { }
        //discounts and home
        else -> TopBar(scrollBehavior = scrollBehavior, drawerState = drawerState)
    }
}