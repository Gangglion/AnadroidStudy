package com.glion.businesscardapp

import android.graphics.Paint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.glion.businesscardapp.ui.theme.BusinessCardAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BusinessCardAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    MainLogo()
                }
            }
        }
    }
}

@Composable
fun MainLogo(modifier: Modifier = Modifier) {
    Column(
        modifier
            .fillMaxSize()
            .background(Color(0xFF00283F)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(modifier.weight(0.7f)) {
            TopCard(
                painterResource(id = R.drawable.android_logo),
                stringResource(id = R.string.user_name),
                stringResource(id = R.string.explain),
                modifier = modifier
            )
        }
        Row(
            Modifier
                .weight(0.3f)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                BottomCard(
                    painterResource(id = R.drawable.ic_baseline_call),
                    stringResource(id = R.string.phone_num),
                )
                BottomCard(
                    painterResource(id = R.drawable.ic_baseline_share_24),
                    stringResource(id = R.string.share),
                )
                BottomCard(
                    painterResource(id = R.drawable.ic_baseline_email_24),
                    stringResource(id = R.string.email),
                )
            }
        }
    }
}

@Composable
fun TopCard(
    image: Painter,
    name: String,
    explain: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = image, contentDescription = null, modifier.height(100.dp).width(150.dp))
        Text(name, color = Color.White, fontSize = 40.sp)
        Text(
            explain,
            color = Color(0xFF3ddc84),
            fontWeight = FontWeight.Bold,
            modifier = modifier.padding(10.dp)
        )
    }
}

@Composable
fun BottomCard(
    image: Painter,
    type: String,
    modifier: Modifier = Modifier
) {
    Column{
        Divider(modifier = modifier.fillMaxWidth(), thickness = 2.dp, color = Color(0xFF526E7B))
        Row(
            Modifier.padding(start = 40.dp ,top=5.dp, bottom = 5.dp),
        ) {
            Image(
                painter = image,
                contentDescription = null
            )
            Text(
                text = type,
                color = Color.White,
                modifier = Modifier.padding(start = 20.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun BusinessCardPreview() {
    BusinessCardAppTheme {
        MainLogo()
    }
}