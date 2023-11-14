package com.glion.artspace

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.glion.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ArtSpaceLayout()
                }
            }
        }
    }
}

@Composable
fun ArtSpaceLayout(modifier: Modifier = Modifier) {
    var step: Int by remember { mutableStateOf(0) }
    Column(
        modifier = modifier
            .fillMaxSize()
    ){
        ImageArea(
            modifier
                .weight(0.7f, true)
                .padding(top = 100.dp, start = 20.dp, end = 20.dp),
            changeImageResource(step)
        )
        Spacer(modifier.height(20.dp))
        TextArea(
            info = changeResource(step)
        )
        Spacer(modifier.height(20.dp))
        ButtonArea(
            modifier = modifier
                .wrapContentHeight(Alignment.Bottom),
            onClickNext = {
                when(step){
                    0,1,2 ->{
                        step++
                    }
                    3->{
                        step = 0
                    }
                }
            },
            onClickPrev = {
                when(step){
                    1,2,3 ->{
                        step--
                    }
                    else ->{
                        step = 3
                    }
                }
            }
        )
    }
}
fun changeImageResource(step: Int): Int{
    return when(step){
        0 -> R.drawable.iron_man_1
        1 -> R.drawable.iron_man_2
        2 -> R.drawable.iron_man_3
        else -> R.drawable.avengers
    }
}
fun changeResource(step: Int): Info{
    return when(step){
        0 ->{
            Info(R.string.title_1, R.string.artist_1, R.string.year1)
        }
        1 ->{
            Info(R.string.title_2, R.string.artist_2, R.string.year2)
        }
        2->{
            Info(R.string.title_3, R.string.artist_3, R.string.year3)
        }
        else ->{
            Info(R.string.title_4, R.string.artist_4, R.string.year4)
        }
    }
}

@Composable
fun ImageArea(
    modifier: Modifier,
    image: Int
) {
    Surface(
        modifier = modifier
            .fillMaxSize()
            .wrapContentWidth(Alignment.CenterHorizontally)
            .border(
                border = BorderStroke(40.dp, Color.White),
            )
            .shadow(8.dp)
    ){
        Image(
            painter = painterResource(id = image),
            contentScale = ContentScale.FillHeight,
            contentDescription = null,
        )
    }
}
@Composable
fun TextArea(
    modifier: Modifier = Modifier,
    info: Info
)
{
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
            .background(Color.LightGray)
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(id = info.title),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge
        )
        Text(
            text = buildAnnotatedString {
                withStyle(
                    SpanStyle(fontWeight = FontWeight.Bold)
                ){
                    append(stringResource(id = info.artist))
                }
                append(stringResource(id = info.year))
            },
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun ButtonArea(
    modifier: Modifier = Modifier,
    onClickPrev: () -> Unit,
    onClickNext: ()-> Unit
){
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Bottom
    ){
        Button(
            onClick = onClickPrev,
            modifier = modifier
                .wrapContentWidth(Alignment.Start)
        )
        {
            Text(
                text = stringResource(id = R.string.previous),
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
            )
        }
        Button(
            onClick = onClickNext,
            modifier = modifier
                .wrapContentWidth(Alignment.End)
        ){
            Text(
                text = stringResource(id = R.string.next),
                modifier = Modifier
                    .padding(start = 30.dp, end = 30.dp)
            )
        }
    }
}

@Preview(
    showBackground = true,
    name = "Light_Mode"
)
@Preview(
    showBackground = true,
    name = "Dark_Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun GreetingPreview() {
    ArtSpaceTheme {
        // TODO : ArtSpaceLayout
        ArtSpaceLayout(modifier = Modifier)
    }
}