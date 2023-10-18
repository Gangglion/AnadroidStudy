package com.example.practice_and.unscramble.ui.game

import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.practice_and.App
import com.google.android.material.color.utilities.Score
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.lang.reflect.Array.get

class GameViewModel : ViewModel() {
    // 이 속성들은 ViewModel에서만 접근 가능하다. UI Controller(Activity, Fragment)에서는 접근이 불가능해야 한다.
    // Public으로 변경 할 수 없다. 외부 클래스가 이 ViewModel에 접근하여 예기치 않는 방식으로 데이터를 변경 할 수 있기 때문이다.
    // ViewModel 내에서 데이터가 수정이 가능해야 하므로 var로 변수를 선언했다. 그러나, ViewModel 외부에서 이 데이터를 변경해서는 안되므로, 외부에 노출 시에는 private val 로 노출되어야 한다.
    // 지원 속성을 사용하여 _score 객체가 아닌 _score 을 반환하는 val 로 선언된 score를 선언하여 get() = _score 해준다.
    // get() set() 메서드 중 get() 메서드만 재정의 하여 반환하므로, 변경 불가능하며 읽기전용으로 외부에서 해당 변수를 읽기전용으로 접근할 수 있게 한다.
    // 정리하면, _scroe 클래스는 viewModel에서만 수정이 가능하고, 외부 클래스에서 접근이 가능하지만 이들로 인해 변경되지 않도록 보호된다.
//    private var _score = 0
//    val score : Int
//        get() = _score

    // score 변수에 대해 LiveData 로 래핑
    private val _score = MutableLiveData(0) // MutableLiveData의 데이터 타입을 Int형, 0으로 초기화
    val score: LiveData<Int> get() = _score

//    private var _currentWordCount = 0
//    val currentWordCount: Int
//        get() = _currentWordCount

    // _currentWordCount에 대해 LiveData 로 래핑
    private val _currentWordCount = MutableLiveData(0)
    val currentWordCount: LiveData<Int> get() = _currentWordCount

    // 기존 String 형 변수를 내부에 저장된 데이터의 값을 변경할 수 있는 라이브데이터인 MutableLiveData 로 변경
//    private lateinit var _currentScrambledWord: String
    private val _currentScrambledWord = MutableLiveData<String>() // val 로 하는 이유는 MutableLiveData의 객체 자체는 변하지 않고, 내부에 저장된 데이터만 변경되기 때문

    // 기존 getter 제거 후 라이브데이터 getter로 변경
//    val currentScrambledWord: String get() = _currentScrambledWord
    val currentScrambledWord: LiveData<String> get() = _currentScrambledWord

    private var wordsList: MutableList<String> = mutableListOf() // viewModel에서 게임에 사용하는 단어 목록 보유
    private lateinit var currentWord: String // 플레이어가 맞춰야 되는 단어 보유할 변수

    private fun getNextWord(){
        currentWord = allWordsList.random()
        val tmpWord = currentWord.toCharArray()
        tmpWord.shuffle()

        while(String(tmpWord).equals(currentWord, false)){ // ignorecase false : 비교하는 두 대상이 같으면 true를 반환하는데, 대소문자 구분하지 않는다.
            tmpWord.shuffle() // 단어의 배열이 다르도록 반복하여 셔플. 가져온 단어와 섞은 단어가 같지 않을때까지 반복함
        }
        if(wordsList.contains(currentWord)){ // 현재 단어가 이미 사용되었던 단어라면, 이 함수를 다시 호출해서 단어를 다시 가져온다.
            getNextWord()
        } else{
//            _currentScrambledWord = String(tmpWord)
            _currentScrambledWord.value = String(tmpWord) // LiveData 객체 내의 데이터에 액세스 하기 위해 value 속성 사용함

//            ++_currentWordCount
            // increaseScore의 상황과 마찬가지로, 1 증가 함수인 inc()를 사용하여 처리해주어야 한다.
            _currentWordCount.value = (_currentWordCount.value)?.inc()
            wordsList.add(currentWord)
        }
    }

    fun nextWord(): Boolean{
        // LiveData 사용함에 따라 다음과 같이 변경하였다
        return if(/*currentWordCount < MAX_NO_OF_WORDS*/ currentWordCount.value!! < MAX_NO_OF_WORDS){ // 한 게임당 10개의 단어가 제시된다. 게임이 진행되는 동안 10개의 단어가 제시되었는지를 체크하는 부분이다.
            getNextWord()
            true
        } else false
    }

    private fun increaseScore(){
//        _score += SCORE_INCREASE

        // _score는 LiveData를 사용함에 따라 더이상 Int가 아닌, LiveData<Int> 타입이다. 따라서 plus() 를 사용하여 덧셈처리를 해주어야 한다
        _score.value = (_score.value)?.plus(SCORE_INCREASE) // _score.value 가 null일 수 있으니 null-safety 처리해준다
    }

    fun isUserWordCorrect(playerWord: String): Boolean {
        return if (currentWord.equals(playerWord, true)) { // 대소문자 구분하여 같은지 체크
            increaseScore()
            true
        } else {
            false
        }
    }

    fun reinitializeData(){
//        _score = 0
//        _currentWordCount = 0

        // 라이브데이터 사용함에 따라 다음과 같이 변경
        _score.value = 0
        _currentWordCount.value = 0
        wordsList.clear()
        getNextWord()
    }



    // ========================================== ViewModel 생명주기 관찰 ========================================= //
    init{
        Log.d(App.TAG, "GameViewModel Created")
        getNextWord()
    }

    // ViewModel 은 fragment가 처음 생성됬을때 생성된다. 이후 화면전환 같은걸로 fragment가 destroy, create를 반복할 때, viewModel은 소멸되지 않고 유지된다.
    // fragment가 완전히 분리(detach) 될 때 비로소 viewModel이 소멸된다.(지속적으로 유지됨을 알 수 있다.)
    override fun onCleared() { // ViewModel은 연결된 프래그먼트가 분리되거나, 활동이 완료되면 소멸한다. ViewModel이 소멸되기 직전 onCleared 콜백이 호출된다.
        super.onCleared()
        Log.d(App.TAG, "GameViewModel Destroyed!")
    }
    // ========================================== ViewModel 생명주기 관찰 ========================================= //
}