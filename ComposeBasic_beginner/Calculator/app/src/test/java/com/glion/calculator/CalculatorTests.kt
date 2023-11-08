package com.glion.calculator

import junit.framework.TestCase.assertEquals
import org.junit.Test
import java.text.NumberFormat
import java.util.Locale

class CalculatorTests {

    @Test // MEMO : 테스트함수임을 명시
    fun calculateTip_20PercentNoRoundUp(){
        val amount = 10.00
        val tipPercent = 20.00
        val expectedTip = NumberFormat.getCurrencyInstance(Locale.US).format(2) // MEMO : 예상값. 직접 넣어주고, MainActivity에서 사용하는것과 같은 형식으로 지정한다. 10.00 달러에 20.00 퍼센트이면 팁은 2달러이다.
        val actualTip = calculateTip(amount, tipPercent, false) // MEMO : 실제 함수를 돌렸을 때의 값.
        // MEMO : 실제 반환된 값이 올바른 값인지 어설션으로 확인해야함. 예상값과 실제값이 같은지를 확인해야하므로 JUnit 라이브러리의 assertEquals() 메서드를 사용한다.
        assertEquals(expectedTip, actualTip)
    }
}