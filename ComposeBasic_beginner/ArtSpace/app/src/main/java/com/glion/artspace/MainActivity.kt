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

private lateinit var utility: Utility
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        utility = Utility() // MEMO : Utility 객체 초기화
        setContent { // MEMO : 앱 시작점
            ArtSpaceTheme { // MEMO : ui/theme/Theme.kt 에 정의된 스타일을 따르는 ArtSpaceLayout() 컴포저블 생성. ArtSpaceTheme 는 패키지명+Theme 로 자동생성됨
                ArtSpaceLayout()
            }
        }
    }
}

@Composable
fun ArtSpaceLayout(modifier: Modifier = Modifier) { // MEMO : modifier 가 인자로 안들어오면 기본 Modifier사용
    var step: Int by remember { mutableStateOf(0) } // MEMO : 상태 저장 변수. remember로 저장되어 recomposable 시에도 값이 유지됨. mutableStateOf 로 변경가능한 값을 지정하고, 추적/관찰할 수 있음
    Column(
        modifier = modifier
            .fillMaxSize() // MEMO : Column 이 화면 전체에 가득 차도록 modifier를 이용하여 fillMaxSize 해줌
    ){
        PosterArea(
            modifier
                .weight(0.7f, true) // MEMO : 이미지 영역을 부모 요소(Column) 의 70퍼센트로 지정
                .padding(top = 100.dp, start = 20.dp, end = 20.dp), // MEMO : 지정한 값 만큼 이미지 영역에 Padding 추가
            utility.getImageResource(step) // MEMO : step 별 다른 이미지를 얻기 위함.
        )
        Spacer(modifier.height(20.dp)) // MEMO : 이미지 영역과 Text영역 사이에 여백 줌
        InfoArea(
            info = utility.getStringResources(step) // MEMO : Text영역에 리소스 넣어줌
        )
        Spacer(modifier.height(20.dp)) // MEMO : Text영역과 Button 영역 사이에 여백 줌
        ControllerArea( // MEMO : 상태 호이스팅 - 클릭 이벤트를 인자로 주고, ControllerArea를 stateless 로 만듬
            onClickNext = { // MEMO : "다음" 버튼 눌렀을때의 이벤트. step의 값이 0123 반복됨
                when(step){
                    0,1,2 ->{
                        step++
                    }
                    3->{
                        step = 0
                    }
                }
            },
            onClickPrev = { // MEMO : "이전" 버튼 눌렀을때의 이벤트, step 의 값이 3210 반복됨
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

@Composable
fun PosterArea( // MEMO : 이미지영역에 대한 Composable 함수
    modifier: Modifier,
    image: Int
) {
    Surface( // MEMO : Surface로 Image를 감싸 여러 스타일을 줄 수 있는데, 여기서는 액자모양을 구현하기 위해 Surface로 감쌈
        modifier = modifier
            .fillMaxSize() // MEMO : 부모요소의 영역에서(여기서는 Column에서 70퍼센트를 차지하는 영역) 가득 차게 채움
            .wrapContentWidth(Alignment.CenterHorizontally) // MEMO : Width를 요소의 크기만큼(wrap_content)지정하고, 가운데 정렬함
            .border( // MEMO : 하얀색의 테두리를 만듬. 테두리의 굵기는 40dp 임.
                border = BorderStroke(40.dp, Color.White),
            )
            .shadow(8.dp) // MEMO : Surface의 그림자를 지정함.
    ){
        Image(
            painter = painterResource(id = image),
            contentScale = ContentScale.FillHeight, // MEMO : 이미지의 크기 조절. 세로로 꽉 차게끔 지정함.
            contentDescription = null,
            modifier = Modifier // MEMO : 이미지의 padding 지정. 굳이 넣어준 이유는, surface의 border 영역만큼 이미지가 잘리기 때문에, border 영역만큼 떨어뜨려서 이미지를 그리기 위함
                .padding(40.dp)
        )
    }
}
@Composable
fun InfoArea(
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
            style = MaterialTheme.typography.bodyLarge // MEMO : MaterialTheme.typography 의 글자스타일 중, 원하는 것을 사용함. ui/Type.kt 에서 오버라이딩 해서 재구성 가능
        )
        Text(
            text = buildAnnotatedString { // MEMO : 1개의 Text에 원하는 글자에만 스타일을 적용할 수 있음. 사용법은 아래와 같음
                withStyle( // MEMO : 적용할 스타일을 지정함
                    SpanStyle(fontWeight = FontWeight.Bold)
                ){
                    append(stringResource(id = info.artist)) // MEMO : 위에서 지정한 스타일을 적용할 글자를 append 함.
                }
                append(stringResource(id = info.year)) // MEMO : withStyle구문 바깥에서 원래 스타일대로 지정될 Text를 Append 함
            },
            style = MaterialTheme.typography.bodyMedium // MEMO : 전체(InfoArea의) 글자 스타일 지정
        )
    }
}

@Composable
fun ControllerArea(
    modifier: Modifier = Modifier,
    onClickPrev: () -> Unit, // MEMO : 상태 호이스팅 - onClick 시 수행할 동작을 매개변수로 받아옴
    onClickNext: ()-> Unit // MEMO : 상태 호이스팅 - onClick 시 수행할 동작을 매개변수로 받아옴
){
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround, // MEMO : horizontal 배치 지정. spaceAround - 자식 컴포넌트들의 간격을 동일하게 맞춰준다.
        verticalAlignment = Alignment.Bottom
    ){
        Button(
            onClick = onClickPrev,
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
        ){
            Text(
                text = stringResource(id = R.string.next),
                modifier = Modifier
                    .padding(start = 30.dp, end = 30.dp)
            )
        }
    }
}

@Preview( // MEMO : 미리보기 - 라이트모드
    showBackground = true,
    name = "Light_Mode"
)
@Preview( // MEMO : 미리보기 - 다크모드
    showBackground = true,
    name = "Dark_Mode",
    uiMode = Configuration.UI_MODE_NIGHT_YES // MEMO : 다크모드로 볼 수 있게 uiMode 지정
)
@Composable
fun GreetingPreview() {
    utility = Utility()
    ArtSpaceTheme {
        ArtSpaceLayout()
    }
}