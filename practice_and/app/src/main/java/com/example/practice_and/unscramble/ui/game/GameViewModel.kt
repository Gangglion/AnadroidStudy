package com.example.practice_and.unscramble.ui.game

import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
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
    private var _score = 0
    val score : Int
        get() = _score

    private var _currentWordCount = 0
    val currentWordCount: Int
        get() = _currentWordCount

    private lateinit var _currentScrambledWord: String
    val currentScrambledWord: String
        get() = _currentScrambledWord

    private var wordsList: MutableList<String> = mutableListOf() // viewModel에서 게임에 사용되는 단어 목록 보유
    private lateinit var currentWord: String // 플레이어가 맞춰야 되는 단어 보유할 변수

    private fun getNextWord(){
        currentWord = allWordsList.random()
        val tmpWord = currentWord.toCharArray()
        tmpWord.shuffle()

        while(String(tmpWord).equals(currentWord, false)){ // ignorecase false : 비교하는 두 대상이 같으면 true를 반환하는데, 대소문자 구분하지 않는다.
            tmpWord.shuffle()
        }
        if(wordsList.contains(currentWord)){
            getNextWord()
        } else{
            _currentScrambledWord = String(tmpWord)
            ++_currentWordCount
            wordsList.add(currentWord)
        }
    }

    fun nextWord(): Boolean{
        return if(currentWordCount < MAX_NO_OF_WORDS){ // 한 게임당 10개의 단어가 제시된다. 게임이 진행되는 동안 10개의 단어가 제시되었는지를 체크하는 부분이다.
            getNextWord()
            true
        } else false
    }

    private fun increaseScore(){
        _score += SCORE_INCREASE
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
        _score = 0
        _currentWordCount = 0
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