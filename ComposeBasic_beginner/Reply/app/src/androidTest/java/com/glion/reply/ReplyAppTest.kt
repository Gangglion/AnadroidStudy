package com.glion.reply

import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.glion.reply.ui.ReplyApp
import org.junit.Rule
import org.junit.Test

// MEMO : 화면 크기에 따라 다른 탐색요소를 사용함. 따라서 다양한 화면 크기에 관해 Navigation, NavigationRail, NavigationDrawer 등 여러 탐색요소의 존재를 확인하는 테스트 필요함
class ReplyAppTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    @Test
    @TestCompactWidth
    fun compactDevice_verifyUsingBottomNavigation(){
        composeTestRule.setContent {
            ReplyApp(windowSize = WindowWidthSizeClass.Compact) // MEMO : 소형화면 WindowWidthSizeClass 로 windowSize 지정하여 ReplyApp 호출
        }
        // MEMO : BottomNavigation 이 있는지 어설션
        composeTestRule.onNodeWithTagForStringId(R.string.navigation_bottom).assertExists()
    }

    @Test
    @TestMediumWidth
    fun mediumDevice_verifyUsingNavigationRail(){
        composeTestRule.setContent{
            ReplyApp(windowSize = WindowWidthSizeClass.Medium) // MEMO : 중형화면 WindowWidthSizeClass 로 windowSize 지정하여 ReplyApp 호출
        }
        // MEMO : NavigationRail 이 있는지 어설션
        composeTestRule.onNodeWithTagForStringId(R.string.navigation_rail).assertExists()
    }

    @Test
    @TestExpandedWidth
    fun expandedDevice_verifyUsingNavigationDrawer(){
        composeTestRule.setContent{
            ReplyApp(windowSize = WindowWidthSizeClass.Expanded) // MEMO : 대형화면 WindowWidthSizeClass 로 windowSize 지정하여 ReplyApp 호출
        }
        // MEMO : NavigationDrawer 이 있는지 어설션
        composeTestRule.onNodeWithTagForStringId(R.string.navigation_drawer).assertExists()
    }
}