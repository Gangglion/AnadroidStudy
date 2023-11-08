package com.glion.calculator

import android.content.res.Configuration
import android.graphics.Paint.Align
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.glion.calculator.ui.theme.CalculatorTheme
import java.text.NumberFormat
import java.util.Locale

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
    // MEMO : 선언형 UI는 UI 의 모양을 코드로 나타낸것. Compose는 초기 컴포지션을 통해서 생성되고, 리컴포지션을 통해서만 업데이트 됨
    //  그를 위해서 추적할 상태를 알아야 하고, State 혹은 MutableState 를 사용하여 관찰/추적 가능한 상태로 설정 가능
    //  저장되지 않은 값은 리컴포지션 시 다시 초기화 될 수 있다. 따라서 remember로 저장해주어야 한다.
    //  상태 호이스팅을 위해 EditNumberField 멤버에서 TipCalculatorLayout 으로 이동함
    var amountInput by remember { mutableStateOf("") }  // MEMO : 상태 저장 변수 - 청구 금액에 관련됨
    val amount = amountInput.toDoubleOrNull()?: 0.0 // MEMO : 사용자가 입력한 금액을 Double 형으로 casting
    var tipPercentInput by remember { mutableStateOf("") }
    val tipPercent = tipPercentInput.toDoubleOrNull()?: 15.0
    var isRoundUp by remember { mutableStateOf(false) }
    val tip = calculateTip(amount, tipPercent, isRoundUp)

    Column (
        modifier = Modifier
            .statusBarsPadding()
            .padding(horizontal = 40.dp)
            .verticalScroll(rememberScrollState()) // MEMO : Column을 세로로 스크롤 할 수 있게 함. rememberScrollState 는 스크롤 상태를 만들고 자동으로 기억함
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
        EditNumberField( // MEMO : 여기에서 호이스팅한 상태를 사용하고 있기 때문에 EditNumberField는 Stateless가 된다.
            label = R.string.bill_amount,
            value = amountInput,
            onValueChange = { amountInput = it },
            leadingIcon = R.drawable.money,
            keyBoardOptions = KeyboardOptions.Default.copy( // MEMO : KeyBoardOptions.Default.copy 는 다른 기본 옵션을 사용하는지 확인한다.
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            ),
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
        EditNumberField( // MEMO : 동일한 EditNumberField 재사용 - 팁 비율 나타냄
            label = R.string.tip_percent,
            value = tipPercentInput,
            onValueChange = { tipPercentInput = it },
            leadingIcon = R.drawable.percent,
            keyBoardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            modifier = Modifier
                .padding(bottom = 32.dp)
                .fillMaxWidth()
        )
        RoundSwitchRow(
            roundUp = isRoundUp,
            onRoundUpChanged = { isRoundUp = it }
        )
        Text(
            // MEMO : 팁 금액 계산 후 띄워주기 위해 EditNumberField 에서 입력받은 amountInput 변수가 필요하다
            //  amountInput 은 EditNumberField에 선언된 변수라 접근이 안되므로 [상태 호이스팅] 이 필요하다.
            text = stringResource(id = R.string.tip_amount, tip),
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.padding(top = 32.dp)
        )
        Spacer(modifier = Modifier.height(150.dp))
    }
}

@Composable
fun EditNumberField(
    @StringRes label: Int, // MEMO : @StringRes 는 문자열 리소스 참조임을 나타냄
    value: String,
    onValueChange: (String) -> Unit, // MEMO : String 값을 입력으로 받고 반환값이 없는 함수,
    @DrawableRes leadingIcon: Int,
    keyBoardOptions: KeyboardOptions,
    modifier: Modifier = Modifier
){
    TextField(
        label = { Text(text = stringResource(id = label)) },
        value = value, // MEMO : 문자열 값을 표시하는 값
        onValueChange = onValueChange, // MEMO : 텍스트 상자에 텍스트를 입력할 때 트리거되는 람다 콜백
        modifier = modifier,
        singleLine = true,
        keyboardOptions = keyBoardOptions,
        leadingIcon = { Image(painterResource(id = leadingIcon), contentDescription = null) }
    )
}

@Composable
fun RoundSwitchRow(
    roundUp: Boolean,
    onRoundUpChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .size(48.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = stringResource(id = R.string.question_round),
        )
        Switch(
            checked = roundUp,
            onCheckedChange = onRoundUpChanged,
            modifier = modifier
                .fillMaxSize()
                .wrapContentWidth(Alignment.End)
        )
    }
}

// MEMO : 팁 금액을 표시하는 함수 - 지불한 금액에 15퍼센트만큼이 팁으로 지정됨
@VisibleForTesting // MEMO : calculateTip 메서드가 같은 모듈에서는 공개되지만, @VisibleForTesting 을 추가하여 테스트목적으로 공개됨을 알림
internal fun calculateTip(amount: Double, tipPercent: Double = 15.0, isRoundUp: Boolean): String {
    var tip = tipPercent / 100 * amount
    if(isRoundUp) tip = kotlin.math.ceil(tip)
    return NumberFormat.getCurrencyInstance(Locale.US).format(tip) // MEMO : NumberFormat 형식으로 팁 형식을 통화로 표시한다.
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
