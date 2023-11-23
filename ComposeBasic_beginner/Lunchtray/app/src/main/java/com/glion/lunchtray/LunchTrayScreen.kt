/*
 * Copyright (C) 2023 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.glion.lunchtray

import android.util.Log
import androidx.annotation.StringRes
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.glion.lunchtray.datasource.DataSource.accompanimentMenuItems
import com.glion.lunchtray.datasource.DataSource.entreeMenuItems
import com.glion.lunchtray.datasource.DataSource.sideDishMenuItems
import com.glion.lunchtray.ui.AccompanimentMenuScreen
import com.glion.lunchtray.ui.CheckoutScreen
import com.glion.lunchtray.ui.EntreeMenuScreen
import com.glion.lunchtray.ui.OrderViewModel
import com.glion.lunchtray.ui.SideDishMenuScreen
import com.glion.lunchtray.ui.StartOrderScreen

enum class LunchTrayScreen(@StringRes val titleRes: Int){
    START(R.string.app_name),
    ENTREE(R.string.choose_entree),
    SIDE_DISH(R.string.choose_side_dish),
    ACCOMPANIMENT(R.string.choose_accompaniment),
    CHECK_OUT(R.string.order_checkout)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LunchTrayAppbar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    currentScreen: LunchTrayScreen,
    modifier: Modifier = Modifier
){
    TopAppBar(
        title = { Text(stringResource(id = currentScreen.titleRes)) },
        navigationIcon = {
            if(canNavigateBack){
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(id = R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun LunchTrayApp() {
    val navController: NavHostController = rememberNavController()
    // Create ViewModel
    val viewModel: OrderViewModel = viewModel()
    val backStackEntry by navController.currentBackStackEntryAsState()
    Scaffold(
        topBar = {
            LunchTrayAppbar(
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                currentScreen = LunchTrayScreen.valueOf(backStackEntry?.destination?.route ?: LunchTrayScreen.START.name)
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            navController = navController,
            startDestination = LunchTrayScreen.START.name,
            modifier = Modifier.padding(innerPadding).fillMaxSize(),
            enterTransition = { fadeIn(animationSpec = tween(0)) },
            exitTransition = { fadeOut(animationSpec = tween(0)) },
        ){
            composable(route = LunchTrayScreen.START.name){
                StartOrderScreen(
                    onStartOrderButtonClicked = { navController.navigate(LunchTrayScreen.ENTREE.name) }
                )
            }
            composable(route = LunchTrayScreen.ENTREE.name){
                EntreeMenuScreen(
                    options = entreeMenuItems,
                    onCancelButtonClicked = { navController.navigate(LunchTrayScreen.START.name) },
                    onNextButtonClicked = { navController.navigate(LunchTrayScreen.SIDE_DISH.name) },
                    onSelectionChanged = { viewModel.updateEntree(it) }
                )
            }
            composable(route = LunchTrayScreen.SIDE_DISH.name){
                SideDishMenuScreen(
                    options = sideDishMenuItems,
                    onCancelButtonClicked = { navController.navigate(LunchTrayScreen.START.name) },
                    onNextButtonClicked = { navController.navigate(LunchTrayScreen.ACCOMPANIMENT.name) },
                    onSelectionChanged = { viewModel.updateSideDish(it) }
                )
            }
            composable(route = LunchTrayScreen.ACCOMPANIMENT.name){
                AccompanimentMenuScreen(
                    options = accompanimentMenuItems,
                    onCancelButtonClicked = {navController.navigate(LunchTrayScreen.START.name) },
                    onNextButtonClicked = { navController.navigate(LunchTrayScreen.CHECK_OUT.name) },
                    onSelectionChanged = { viewModel.updateAccompaniment(it) }
                )
            }
            composable(route = LunchTrayScreen.CHECK_OUT.name){
                CheckoutScreen(
                    orderUiState = uiState,
                    onNextButtonClicked = {
                        Log.d("shhan", "Finish")
                        navController.navigate(LunchTrayScreen.START.name)
                    },
                    onCancelButtonClicked = { navController.navigate(LunchTrayScreen.START.name) })
            }
        }
    }
}
