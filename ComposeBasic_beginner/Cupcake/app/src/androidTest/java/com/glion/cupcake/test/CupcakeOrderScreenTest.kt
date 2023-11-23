package com.glion.cupcake.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsEnabled
import androidx.compose.ui.test.assertIsNotEnabled
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.glion.cupcake.ui.SelectOptionScreen
import org.junit.Rule
import org.junit.Test
import com.glion.cupcake.R
import com.glion.cupcake.data.DataSource.quantityOptions
import com.glion.cupcake.data.OrderUiState
import com.glion.cupcake.ui.OrderSummaryScreen
import com.glion.cupcake.ui.StartOrderScreen
import org.junit.Before
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

// MEMO : 사용자와의 화면 상호작용 결과 테스트
class CupcakeOrderScreenTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    fun selectOptionScreenFlavors_verifyContent(){
        val flavors = listOf("Vanilla", "Chocolate", "Hazelnut", "Cookie", "Mango")
        val subtotal = "$100"

        // MEMO : 상호작용 하는 화면 SelectOptionScreen 으로 바로 접근
        composeTestRule.setContent {
            SelectOptionScreen(subtotal = subtotal, options = flavors)
        }

        // MEMO : 나타난 목록의 문자열 항목이 제공한것과 동일한지 확인
        flavors.forEach{flavor ->
            composeTestRule.onNodeWithText(flavor).assertIsDisplayed() // flavors 리스트를 한개씩 돌면서 IsDisplayed, 화면에 표시됬는지 확인
        }

        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(R.string.subtotal_price, subtotal)
        )

        // MEMO : 아직 항목을 선택하지 않았으므로, Next 버튼은 활성화되어있으면 안됨.
        composeTestRule.onNodeWithStringId(R.string.next).assertIsNotEnabled()

        // MEMO : 옵션 선택 후 Next 버튼 활성화 여부 확인
        composeTestRule.onNodeWithText(flavors[0]).performClick()
        composeTestRule.onNodeWithStringId(R.string.next).assertIsEnabled()
    }

    // MEMO : pickup 화면 콘텐츠 확인
    @Test
    fun selectOptionScreenPickup_verifyContent(){
        val pickupOptions = pickupOptions()
        val subtotal = "$100"

        composeTestRule.setContent {
            SelectOptionScreen(subtotal = subtotal, options = pickupOptions)
        }

        pickupOptions.forEach { pickup ->
            composeTestRule.onNodeWithText(pickup).assertIsDisplayed()
        }

        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(R.string.subtotal_price, subtotal)
        )

        composeTestRule.onNodeWithStringId(R.string.next).assertIsNotEnabled()

        composeTestRule.onNodeWithText(pickupOptions[0]).performClick()
        composeTestRule.onNodeWithStringId(R.string.next).assertIsEnabled()
    }

    private fun pickupOptions(): List<String> {
        val dateOptions = mutableListOf<String>()
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        val calendar = Calendar.getInstance()
        // add current date and the following 3 dates.
        repeat(4) {
            dateOptions.add(formatter.format(calendar.time))
            calendar.add(Calendar.DATE, 1)
        }
        return dateOptions
    }

    // MEMO : 시작화면 콘텐츠 확인
    @Test
    fun startOnScreen_verifyContent(){
        val quantityOptions = quantityOptions
        
        composeTestRule.setContent { 
            StartOrderScreen(quantityOptions = quantityOptions)
        }

        quantityOptions.forEach { item->
            composeTestRule.onNodeWithStringId(item.first).assertIsDisplayed()
        }
    }

    // MEMO : 요약화면 콘텐츠 확인
    @Test
    fun summaryScreen_verifyContent(){
        val fakeUiState = OrderUiState(
            quantity = 2,
            flavor = "Vanilla",
            date = "목 11월 23",
            price = "100$",
            pickupOptions = listOf()
        )
        composeTestRule.setContent {
            OrderSummaryScreen(orderUiState = fakeUiState)
        }

        val expectedQuantity = composeTestRule.activity.resources.getQuantityString(
            R.plurals.cupcakes,
            fakeUiState.quantity,
            fakeUiState.quantity
        )
        composeTestRule.onNodeWithText(expectedQuantity).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeUiState.flavor).assertIsDisplayed()
        composeTestRule.onNodeWithText(fakeUiState.date).assertIsDisplayed()
        val expectedSubtotal = composeTestRule.activity.getString(R.string.subtotal_price, fakeUiState.price)
        composeTestRule.onNodeWithText(expectedSubtotal).assertIsDisplayed()
    }
}