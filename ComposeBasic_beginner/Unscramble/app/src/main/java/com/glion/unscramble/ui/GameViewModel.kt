package com.glion.unscramble.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.glion.unscramble.data.MAX_NO_OF_WORDS
import com.glion.unscramble.data.SCORE_INCREASE
import com.glion.unscramble.data.allWords
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class GameViewModel : ViewModel() {
    // Game UI State
    private val _uiState = MutableStateFlow(GameUiState()) // 구성가능한 함수(Composable) 이 UI 상태 업데이트를 확인하고, 구성변경 시에도 화면 상태가 지속되도록 노출시킴
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow() // asStateFlow() 는 이 변경 가능 상태 흐름을 읽기 전용 상태 흐름으로 만들어 줌. 외부에서 uiState로 접근할때, 읽기전용으로 수정불가능하게 할 수 있음.
    // _uiState는 여기(viewModel)서만 변경할 수 있음

    // 게임에 사용된 단어를 저장하는 변경 가능한 Set
    private var usedWords: MutableSet<String> = mutableSetOf()
    private lateinit var currentWord: String

    var userGuess by mutableStateOf("") // MEMO : 상태를 나타내는 변수. 변경시 리컴포지션이 일어나는 원인
        private set

    init{
        resetGame()
    }

    private fun pickRandomWordAndShuffle(): String{
        currentWord = allWords.random()
        if(usedWords.contains(currentWord)){
            return pickRandomWordAndShuffle()
        } else{
            usedWords.add(currentWord)
            return shuffleCurrentWord(currentWord)
        }
    }

    private fun shuffleCurrentWord(word: String): String{
        val tempWord = word.toCharArray()
        tempWord.shuffle()
        while(String(tempWord) == word){
            tempWord.shuffle()
        }
        return String(tempWord)
    }

    fun resetGame(){
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambleWord = pickRandomWordAndShuffle()) // MEMO : GameUiState에 정의된 데이터에서 currentScrambleWord 값을 pickRandomWordAndShuffle() 값으로 저장한다.
        // MEMO : uiState에 저장된 값들은 UI에서 관찰하여 UI를 그리게 된다. 위에서 _uiState를 GamUiState 타입의 상태로 정의하였다.
    }

    fun updateUserGuess(guessedWord: String){
        userGuess = guessedWord
    }

    fun checkUserGuess(){
        if(userGuess.equals(currentWord, ignoreCase = true)){
            val updatedScore = _uiState.value.score.plus(SCORE_INCREASE)
            updateGameState(updatedScore = updatedScore)
        } else{
            _uiState.update {currentState ->
                currentState.copy(isGuessedWordWrong = true)
            }
        }
        updateUserGuess("")
    }

    private fun updateGameState(updatedScore: Int){
        if(usedWords.size == MAX_NO_OF_WORDS){ // 마지막
            _uiState.update{currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    score = updatedScore,
                    isGameOver = true
                )
            }
        } else{
            _uiState.update{ currentState ->
                currentState.copy(
                    isGuessedWordWrong = false,
                    currentScrambleWord = pickRandomWordAndShuffle(),
                    score = updatedScore,
                    currentWordCount = currentState.currentWordCount.inc(),
                    isGameOver = false
                )
            }
        }
    }

    fun skipWord(){
        updateGameState(_uiState.value.score)

        updateUserGuess("")
    }
}