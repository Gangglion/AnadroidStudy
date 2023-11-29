package com.glion.sports

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.glion.sports.ui.SportsApp
import com.glion.sports.ui.theme.SportsTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SportsTheme {
                val windowSize = calculateWindowSizeClass(activity = this).widthSizeClass
                Surface {
                    SportsApp(windowSize)
                }
            }
        }
    }
}

@Preview(widthDp = 400)
@Composable
fun CompactSportsAppPreview(){
    SportsTheme {
        Surface {
            SportsApp(WindowWidthSizeClass.Compact)
        }
    }
}
@Preview(widthDp = 700)
@Composable
fun MediumSportsAppPreview(){
    SportsTheme {
        Surface {
            SportsApp(WindowWidthSizeClass.Medium)
        }
    }
}
@Preview(widthDp = 1000)
@Composable
fun ExpandedSportsAppPreview(){
    SportsTheme {
        Surface {
            SportsApp(WindowWidthSizeClass.Expanded)
        }
    }
}