package com.glion.greetingcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.glion.greetingcard.ui.theme.GreetingCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GreetingCardTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Surface(color = Color.Yellow) {
        Text(text = "Hi, My name is $name!", modifier = Modifier.padding(24.dp))
    }
}

@Preview(showBackground = true) // showBackground 매개변수가 true로 설정되면 앱 미리보기에 배경이 추가됨.
                                // 흰색 배경이 true로 설정됬을때임. false는 검은색 배경이 됨.
@Composable
// DefaultPreview 함수는 전체 앱을 빌드하지 않고도 어떻게 표시되는지 확인할 수 있음. @Preview 주석을 추가해야 함.
fun DefaultPreview() {
    GreetingCardTheme {
        Greeting("Glion")
    }
}