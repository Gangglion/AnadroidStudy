package com.glion.gridbuild

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.glion.gridbuild.data.GridDataSource
import com.glion.gridbuild.model.CardData
import com.glion.gridbuild.model.GridRowData
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
                    GridCardList(GridDataSource().getData())
                }
            }
        }
    }
}

@Composable
fun GridCardList(
    gridRowData: List<GridRowData>,
    modifier: Modifier = Modifier
){
    LazyColumn(
        modifier = modifier
    ){
        items(gridRowData){gridData ->
            GridCardRow(gridData = gridData)
        }
    }
}

@Composable
fun GridCardRow(
    gridData: GridRowData,
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .padding(start = 8.dp, top = 8.dp, end = 8.dp)
    ) {
        CardUI(gridData.cardData1, modifier.weight(1f))
        CardUI(gridData.cardData2,
            modifier
                .weight(1f)
                .padding(start = 8.dp))
    }
}

@Composable
fun CardUI(data: CardData, modifier: Modifier = Modifier){
    Card(
        modifier = modifier
    ) {
        Row {
            Image(
                painter = painterResource(id = data.drawableRes),
                contentScale = ContentScale.Crop,
                contentDescription = null
            )
            Column(
                modifier = Modifier
                    .padding(8.dp)
            ){
                Text(
                    text = stringResource(id = data.stringRes),
                    style = MaterialTheme.typography.bodySmall
                )
                Row(
                    modifier = Modifier
                        .padding(top = 4.dp)
                ){
                    Image(
                        painter = painterResource(id = R.drawable.ic_grain),
                        contentDescription = null
                    )
                    Text(
                        text = data.count.toString(),
                        style = MaterialTheme.typography.labelSmall
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun GridCardRowPreview(){
    GridbuildTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            GridCardList(GridDataSource().getData())
        }
    }
}