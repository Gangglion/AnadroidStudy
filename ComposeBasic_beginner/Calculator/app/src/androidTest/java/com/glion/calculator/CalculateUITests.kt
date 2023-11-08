package com.glion.calculator

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.glion.calculator.ui.theme.CalculatorTheme
import org.junit.Rule
import org.junit.Test
import java.text.NumberFormat
import java.util.Locale

class CalculateUITests {

    @get:Rule
    val composeTestRule = createComposeRule() // MEMO : Compose로 빌드된 앱의 모든 계측테스트를 작성하기 전에 무조건 해야함. 이걸 사용해서 UI구성요소에 액세스 할 수 있다

    @Test // MEMO : 컴파일러는 디렉토리에 따라 같은 주석이여도 계측테스트, 로컬테스트를 나타내는지 인식한다. 여기선 계측테스트임을 자동으로 인식한다.
    fun calculate_20_percent_tip(){
        composeTestRule.setContent { // MEMO : MainActivity와 동일하게 작성한다.
            CalculatorTheme {
                Surface(modifier = Modifier.fillMaxSize()){
                    TipCalculatorLayout()
                }
            }
        }
        composeTestRule.onNodeWithText("Bill Amount") // MEMO : 청구금액에 대한 TextField 컴포저블에 액세스
            .performTextInput("10")
        composeTestRule.onNodeWithText("Tip Percentage").performTextInput("20")
        val expectTip = NumberFormat.getCurrencyInstance(Locale.US).format(2) // MEMO : 예상되는 값.

        // MEMO : 2개의 TextField에 값을 넣었으므로, 어설션을 통해 올바른 팁이 표시되는지 확인해야함. 계측테스트에서 어설션은 UI구성요소에서 직접 호출 가능
        composeTestRule.onNodeWithText("Tip Amount: $expectTip").assertExists( // MEMO : assertExists의 인자로, 테스트 실패시 나타날 메세지를 입력한다. 결과값이 예상(expectTip)과 다르면, AssertionError가 나오며, 여기에 전달한 메세지가 함께 나온다.
            "No node with this text was found"
        )
    }
}