# Compose 사용시 알아야 하는 Android 기본 사항 ( 1 )

## Jetpack Compose 란?
네이티브 Android UI를 빌드하기 위한 최신 도구 키트이다.

기존 안드로이드 앱 개발시, UI부분은 xml로 View를 그리고, 코드 상에서 setContentView 나 inflate 메소드를 이용하여 필요한 View 를 로드해야 했다.

Jetpack Compose 를 이용하면 이전과 달리 코드상에서 UI에 대한 모든 관리를 하게 된다.

초기 xml을 사용하는 Android 개발 방식을 접했다가 Flutter 를 잠시 공부해본 경험으로는, 이 Compose 방식이 Flutter 와 유사하다고 느꼈고, 굉장히 편리하고 강력한 기능이라는 느낌을 받았다.

Compose를 이용하여 프로젝트를 생성하는 방법은 New Project에서 Empty Compose Activity를 선택하여 프로젝트를 생성한다.

## 구성 요소

기본 제공하는 코드를 확인해보자.
~~~
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GreetingCardTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Surface(color = Color.Yellow) {
        Text(text = "Hi, My name is $name!", modifier = Modifier.padding(24.dp))
    }
}
~~~

- `onCreate()` 함수는 동일하다. 이 프로그램의 진입점이다.

- `setContent()` 함수는 Composable 함수를 통해 레이아웃을 정의하는데 사용된다. `@Composable` 주석으로 표시된 모든 함수는 `setContent()` 함수 또는 다른 `@Composable` 함수에서 호출이 가능하다.

- `@Composable` 어노테이션이 붙은 함수는 구성가능한 함수이다. 화면에 표시되는 내용을 생성한다. 이름은 대문자로 표기하는 파스칼 표기법을 사용하며, 아무것도 반환 할 수 없다. 또한 명사로 명명해야 한다.

- `Greeting` 함수에 Text를 Surface로 감쌈으로서, 배경색을 변경할 수 있다. 배경색이 인자로 들어간다.

- `Modifier`(수정자)는 Composable을 강화하거나 장식하는데 사용한다. 상하좌우 위치선정, 정렬, 패딩 등등 여러 역할이 존재한다. `modifier = Modifier.padding(24.dp)` 의 경우 Text 에 상하좌우 24dp 만큼의 패딩을 준다는 의미이다.

<br><br><br>
Compose에는 미리보기 기능이 존재한다. 다음 코드는 전체 앱을 빌드하지 않고 앱이 어떻게 표시되는지 확인 할 수 있는 기능이다.
```
@Preview(showBackground = true) // showBackground 매개변수가 true로 설정되면 앱 미리보기에 배경이 추가됨.
                                // 흰색 배경이 true로 설정됬을때임. false는 검은색 배경이 됨.
@Composable
fun DefaultPreview() {
    GreetingCardTheme {
        Greeting("Glion")
    }
}
```
- `@Preview` 어노테이션을 추가한 함수는 미리보기 함수가 된다.

- showBackground 매개변수가 true 로 설정되면 앱 미리보기에 배경이 추가된다.

- 미리보기 위해서는 Composable 함수를 호출하여 UI를 그려야 하므로 @Composable 어노테이션을 추가한 모습을 확인할 수 있으며, `onCreate()` 함수와 마찬가지로 `GreetingCardTheme` 내부에 `Greeting()`이라는 Composable함수를 호출하는 모습을 볼 수 있다.

# Compose 사용시 알아야 하는 Android 기본 사항 ( 2 )

## Jetpack Compose 에 대해 다시 짚고 넘어가자
Jetpack Compose 는 Android에서 UI를 빌드하기 위해 사용하는 최신 툴킷이다. 적은 양의 코드, 강력한 도구 및 직관적은 Kotlin의 기능으로 UI 개발을 간소화하고, 가속화 시킨다.
<br>
<br>
Compose를 사용하면 데이터를 받아서 UI요소를 내보내는 Composable 함수 집합을 정의하여 UI를 빌드 할 수 있다.
<br>
다음과 같은 특징이 있다.
1. UI 의 일부를 설명한다.
2. 아무것도 반환하지 않는다
3. 몇개의 입력을 받아서 화면에 표시되는 내용을 생성한다.
4. 여러 UI요소를 내보낼 수 있다.

## UI 계층 구조
공식 튜토리얼에는 다음과 같이 나와 있다.
```
UI 계층구조는 포함에 기반합니다다. 즉, 한 구성 요소에 하나 이상의 구성 요소를 포함할 수 있으며, 상위 요소 및 하위 요소라는 용어가 사용되는 경우도 있습니다. 
여기에서 컨텍스트는 상위 UI 요소가 하위 UI 요소를 포함하는 것이며 이 하위 UI 요소는 하위 UI 요소를 차례로 포함할 수 있습니다. 이 섹션에서는 상위 UI 요소 역할을 할 수 있는 Column, Row, Box 컴포저블에 관해 알아봅니다.
```
간단히 얘기하면 최상위 구성요소가 있고, 그 내부에 하위 요소, 하위 요소를 층층히 쌓아 내려가며 UI를 구성한다는 의미이다. 그중에 상위 요소 역할을 할 수 있는 것이 `Column`, `Row`, `Box` 가 있다.
<br><br>
`Column` Composable은 UI를 세로로 쌓는다.<br>
`Row` Composable은 UI를 가로로 쌓는다.<br>
`Box` Composable은 UI가 위로 쌓이는 모양이다.<br>

