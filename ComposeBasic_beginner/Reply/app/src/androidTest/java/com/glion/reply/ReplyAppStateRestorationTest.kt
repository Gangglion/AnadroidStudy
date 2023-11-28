package com.glion.reply

import androidx.activity.ComponentActivity
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyDescendant
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.StateRestorationTester
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.glion.reply.data.local.LocalEmailsDataProvider
import com.glion.reply.ui.ReplyApp
import org.junit.Rule
import org.junit.Test

class ReplyAppStateRestorationTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    // MEMO : 소형화면에서 세로모드일때 가로모드로 변경하면 구성이 변경되며 앱이 다시 실행된다. 이때 앱의 상태가 유지되는지 확인한다
    //  소형화면에서 BottomNavigation 이 나타나고, 화면을 회전하여 가로모드로 변경하면 Navigation Rail 이 나타난다.
    @Test
    @TestCompactWidth
    fun compactDevice_selectedEmailEmailRetained_afterConfigChange(){
        val stateRestorationTester = StateRestorationTester(composeTestRule) // composeTestRule 을 상태복원테스터로 감싼다. 추후 상태를 변경할 수 있다.
        stateRestorationTester.setContent {
            ReplyApp(windowSize = WindowWidthSizeClass.Compact)
        }

        // MEMO : 화면 구성 변경 전 세번째 이메일의 제목 나타나는지 확인
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(LocalEmailsDataProvider.allEmails[2].body)
        ).assertIsDisplayed()

        // MEMO : 이메일 제목 클릭하여 이메일의 세부정보로 이동한다
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(LocalEmailsDataProvider.allEmails[2].subject)
        ).performClick()

        // MEMO : 위에서 클릭했던 이메일의 세부정보가 나타나는지 확인하고, 세부정보 화면에 존재하는 뒤로가기 버튼의 존재 유무 또한 함께 확인
        composeTestRule.onNodeWithContentDescriptionForStringId(
            R.string.navigation_back
        ).assertExists() // MEMO : 뒤로가기 버튼 존재 어설션
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(LocalEmailsDataProvider.allEmails[2].body)
        ).assertExists()

        // MEMO : Simulate a config change - 구성 변경
        stateRestorationTester.emulateSavedInstanceStateRestore() // 상태를 변경한다. 이후 데이터가 복원되었는지 확인해볼수 있다.

        // MEMO : 구성 변경 이후 세번째 이메일 세부정보 화면에 존재하는지, 뒤로버튼이 존재하는지 다시 확인
        composeTestRule.onNodeWithContentDescriptionForStringId(
            R.string.navigation_back
        ).assertExists()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(LocalEmailsDataProvider.allEmails[2].body)
        ).assertExists()
    }

    @Test
    @TestExpandedWidth
    fun expandedDevice_selectedEmailEmailRetained_afterConfigChange(){
        val stateRestorationTester = StateRestorationTester(composeTestRule)
        stateRestorationTester.setContent {
            ReplyApp(windowSize = WindowWidthSizeClass.Expanded)
        }

        // MEMO : 세번째 이메일이 앱에 표시되는지 확인
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(LocalEmailsDataProvider.allEmails[2].body)
        ).assertIsDisplayed()

        // MEMO : 세번째 이메일을 선택하고, 세부정보 화면에 표시되는지 확인
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(LocalEmailsDataProvider.allEmails[2].subject)
        ).performClick()
        composeTestRule.onNodeWithTagForStringId(R.string.details_screen).onChildren()
            .assertAny(
                hasAnyDescendant( // MEMO : hasAnyDescendant - 노드에 주어진 일치자(hasText) 를 만족하는 하위 항목이 하나 이상 있는지 여부를 반환한다.
                    hasText(composeTestRule.activity.getString(LocalEmailsDataProvider.allEmails[2].body)) // MEMO : 노드에 인자로 넘긴 텍스트가 포함되어있는지 여부를 판단한다.(상위 hasAnyDescendant의 일치자(인자))
                )
            )

        stateRestorationTester.emulateSavedInstanceStateRestore()

        composeTestRule.onNodeWithTagForStringId(R.string.details_screen).onChildren()
            .assertAny(
                hasAnyDescendant(
                    hasText(composeTestRule.activity.getString(LocalEmailsDataProvider.allEmails[2].body))
                )
            )
    }
}