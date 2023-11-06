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