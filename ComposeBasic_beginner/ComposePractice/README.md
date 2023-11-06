# Compose 사용시 알아야 하는 Android 기본 사항 ( 3 )

## 연습 : Compose 기본 사항

(3번째 문제였던 Compose 사분면 문제를 다룬다)
<br><br>
우선, 해당 연습문제를 풀며 느낀건 Composable 함수에 인자를 전달하여 해당 인자를 출력하게 하는 방식을 권장하고 있으며, 레이아웃에 대해 잘 파악하여 어떤 Composable 함수를 작성하는지가 매우 중요하다는 사실을 깨달았다.
<br><br>

사분면 문제에서 제시된 조건은 화면을 4개의 사분면으로 나눠 각각의 부분 색상과 텍스트를 다르게 나타내야 하는 것이다.<br>
필요한 text들은 string.xml 에 지정하여 아이디값으로 불러오게끔 하였다.<br><br>

우선 한개의 사분면을 구성한다. 화면 전체가 특정 색으로 칠해지며, 텍스트는 양쪽 정렬로 되어있다.

```
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
```
매개변수로 필요한 텍스트의 제목, 본문, 해당 사분면의 색상을 받는다.<br>
수정자 또한 매개변수로써 기본 매개변수로 `Modifier` 로 초기화 되어있는데, 모든 Composable 함수는 선택적 Modifier 매개변수를 허용해야 하기 때문이다.<br>
`Column` 내부를 보면 매개변수로 넘어온 색상으로 배경색을 지정해주고, 화면 전체를 차지하게끔 `fillMaxSize()`를 해준다.<br>
텍스트 제목과 본문은 화면 중앙에 위치해 있으므로 `horizontalAlignment = Alignment.CenterHorizontally` , `verticalArrangement = Arrangement.Center` 를 지정해주어 화면 중앙에 하위 요소들이 배치되게끔 지정해준다.<br>
본문 텍스트가 양쪽 정렬로 되어있었으므로 `TextAlign.Justifty` 해주었다.<br>
<br>
이제 한개의 사분면을 만들 수 있는 Composable 함수를 4번 호출하여 사분면 형태를 완성해주어야 한다.<br>
다음은 4사분면을 그리는 Composable 코드이다.
```
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
```
Column 컴포저블은 화면 가로 전체를 차지하게끔 Modifier.fillMaxWidth() 지정해주고, 사분면의 모양을 하기 위해 한줄에 2개씩(가로로 2개 배치) 배치하기 위해 Row를 2번 써주었다.<br>
이때, Row의 인자로 Modifier.weight(1f) 가 들어간다.<br><br>
고정 높이를 지정하지 않고도 가중치로 각 자식요소가 차지하는 공간을 제어하는데 Weight를 사용한다.<br>
예를들어 컨테이너 안에 3개의 Composable이 있다고 하자. 2개의 weight는 3이고, 1개의 weight가 2일때 weight의 총합은 8이 된다.<br>
사용 가능한 컨테이너에서 height를 8로 나누고 해당 가중치에 따라 Composable이 차지하게 된다.
<br><br>
여기서 컨테이너란, jetpack compose에서 다른 구성요소를 보관하고 배열하는데 사용되는 레이아웃 구성요소이다. 컨테이너는 레이아웃 내에서 하위 구성요소의 위치 및 크기를 제어하는 역할을 한다.<br>
우리가 알고있는 Column, Row, Box 등이 컨테이너이다. Composable은 @Composable 함수를 말하는 것이 아닌 Text, Image와 같은 UI요소를 의미한다.
