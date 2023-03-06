package com.glion.composepractice

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.glion.composepractice.ui.theme.ComposePracticeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposePracticeTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background
                ) {
                    // Compose 도움말
                    // ComposeArticleApp()

                    // Task Manager
                    // CompletedScene()

                    // Compose 사분면
                    ComposeFourSection()
                }
            }
        }
    }
}

/* Compose 도움말
@Composable
fun ComposeArticleApp(modifier: Modifier = Modifier) {
    ArticleCard(
        title = stringResource(id = R.string.text_title),
        head = stringResource(id = R.string.text_head),
        body = stringResource(id = R.string.text_body),
        image = painterResource(id = R.drawable.bg_compose_background)
    )
}

@Composable
fun ArticleCard(
    title: String,
    head: String,
    body: String,
    image: Painter,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Image(painter = image, contentDescription = null)
        Text(
            text = stringResource(id = R.string.text_title),
            fontSize = 24.sp,
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 16.dp)
        )
        Text(
            text = stringResource(id = R.string.text_head),
            modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            textAlign = TextAlign.Justify
        )
        Text(
            text = stringResource(id = R.string.text_body),
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 16.dp),
            textAlign = TextAlign.Justify
        )
    }
}

@Preview(showBackground = true)
@Composable
fun HelperImagePreview() {
    ComposePracticeTheme {
        Surface {
            ComposeArticleApp()
        }
    }
}
*/
/*  Task Manager
@Composable
fun CompletedScene(modifier: Modifier = Modifier) {
    val image = painterResource(id = R.drawable.ic_task_completed)
    Column(
        modifier = modifier.fillMaxWidth().fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = image, contentDescription = null)
        Text(
            text = stringResource(id = R.string.task_title),
            modifier = Modifier.padding(top = 24.dp, bottom = 8.dp),
            fontWeight = FontWeight.Bold
        )
        Text(text = stringResource(id = R.string.task_body), fontSize = 16.sp)
    }
}

@Preview(showBackground = true)
@Composable
fun TaskManagerPreview() {
    ComposePracticeTheme {
        CompletedScene()
    }
}
*/
// Compose 사분면 - try
@Composable
fun ComposeFourSection(modifier: Modifier = Modifier) {
    Column(modifier.fillMaxWidth()) {
        Row(Modifier.weight(1f)) {
            ComposeOneSection(
                stringResource(id = R.string.compose_text1),
                stringResource(id = R.string.compose_text2),
                Color.Green,
                modifier = Modifier.weight(1f)
            )
            ComposeOneSection(
                stringResource(id = R.string.compose_image1),
                stringResource(id = R.string.compose_image2),
                Color.Yellow,
                modifier = Modifier.weight(1f)
            )
        }
        Row(Modifier.weight(1f)) {
            ComposeOneSection(
                stringResource(id = R.string.compose_row1),
                stringResource(id = R.string.compose_row2),
                Color.Cyan,
                modifier = Modifier.weight(1f)
            )
            ComposeOneSection(
                stringResource(id = R.string.compose_column1),
                stringResource(id = R.string.compose_column2),
                Color.LightGray,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun ComposeOneSection(
    title: String,
    body: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(backgroundColor)
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = body,
            textAlign = TextAlign.Justify
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ComposeFourSectionPreview() {
    ComposePracticeTheme {
        Surface {
            ComposeFourSection()
        }
    }
}
