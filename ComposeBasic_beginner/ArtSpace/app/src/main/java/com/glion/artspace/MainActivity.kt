package com.glion.artspace

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
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
                    // TODO : ArtSpaceLayout
                    ArtSpaceLayout(modifier = Modifier)
                }
            }
        }
    }
}

@Composable
fun ArtSpaceLayout(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        ImageArea()
    }
}

@Composable
fun ImageArea() {

}

@Composable
fun ButtonArea(modifier: Modifier = Modifier){
    Row(
        modifier = modifier
            .fillMaxWidth()
    ){
        Button(
            onClick = {},
            modifier = modifier
                .wrapContentWidth(Alignment.Start)
        )
        {
            Text(text = stringResource(id = R.string.previous))
        }
        Button(
            onClick = {},
            modifier = modifier
                .wrapContentWidth(Alignment.End)
        ){
            Text(text = stringResource(id = R.string.next))
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
        ButtonArea()
    }
}