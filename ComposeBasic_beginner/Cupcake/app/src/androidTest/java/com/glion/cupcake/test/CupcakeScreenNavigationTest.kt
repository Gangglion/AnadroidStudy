package com.glion.cupcake.test

import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.glion.cupcake.CupcakeApp
import com.glion.cupcake.R
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

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
        CupcakeApp()
    }
}

// MEMO : 앱의 경로를 나타내는 Enum Class 추가 - 메인에서 사용하는것과 동일
enum class CupcakeScreen(@StringRes val title: Int){
    Start(R.string.app_name),
    Flavor(R.string.choose_flavor),
    Pickup(R.string.choose_pickup_date),
    Summary(R.string.order_summary)
}

@Test
fun cupcakeNavHost_verifyStartDestination(){
    navController.assertCurrentRouteName(CupcakeScreen.Start.name) // MEMO : CupcakeScreen.Start.name이 탐색 컨트롤러의 현재 백스택 항목의 대상경로와 같은지 확인
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

// MEMO : 특정 화면으로 이동하는 도우미 함수. 테스트 함수는 각각 별개로 실행되기 때문에, 만약 픽업날짜 화면으로 이동하려면 갯수선택->맛 항목 선택 -> 다음버튼 클릭 이 필요하다. 이런 반복 코드를 줄여주기 위해 특정 영역으로 가는 도우미 함수를 만든다
private fun navigateToFlavorScreen(){
    composeTestRule.onNodeWithStringId(R.string.one_cupcake).performClick()
    composeTestRule.onNodeWithStringId(R.string.chocolate).performClick()

}

// MEMO : 위의 함수와 마찬가지로, 각 화면으로 이동하기 위한 함수
private fun navigateToPickUpScreen(){
    navigateToFlavorScreen()
    composeTestRule.onNodeWithStringId(R.string.next).performClick()
}
private fun navigateToSummaryScreen(){
    navigateToPickUpScreen()
    composeTestRule.onNodeWithText(getFormattedDate()).performClick()
    composeTestRule.onNodeWithStringId(R.string.next).performClick()
}
private fun performNavigateUp(){
    val backText = composeTestRule.activity.getString(R.string.back_button)
    composeTestRule.onNodeWithContentDescription(backText).performClick()
}

private fun getFormattedDate(): String{
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DATE, 1)
    val formatter = SimpleDateFormat("E MM d", Locale.getDefault())
    return formatter.format(calendar.time)
}

class CupcakeScreenNavigationTest{

}


