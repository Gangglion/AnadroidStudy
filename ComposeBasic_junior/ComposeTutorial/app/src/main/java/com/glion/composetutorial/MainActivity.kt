package com.glion.composetutorial

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.glion.composetutorial.ui.Message
import com.glion.composetutorial.ui.SampleData
import com.glion.composetutorial.ui.theme.ComposeTutorialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // MEMO : Jetpack Compose 는 Material Design 및 UI 요소 즉시 사용 가능
            ComposeTutorialTheme{
                Surface(modifier = Modifier.fillMaxSize()){
                    Conversation(messages = SampleData.conversationSample)
                }
            }
        }
    }
}

// MEMO : Text 나 Image 등 Composable 요소는 @Composable 내에서 사용해야 한다.
//  Modifier(수정자) 를 사용하여 컴포저블의 크기, 레이아웃, 모양 변경뿐만이 아니라 요소 클릭 이벤트 등의 상호작용 추가 가능.
@Composable
fun MessageCard(msg: Message){
    Row(modifier = Modifier.padding(all = 8.dp)){
        Image(
            painterResource(id = R.drawable.profile_picture),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape) // MEMO : 이미지 clip
                .border(1.5.dp, MaterialTheme.colorScheme.secondary, CircleShape) // MEMO : 이미지 테두리를 원형으로 지정(CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))

        // MEMO : 확장 기능 추가 - remember 와 mutableStateOf 함수 사용.
        //  remember 사용하여 메모리에 로컬 상태 저장, mutableStateOf에 전달된 값의 변경사항 추적 가능. 값이 업데이트되면 자동으로 재구성(다시 그려짐) 됨
        var isExpanded by remember {mutableStateOf(false)}

        // MEMO : isExpanded 상태에 따라 애니메이션과 함께 배경색 변경해줌
        val surfaceColor by animateColorAsState(
            if(isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
            label = ""
        )

        Column(modifier = Modifier.clickable{ isExpanded = !isExpanded}) {
            Text(
                text = msg.author,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                // MEMO : 위에서 적용한 isExpanded 상태에 따라 달라지는 색상 추가
                color = surfaceColor,
                // MEMO : Modifier(수정자) 이용하여 애니메이션 추가. 이 Surface의 사이즈가 점진적으로 증가하는 애니메이션.
                //  padding 1dp 는 애니메이션과 상관없이 이 Surface에 여백 1dp를 주는 의미.
                modifier = Modifier.animateContentSize().padding(1.dp)
            ){
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(4.dp),
                    maxLines = if(isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

// MEMO : Compose 의 LazyColumn 과 LazyRow : 화면에 표시되는 요소만 렌더링한다.
//  LazyColumn 의 경우 List를 매개변수로 가져와 제공되는 List의 각 항목마다 호출된다.

// MEMO : @Preview 는 매개변수가 없는 Composable 함수에 사용해야 한다.
//    @Preview에 이름을 지정하여 여러개를 띄울 수 있다. 아래에서는 다크모드일때와 라이트모드일때의 두개의 미리보기를 보여준다.
@Composable
fun Conversation(messages: List<Message>){
    LazyColumn{
        items(messages){ message ->
            MessageCard(message)
        }
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun MessageCardPreview(){
    ComposeTutorialTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Conversation(messages = SampleData.conversationSample)
        }
    }
}
