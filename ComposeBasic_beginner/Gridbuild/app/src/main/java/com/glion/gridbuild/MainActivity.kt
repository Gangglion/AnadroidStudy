package com.glion.gridbuild

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.glion.gridbuild.ui.theme.GridbuildTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GridbuildTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                }
            }
        }
    }
}

@Composable
fun GridCardRow(
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .padding(8.dp)
    ) {
        Card {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.architecture),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
                Text(
                    text = "Architecture",
                    modifier = modifier
                        .padding(8.dp),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
        Card {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.architecture),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
                Text(
                    text = "Architecture",
                    modifier = modifier
                        .padding(8.dp),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Preview
@Composable
fun GridCardRowPreview(){
    GridCardRow()
}