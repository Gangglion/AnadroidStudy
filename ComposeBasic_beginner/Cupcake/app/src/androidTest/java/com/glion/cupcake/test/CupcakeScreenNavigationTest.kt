package com.glion.cupcake.test

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.glion.cupcake.CupcakeApp
import com.glion.cupcake.CupcakeScreen
import com.glion.cupcake.R
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class CupcakeScreenNavigationTest{
    @get: Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    // MEMO : UI테스트에 사용할 컴포저블 지정
    // MEMO : CupcakeScreenNavigationTest 클래스의 모든 테스트에는 화면 탐색 측면의 테스트가 포함된다. 따라서 각 테스트는 TestNavHostController 에 따라 달라지고 모든 테스트에서 아래 함수를 수동으로 호출해야 한다.
    //  Junit 에서 제공하는 @Before 주석을 이용하여 수동으로 호출하지 않고 자동으로 가능하다
    @Before
    fun setUpCupcakeNavHost() {
        composeTestRule.setContent{
            // MEMO : TestNavHostController 객체 생성 - 후에 이 객체를 이용하여 탐색 상태 확인
            navController = TestNavHostController(LocalContext.current).apply{
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            CupcakeApp(navController = navController) // MEMO : 테스트 시에 사용하는 navController 를 전달해주어야 함
        }
    }

    // MEMO : 특정 화면으로 이동하는 도우미 함수. 테스트 함수는 각각 별개로 실행되기 때문에, 만약 픽업날짜 화면으로 이동하려면 갯수선택->맛 항목 선택 -> 다음버튼 클릭 이 필요하다. 이런 반복 코드를 줄여주기 위해 특정 영역으로 가는 도우미 함수를 만든다
    /**
     * 맛 화면으로 이동
     */
    private fun navigateToFlavorScreen(){
        composeTestRule.onNodeWithStringId(R.string.one_cupcake).performClick()
        composeTestRule.onNodeWithStringId(R.string.chocolate).performClick()
    }

    /**
     * 픽업 화면으로 이동
     */
    private fun navigateToPickUpScreen(){
        navigateToFlavorScreen()
        composeTestRule.onNodeWithStringId(R.string.next).performClick()
    }

    /**
     * 요약 화면으로 이동
     */
    private fun navigateToSummaryScreen(){
        navigateToPickUpScreen()
        composeTestRule.onNodeWithText(getFormattedDate()).performClick()
        composeTestRule.onNodeWithStringId(R.string.next).performClick()
    }

    /**
     * TopAppBar의 뒤로가기 버튼 클릭
     */
    private fun performNavigateUp(){
        val backText = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).performClick()
    }

    private fun getFormattedDate(): String{
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DATE, 1)
        val formatter = SimpleDateFormat("E MMM d", Locale.getDefault())
        return formatter.format(calendar.time)
    }

    // MEMO : CupcakeScreen.Start.name이 탐색 컨트롤러의 현재 백스택 항목의 대상경로와 같은지 확인
    @Test
    fun cupcakeNavHost_verifyStartDestination(){
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    // MEMO : 시작화면에는 뒤로가기 버튼이 존재해서는 안된다. 이를 테스트한다.
    @Test
    fun cupcakeNavHost_verifyBackNavigationNotShownOnStartOrderScreen(){
        val backText = composeTestRule.activity.getString(R.string.back_button)
        // MEMO : 위의 콘텐츠 설명이 있는 노드(TopAppBar의 뒤로가기 버튼)가 없음을 어설션함
        composeTestRule.onNodeWithContentDescription(backText).assertDoesNotExist()
    }

    // MEMO : 시작화면에서 컵케이크의 개수를 나타내는 버튼을 클릭하면, 맛 선택 화면으로 이동하는 메서드가 실행된다. 이동 경로가 맛 화면인지 확인하는 테스트 작성한다
    @Test
    fun cupcakeNaHost_clickOneCupcake_navigatesToSelectFlavorScreen(){
        composeTestRule.onNodeWithStringId(R.string.one_cupcake).performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Flavor.name) // MEMO : navController 의 현재 라우트 이름이 () 안에 넣은 것이라는 어설션
    }

    // MEMO : 맛 화면에서 TopAppBar의 뒤로가기 버튼을 클릭하여 시작 화면으로 이동하는 테스트
    @Test
    fun cupcakeNavHost_clickBackInFlavor_navigateToStartOrderScreen(){
        navigateToFlavorScreen()
        performNavigateUp()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    // MEMO : 맛 화면에서 취소(Cancel) 버튼을 클릭하여 시작 화면으로 이동하는 테스트
    @Test
    fun cupcakeNavHost_clickCancelInFlavor_navigateToStartOrderScreen(){
        navigateToFlavorScreen()
        composeTestRule.onNodeWithStringId(R.string.cancel).performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }
    // MEMO : 수령 화면으로 이동(PickUp 화면)하는 테스트
    @Test
    fun cupcakeNavHost_clickNextInFlavor_navigateToPickupScreen(){
        navigateToPickUpScreen()
        navController.assertCurrentRouteName(CupcakeScreen.Pickup.name)
    }

    // MEMO : 수령화면에서 TopAppBar의 뒤로가기 버튼을 클릭하여 맛 화면으로 이동 테스트
    @Test
    fun cupcakeNavHost_clickBackInPickup_navigateToFlavorScreen(){
        navigateToPickUpScreen()
        performNavigateUp()
        navController.assertCurrentRouteName(CupcakeScreen.Flavor.name)
    }

    // MEMO : 수령화면에서 취소 버튼 클릭하여 시작 화면으로 이동 테스트
    @Test
    fun cupcakeNavHost_clickCancelInPickup_navigateToStartOrderScreen(){
        navigateToPickUpScreen()
        composeTestRule.onNodeWithStringId(R.string.cancel).performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }

    // MEMO : 요약화면으로 이동
    @Test
    fun cupcakeNavHost_clickNextInPickup_navigateToSummaryScreen(){
        navigateToSummaryScreen()
        navController.assertCurrentRouteName(CupcakeScreen.Summary.name)
    }

    // MEMO : 요약화면에서 취소 버튼을 클릭하여 시작 화면으로 이동하는 테스트
    @Test
    fun cupcakeNavHost_clickCancelInSummary_navigateToStartOrderScreen(){
        navigateToSummaryScreen()
        composeTestRule.onNodeWithStringId(R.string.cancel).performClick()
        navController.assertCurrentRouteName(CupcakeScreen.Start.name)
    }
}


