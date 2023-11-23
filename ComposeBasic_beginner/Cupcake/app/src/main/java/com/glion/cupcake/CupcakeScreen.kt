package com.glion.cupcake

import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.glion.cupcake.data.DataSource
import com.glion.cupcake.data.DataSource.flavors
import com.glion.cupcake.data.DataSource.quantityOptions
import com.glion.cupcake.ui.OrderSummaryScreen
import com.glion.cupcake.ui.OrderViewModel
import com.glion.cupcake.ui.SelectOptionScreen
import com.glion.cupcake.ui.StartOrderScreen

/**
 * Composable that displays the topBar and displays back button if back navigation is possible.
 */
enum class CupcakeScreen(@StringRes val title: Int){
    Start(R.string.app_name),
    Flavor(R.string.choose_flavor),
    Pickup(R.string.choose_pickup_date),
    Summary(R.string.order_summary)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CupcakeAppBar(
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    currentScreen: CupcakeScreen,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(id = currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = { // MEMO : 기본적으로 TopAppbar의 왼쪽에 배치됨
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@Composable
fun CupcakeApp(
    viewModel: OrderViewModel = OrderViewModel(),
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
) {
    val backStackEntry by navController.currentBackStackEntryAsState()

    Scaffold(
        topBar = {
            CupcakeAppBar(
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                currentScreen = CupcakeScreen.valueOf(backStackEntry?.destination?.route ?: CupcakeScreen.Start.name)
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()
        NavHost(
            navController = navController,
            startDestination = CupcakeScreen.Start.name,
            modifier = Modifier.padding(innerPadding)
        ){
            // MEMO : 경로에 따라 띄워줄 화면 설정.
            composable(route = CupcakeScreen.Start.name){
                StartOrderScreen(
                    quantityOptions = quantityOptions,
                    onNextButtonClicked = {
                        viewModel.setQuantity(it)
                        navController.navigate(CupcakeScreen.Flavor.name)
                    }
                )
            }
            composable(route = CupcakeScreen.Flavor.name){
                SelectOptionScreen(
                    subtotal = uiState.price,
                    options = flavors.map { id -> stringResource(id = id)},
                    onCancelButtonClicked = { cancelOrderAndNavigateToStart(viewModel, navController) },
                    onNextButtonClicked = {
                        navController.navigate(CupcakeScreen.Pickup.name)
                    },
                    onSelectionChanged = { viewModel.setFlavor(it) }
                )
            }
            composable(route = CupcakeScreen.Pickup.name){
                SelectOptionScreen(
                    subtotal = uiState.price,
                    options = uiState.pickupOptions,
                    onSelectionChanged = { viewModel.setDate(it) },
                    onCancelButtonClicked = { cancelOrderAndNavigateToStart(viewModel, navController) },
                    onNextButtonClicked = {
                        navController.navigate(CupcakeScreen.Summary.name)
                    }
                )
            }
            composable(route = CupcakeScreen.Summary.name){
                val context = LocalContext.current
                OrderSummaryScreen(
                    orderUiState = uiState,
                    onCancelButtonClick = { cancelOrderAndNavigateToStart(viewModel, navController) },
                    onSendButtonClick = { subject: String, summary: String ->
                        shareOrder(context, subject, summary)
                    }
                )
            }
        }
    }
}

private fun cancelOrderAndNavigateToStart(
    viewModel: OrderViewModel,
    navController: NavHostController
){
    viewModel.resetOrder()
    navController.popBackStack(CupcakeScreen.Start.name, false) // MEMO : inclusive 가 true일 경우에는 지정한 route까지 모두 삭제하고, false 일 경우에는 route위의 경로를 제거하고 route를 최상단으로 둔다.
}

private fun shareOrder(context: Context, subject: String, summary: String){
    val intent = Intent(Intent.ACTION_SEND).apply{
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, subject) // MEMO : EXTRA_SUBJECT - 제목
        putExtra(Intent.EXTRA_TEXT, summary) // MEMO : EXTRA_TEXT - 본문
    }
    context.startActivity(
        Intent.createChooser(intent, context.getString(R.string.new_cupcake_order))
    )
}
