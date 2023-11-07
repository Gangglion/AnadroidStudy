package com.glion.calculator

import android.content.res.Configuration
import android.graphics.Paint.Align
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.glion.calculator.ui.theme.CalculatorTheme
import java.text.NumberFormat
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContent {
            CalculatorTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    TipCalculatorLayout()
                }
            }
        }
    }
}

@Composable
fun TipCalculatorLayout(){
    Column (
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp)
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally, // 가로 기준 가운데 정렬
        verticalArrangement = Arrangement.Center // 세로 기준 가운데 정렬
    ){
        Text(
            text = stringResource(id = R.string.cal_title),
            modifier = Modifier
                .padding(bottom = 16.dp, top = 40.dp)
                .align(alignment = Alignment.Start)
        )
        EditNumberField(
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
        Text(
            text = stringResource(id = R.string.tip_amount, "$0.00"),
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.height(150.dp))
    }
}

@Composable
fun EditNumberField(modifier: Modifier = Modifier){
    // MEMO : 선언형 UI는 UI 의 모양을 코드로 나타낸것. Compose는 초기 컴포지션을 통해서 생성되고, 리컴포지션을 통해서만 업데이트 됨
    //  그를 위해서 추적할 상태를 알아야 하고, State 혹은 MutableState 를 사용하여 관찰/추적 가능한 상태로 설정 가능
    //  저장되지 않은 값은 리컴포지션 시 다시 초기화 될 수 있다. 따라서 remember로 저장해주어야 한다.
    var amountInput by remember { mutableStateOf("") }  // MEMO : 상태 저장 변수 - 청구 금액에 관련됨
    val amount = amountInput.toDoubleOrNull()?: 0.0 // MEMO : 사용자가 입력한 금액을 Double 형으로 casting
    val tip = calculateTip(amount)
    TextField(
        label = {Text(stringResource(id = R.string.bill_amount))},
        value = amountInput, // MEMO : 문자열 값을 표시하는 값
        onValueChange = { amountInput = it }, // MEMO : 텍스트 상자에 텍스트를 입력할 때 트리거되는 람다 콜백
        modifier = modifier,
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
}

// MEMO : 팁 금액을 표시하는 함수 - 지불한 금액에 15퍼센트만큼이 팁으로 지정됨
private fun calculateTip(amount: Double, tipPercent: Double = 15.0): String {
    val tip = tipPercent / 100 * amount
    return NumberFormat.getCurrencyInstance().format(tip) // MEMO : NumberFormat 형식으로 팁 형식을 통화로 표시한다.
}

@Preview(
    showBackground = true,
    name = "Light Mode",
    showSystemUi = true
)
@Composable
fun TipCalculatorLayoutPreview(){
    CalculatorTheme {
        TipCalculatorLayout()
    }
}
