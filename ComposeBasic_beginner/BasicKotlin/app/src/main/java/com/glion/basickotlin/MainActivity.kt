package com.glion.basickotlin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.glion.basickotlin.ui.theme.BasicKotlinTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicKotlinTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }

            // TODO : fun main() 코드를 여기 넣으면됨
            // MEMO : Smart 코드
            val smartTvDevice = Smart.SmartTvDevice("Android TV", "Entertainment")
            smartTvDevice.turnOn()

            val smartLightDevice = Smart.SmartLightDevice("Google Light", "Utility")
            smartLightDevice.turnOn()

            val smartHome = Smart.SmartHome(smartTvDevice, smartLightDevice)
            smartHome.printSmartTvInfo()
            smartHome.printSmartLightInfo()

            // LaMDA 코드
            val coins: (Int) -> String = { quantity ->
                "$quantity quarters" // MEMO : 반환되는 String 값. 따로 return이 존재하진 않는다.
            }
            val cupcake: (Int) -> String = {
                "Have a cupcake!"
            }
            val treatFunction = Lamda.trickOrTreat(false, coins) // MEMO : 함수를 실행한 것이 아니라 참조한 것이므로 결과값이 나오지 않음
            // MEMO : coins 자리에 결과인 { $it quarters } 를 사용할 수도 있다.
            val treatFunction2 = Lamda.trickOrTreat(false, { "$it quearters" })
            // MEMO : 함수의 매개변수 마지막이 람다표현식일 경우 밖으로 뺄 수 있다. 의미는 동일하다.
            val treatFunction3 = Lamda.trickOrTreat(false) { "$it quearters" }
            val trickFunction = Lamda.trickOrTreat(true, null)
            repeat(4){ // MEMO : for문을 간결하게 표현할 수 있음.
                trickFunction()
            }
            treatFunction()
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    BasicKotlinTheme {
        Greeting("Android")
    }
}