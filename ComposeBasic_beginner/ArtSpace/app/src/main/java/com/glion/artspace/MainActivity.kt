package com.glion.artspace

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
    Column(
        modifier = modifier
            .fillMaxSize()
    ){
        ImageArea()
        TextArea(title = R.string.title_temp, artist = R.string.artist_temp)
        Spacer(modifier.height(20.dp))
        ButtonArea(
            modifier = modifier
                .wrapContentHeight(Alignment.Bottom)
        )
    }
}

@Composable
fun ImageArea() {

}
@Composable
fun TextArea(
    modifier: Modifier = Modifier,
    title: Int,
    artist: Int
)
{
    Column(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
    ) {
        Text(
            text = "ArtWork Title",
            textAlign = TextAlign.Center,
        )
        Text(
            text = "Artwork Artist (Year)",
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun ButtonArea(modifier: Modifier = Modifier){
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.Bottom
    ){
        Button(
            onClick = {},
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
            onClick = {},
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