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
- 비즈니스 로직과 화면을 구성하는 로직을 UI로부터 분리하는 디자인패턴을 사용하여 테스트, 유지보수, 재사용성이 쉬워진다.

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
  - 버튼 클릭이벤트를 예로들면, 패턴 적용 이전에는 `setOnClickListener`를 통해 이벤트 처리로 구현되었다.
    패턴 적용 이후에는 ViewModel에서 `setOnClickListener` 를 통해 이벤트 처리로 구현한다.
    패턴에 데이터바인딩을 적용하게 되면 xml코드에 `android:onClick="@{ViewModel.실행할함수명()}"` 형태가 된다.
  
- 직접 작성해보고 느낀 점(바인딩 적용)
  - 