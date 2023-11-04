package com.cucu.cucuadminpanel.presentation.products.add

import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.cucu.cucuadminpanel.CucuApp
import com.cucu.cucuadminpanel.MainActivity
import com.cucu.cucuadminpanel.application.Routes
import com.cucu.cucuadminpanel.presentation.products.add.view.AddProductScreen
import com.cucu.cucuadminpanel.ui.theme.CucuAdminPanelTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(CucuApp::class)
class AddProductScreenTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    @Before
    fun setUp(){
        hiltRule.inject()
        composeTestRule.setContent {
            val navController = rememberNavController()
            CucuAdminPanelTheme {
                NavHost(navController = navController, startDestination = Routes.AddProduct.route){
                    composable(route = Routes.AddProduct.route){
                        AddProductScreen(navController)
                    }
                }
            }
        }
    }

    @Test
    fun whenFabClicked_thenCheckAnyIsEmpty(){
        //composeTestRule.setContent { AddProductScreen(NavHostController(LocalContext.current)) }

        val text = "input mock"

        composeTestRule.onNodeWithTag("Form string").performTextInput(text)

        composeTestRule.onNodeWithTag("fab").performClick()

        composeTestRule.onNodeWithTag("Form string").apply {
            assertTextEquals(text)
        }
    }
}