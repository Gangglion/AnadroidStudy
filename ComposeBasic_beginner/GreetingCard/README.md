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