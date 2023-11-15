package com.glion.affirmations

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.glion.affirmations.data.Datasrouce
import com.glion.affirmations.model.Affirmation
import com.glion.affirmations.ui.theme.AffirmationsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AffirmationsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AffirmationsApp()
                }
            }
        }
    }
}

@Composable
fun AffirmationsApp(){
    AffirmationList(
        affirmationList = Datasrouce().loadAffirmations()
    )
}

@Composable
fun AffirmationCard(affirmation: Affirmation, modifier: Modifier = Modifier){ // MEMO : 모든 컴포저블에 수정자를 전달하고 기본값을 설정하는 것이 좋다.
    Card(modifier = modifier){
        Column{
            Image(
                painterResource(id = affirmation.imageResourceId),
                contentDescription = stringResource(id = affirmation.stringResourceId),
                modifier = modifier
                    .fillMaxWidth()
                    .height(194.dp),
                contentScale = ContentScale.Crop
            )
            Text(
                text = stringResource(id = affirmation.stringResourceId),
                modifier = modifier
                    .padding(16.dp),
                style = MaterialTheme.typography.headlineSmall
            )
        }
    }
}

@Composable
fun AffirmationList(affirmationList: List<Affirmation>, modifier: Modifier = Modifier){
    // MEMO : LazyColumn 과 Column 의 차이
    //  - Column : Compose 가 한번에 모두 로드하기 때문에 표시할 항목이 적은 경우에 Column 을 사용함. 사전 정의된 고정된 개수의 컴포저블만 보유 가능
    //  - LazyColumn : 길이를 알 수 없는 긴 목록에 경우 적합. 기본적으로 스크롤 제공

    // MEMO : items - LazyColumn 에 항목을 추가하는 방법. LazyColumn의 고유한 특징
    LazyColumn(modifier = modifier){
        items(affirmationList){affirmation ->
            AffirmationCard( // MEMO : 목록의 각 항목 - 리사이클러뷰의 item 레이아웃을 지정하고 viewHolder 에서 기억, onBindViewHolder에서 그려주는 것과 비슷한 느낌
                affirmation = affirmation,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Preview
@Composable
private fun AffirmationCardPreview(){
    AffirmationsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            AffirmationsApp()
        }
    }
}