## 안드로이드 해상도
공식 튜토리얼에서는 이미지를 drawable 폴더에 넣어 사용하고자 할때 `Resource Manager` 를 이용하여 'Density'를 'no density'로 하고 추가하는 과정을 거쳤다. 왜 그런 것일까?<br>

우선 안드로이드 해상도에 대해 알아야 한다.<br>
* DP : 안드로이드에서 사용하는 독립적 단위 수치이다. 어떤 해상도에서도 같은 크기를 보여주는 것이 목적이다.
* DPI : 1인치에 들어있는 픽셀의 수이다. 안드로이드에서는 160을 기본으로 한다.
* px : 스크린의 실제 픽셀 단위를 사용한다. 실제 크기나 밀도와 상관이 없다.

px = dp * (dpi / 160)<br>
160 dpi인 해상도에서 1dp 는 1px라는 뜻이다. 160dpi를 mdpi라고 한다.

구글은 해상도 및 화면 크기가 다른 디바이스들을 범용으로 지원하기 위해 밀도(Density)와 함꼐 화면 크기로 분류하여 기준을 제시하고 있다. 해상도별로 다른 APK를 배포할 수 없기 때문에 정해진 기준에 따라 리소스를 제공해 줄 필요가 있다. <br>이러한 부분을 무시하게 되면 시스템이 자동으로 이미지를 확대하여 흐릿한 이미지가 나오거나, 메모리를 과하게 사용하는 큰 이미지를 얻게 되고, 메모리 부족 오류가 발생할 수 있는 원인이 된다.
<br>
<br>
원래 주제로 넘어와서 No Density로 지정한 것은 예제 프로젝트 이기 때문에 밀도를 정하지 않고 원본 그대로를 사용하겠다는 의미이다.<br>
실제 프로젝트에 적용할 때는 대표적인 밀도는 지원하게끔 설정해 주는 것이 필요 할 것 같다.

## HappyBirthday 실습 프로젝트에서 주목해야 할 코드
1. 이미지 크기를 조절하는 방법 - 이미지 배율 유형 조절
    ```
    Image(
        painter = image, 
        contentDescription = null, 
        contentScale = ContentScale.Crop
    )
    ```
   이미지의 인자로 contentScale가 보인다. 가지는 속성은 다음과 같다.
    * Crop : 이미지의 너비 및 높이가 배치될 대상의 치수보다 크거나 같도록 이미지를 균일하게 조정한다.
    * FillBounds : 배치될 대상 범위를 채우기 위해 수평 및 수직으로 불균일하게 크기를 조정한다.
    * FillHeight : 배치될 대상의 너비와 맞도록 종횡비를 유지하며 크기를 조절한다.
    * Fit : 이미지의 두 치수가 배치될 대상의 치수와 같거나 작도록 균일하게 조정한다. 이때 종횡비는 유지한다
    * Inside : 이미지 소스가 배치될 대상보다 큰 경우 그 경계 내에 있게 종횡비를 유지하며 크기를 조절한다.
    * None : 이미지 소스에 배율을 적용하지 않는다.
      <br>
      <br>
      추가로 `contentDescription` 은 시각장애인들을 위한 `Talkback` 옵션으로써, 사용하지 않을 경우 `null` 을 주면 된다.

2. 레이아웃에 `Modifier` 을 적용하여 `Arrangement`, `alignment` 속성을 통해 하위 요소 배치 하는 방법
    ```
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(text = message, fontSize = 36.sp, modifier = Modifier.padding(top = 16.dp))
        Text(text = from, fontSize = 24.sp, modifier = Modifier
        .padding(top = 16.dp, end = 16.dp)
        .align(alignment = Alignment.End))
    }

    ```
    * Row : horizontalArrangement / verticalAlignment 사용하여 하위 요소의 위치 지정
    * Column : verticalArrangement / horizontalAlignment 사용하여 하위 요소의 위치 지정
      <br><br>
      arrangement 속성은 레이아웃 크기가 그 하위 요소의 합보다 큰 경우 하위 요소를 정렬하는 데 사용한다(세로정렬)<br>
      alignment 속성은 레이아웃의 시작, 가운데, 또는 끝에 하위 요소를 정렬하는데 사용한다(가로정렬)
      <br><br>
      해당 코드에서는 `Column` 전체에 대해 수평으로 사용가능한 공간에서 수직기준 상단 정렬을 하였다.<br>
      또한, `Text` 에 top과 end에 16.dp만큼 패딩을 주었고, `Alignment.End`를 하여 text를 가로 끝에 붙이도록 정렬해준 모습이다.

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

# Compose 사용시 알아야 하는 Android 기본 사항 ( 4 )

## 단원 2 정리