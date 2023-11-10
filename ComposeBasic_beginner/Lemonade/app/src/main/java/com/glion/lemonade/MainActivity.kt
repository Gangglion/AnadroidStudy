package com.glion.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.glion.lemonade.ui.theme.LemonadeTheme
import com.glion.lemonade.ui.theme.LightGreen
import com.glion.lemonade.ui.theme.Yellow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LemonadeLayout(){
    var step by remember {mutableStateOf(1)}
    var squeezeCount by remember { mutableStateOf(0) }
    var touchCount = 0
    val imageResourceId: Int
    val stringResourceId: Int
    when (step % 4) {
        1 -> {
            imageResourceId = R.drawable.lemon_tree
            stringResourceId = R.string.lemon_step_1
        }
        2 -> {
            squeezeCount = (2..4).random()
            imageResourceId = R.drawable.lemon_squeeze
            stringResourceId = R.string.lemon_step_2
        }
        3 -> {
            imageResourceId = R.drawable.lemon_drink
            stringResourceId = R.string.lemon_step_3
        }
        else -> {
            imageResourceId = R.drawable.lemon_restart
            stringResourceId = R.string.lemon_step_4
            touchCount = 0
        }
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name)
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(Yellow)
            )
        },
    ) { _ ->
        ImageAndText(
            imageResourceId,
            stringResourceId,
            onClick = {
                if (step % 4 == 2) {
                    touchCount++
                    if (touchCount == squeezeCount)
                        step++
                } else {
                    step++
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
    }
}

@Composable
fun ImageAndText(
    @DrawableRes image: Int,
    @StringRes text: Int,
    onClick: () -> Unit,
    modifier: Modifier
){
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally

    ){
        Button(
            modifier = Modifier
                .background(LightGreen, RoundedCornerShape(30.dp)) // MEMO : 모양에 라운드 지정.
                .padding(20.dp),
            colors = ButtonDefaults.buttonColors(LightGreen),
            onClick = { onClick() }) {
            Image(painter = painterResource(id = image), contentDescription = null)
        }
        Spacer(Modifier.height(16.dp))
        Text(
            text = stringResource(id = text)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun LemonadeApp() {
    LemonadeTheme {
        LemonadeLayout()
    }
}