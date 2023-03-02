# AnadroidPatternStudy
안드로이드 패턴 공부(MVVM, MVC, MVP)

## 목적
안드로이드 애플리케이션 제작시 요구되는 아키텍처 패턴에 대해 공부하여 올리는 리포지토리

### 패턴 사용 이전
- 아래 코드는 필자가 전공3학년때 진행한 졸업작품 "Sleeper" 애플리케이션 코드의 일부이다.
```  
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_2_maintimertab);
        //(...중략...)
        mAuth = FirebaseAuth.getInstance(); //연결된 계정 불러오기
        timelayout = (LinearLayout)findViewById(R.id.timelayout); //타이머있는 화면
        bottom_menu = (LinearLayout)findViewById(R.id.bottom_menu); //하단 메뉴 레이아웃
        main = (Button) findViewById(R.id.main); //메인기능버튼
        music = (Button) findViewById(R.id.music); //음악기능 버튼
        statistics = (Button) findViewById(R.id.statistics); //통계기능 버튼
        Settings = (Button) findViewById(R.id.Settings); //설정기능 버튼
        setBtn = (Button) findViewById(R.id.setBtn); //설정완료 버튼
        plusBtn = (Button)findViewById(R.id.plusBtn); //리스트 추가 버튼
        lockclear = (Button)findViewById(R.id.lockclear);
        //(...중략...)
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), UI_List.class);

                startActivity(intent);
            }
        });
        mediaVol = (AudioManager)getSystemService(Context.AUDIO_SERVICE); //기능 시작과 동시에 볼륨 줄이기 위한 선언
        this.context = this;
```
- onCreate 부분을 보면 layout에 정의된 많은 뷰를 가져오는 코드와 버튼을 클릭했을때의 이벤트처리 등 여러가지가 한군데 모여있어 난잡한 모습을 볼 수 있다.
- 이런 식으로 구현하면 규모가 커질 수록 하나의 액티비티가 복잡하고 비대해져 여러 이슈가 생길 수 있고, 이슈 대응 또한 어려울 것이다.
- 디자인패턴을 사용하면 비즈니스 로직과 화면을 구성하는 로직을 UI로부터 분리할 수 있고, 테스트, 유지보수, 재사용성이 쉬워진다.

### MVVM 패턴 - MVVMActivity 참고(바인딩 적용 이전과 후로 나누어짐)
- 구성요소
    - View(Activity) : 사용자에게 제공되는 UI, 사용자의 입력을 받고, 이벤트를 특정 ViewModel로 전달한다.
    - ViewModel : View가 사용할 메서드와 필드를 구현하고, 전달된 이벤트를 처리하여 View에 변경사항을 알린다.
    - Model : 앱에서 사용할 데이터에 관련된 로직과 데이터관련 로직이 위치한다.
- 흐름 순서
  - ![MVVM 순서](https://img1.daumcdn.net/thumb/R1280x0/?scode=mtistory2&fname=https%3A%2F%2Fblog.kakaocdn.net%2Fdn%2FHQ9tV%2Fbtq4ZRjEXH3%2FSxMfDOnQqSvCkUVenAnkLK%2Fimg.png)

- 직접 작성해보고 느낀 점(바인딩 적용 안함)
  - 패턴 적용 후 Activity의 코드가 짧아짐을 느꼈다. 그에 따라 Activity에서 연결된 ViewModel을 보고 필요한 기능이 있을 때 추가한다거나, 수정이 용이할 것 같다.
  - 다만 ViewModel의 설계에 대해 신경쓰지 않으면 그 또한 코드가 난잡해지는 원인이 될 것이라고 생각된다.

- 데이터바인딩 이란?
  - 두 데이터 혹은 정보의 소스를 모두 일치시키는 기법이다. 안드로이드에서는 layout을 구성하는 xml에 Data를 연결하는 작업을 의미한다.
  - findViewById를 사용하지 않아도 되는 장점이 있고, 주로 MVVM 패턴과 함께 사용된다.
  - TextView에 값을 바꾸는 것으로 예를 들면, 본인이 기존 사용하던 방식은 `onCreate()` 에서 findViewById를 사용하여 id값을 가져와 `setText()`를 사용하였다
  - 데이터 바인딩을 사용 할 경우 `android:text = "@{bindingViewModel.data}"` 처럼 사용하여 ViewModel의 값을 직접 넣어 줄 수 있다.
  
- 직접 작성해보고 느낀 점(바인딩 적용)
  - 데이터바인딩과 라이브데이터를 적용하여 같은 기능을 하게끔 만들었다. 데이터가 변경되면 View에서 데이터변경을 감지하고 적용한다.
  - 버튼 클릭 이벤트를 구현하는 방식에는 2가지가 있었다. 첫번째는 View에서 `setOnClickListener` 로 클릭 이벤트를 받고, 처리를 ViewModel에 넘겨주는 방식이고
    두번째는 xml에서 바인딩된 함수를 `android:onclick` 속성을 통해 `android:onClick="@{()->viewmodel.initView()}`와 같이 클릭시 처리를 viewmodel에
    넘겨주게끔 할 수 있다. ViewModel이 처리한다는 점은 동일하지만 이벤트를 받는 부분이 다르다. 두번째 방법을 사용하게 되면 View의 코드가 간결해지지만 그만큼 ViewModel
    의 코드가 커질 수 있을것이다.
  - 구조에 익숙해지고, 규모가 큰 프로젝트에서 적용하면 부분별로 나누어져있어 향후 유지보수에 있어 큰 도움이 될 것이라고 느껴진다.