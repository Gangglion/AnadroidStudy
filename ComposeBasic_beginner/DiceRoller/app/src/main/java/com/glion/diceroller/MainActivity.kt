package com.glion.diceroller

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.glion.diceroller.ui.theme.DiceRollerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiceRollerTheme {
                DiceRollerApp()
            }
        }
    }
}

@Composable
fun DiceWithButtonAndImage(
    modifier: Modifier = Modifier
){
    var result by remember {mutableStateOf(1)} // MEMO : mutableStateOf() 함수는 obsavarble을 반환한다(라이브데이터에서 본거)
    val imageResourceId = when(result) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        else -> R.drawable.dice_6
    }
    // MEMO : result 값이 업데이트되면 recomposable 이 트리거되고, 결과값이 반영되어 UI 가 새로고침됨
    Column(
        modifier = modifier, // MEMO : DiceWithButtonAndImage에서 전달받은 Modifier를 Column에서도 사용함. 상위요소에서 정한 규칙 준수하게 됨.
        horizontalAlignment = Alignment.CenterHorizontally, // MEMO : 열 내부에 있는 하위 요소(Image, Button)가 너비에 따라 기기 화면의 중앙에 배치됨
    ){
        Image(
            painter = painterResource(id = imageResourceId),
            contentDescription = "$result"
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            // MEMO : 코틀린의 랜덤 - "(start .. end).random()" start 와 end 포함
            onClick = { result = (1..6).random() } // MEMO : 람다 함수 { ... } 가 매개변수로 전달된다. 함수가 인수로 전달되기에 콜백이라고 한다.
        ){
            Text(stringResource(id = R.string.roll))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DiceRollerApp() {
    DiceWithButtonAndImage(modifier = Modifier // MEMO : modifier 에 속성 변경하여 DiceWithButtonAndImage 를 전달한다. 이는 곧 DiceWithButtonAndImage 가 modifier 에 전달된 속성값을 따라감을 의미함.
        .fillMaxSize()
        .wrapContentSize(Alignment.Center) // MEMO : 구성요소가 세로와 가로로 모두 중앙에 배치하도록 지정한다. 여기서는 Column이 화면 중앙에 오도록 함
    )
}