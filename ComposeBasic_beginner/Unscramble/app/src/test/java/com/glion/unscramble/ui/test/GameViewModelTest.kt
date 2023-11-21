package com.glion.unscramble.ui.test

import com.glion.unscramble.data.MAX_NO_OF_WORDS
import com.glion.unscramble.data.SCORE_INCREASE
import com.glion.unscramble.data.getUnscrambledWord
import com.glion.unscramble.ui.GameViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Assert.assertNotEquals
import org.junit.Test

class GameViewModelTest {
    private val viewModel = GameViewModel() // MEMO : viewModel을 한번만 초기화되어도 각 테스트마다 개별적으로 실행되게 된다. 기본적으로 JUnit4 은 각 테스트 메서드 실행 전 테스트 클래스의 새로운 인스턴스를 만든다.

    // MEMO : 성공 경로의 단위 테스트 작성
    @Test
    fun gameViewModel_CorrectWordGuessed_ScoreUpdatedAndErrorFlagUnset(){
        var currentGameUiState = viewModel.uiState.value
        val correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambleWord)

        viewModel.updateUserGuess(correctPlayerWord)
        viewModel.checkUserGuess()

        currentGameUiState = viewModel.uiState.value // 위의 작업을 끝낸 뒤의 상태를 다시 가져옴
        assertFalse(currentGameUiState.isGuessedWordWrong) // 추측한 단어가 틀렸는지를 저장하는 isGuessWordWrong이 false(정답을 맞춘 상태인지) 확인
        assertEquals(SCORE_AFTER_FIRST_CORRECT_ANSWER, currentGameUiState.score) // 정답을 맞췄다면 점수가 20이 올랐을테니, 점수가 20인지 확인함
    }

    companion object{
        private const val SCORE_AFTER_FIRST_CORRECT_ANSWER = SCORE_INCREASE
    }

    // MEMO : 실패 경로의 단위 테스트 작성
    @Test
    fun gameViewModel_IncorrectGuess_ErrorFlagSet(){
        val incorrectPlayerWord = "and" // 임의의 틀린값 지정

        viewModel.updateUserGuess(incorrectPlayerWord) // MEMO : 틀린값을 썻다고 가정했을떄
        viewModel.checkUserGuess() // MEMO : 작성한 값과 원래 정답 비교

        val currentGameUiState = viewModel.uiState.value // 위의 작업을 끝낸 상태 가져옴

        assertTrue(currentGameUiState.isGuessedWordWrong) // 틀린 답을 썻기 때문에 isGuessWordWrong이 true로 될 것을 예측하고 테스트
        assertEquals(0, currentGameUiState.score) // 틀린 답을 썼기 때문에 점수가 오르지 않았을 것으로 예측하고, 점수가 0인지 확인
    }

    // MEMO : 경계 상태의 단위 테스트 작성 - 초기 상태 테스트
    @Test
    fun gameViewModel_Initialization_FirstWordLoad(){
        val gameUiState = viewModel.uiState.value
        val unScrambledWord = getUnscrambledWord(gameUiState.currentScrambleWord) // 초기 상태의 currentScrambleWord는 viewModel의 init 으로 인해 한가지 단어가 섞여있는 상태로 초기화가 되어있을 것이다. 따라서 그 단어의 원형을 가져온다.

        assertNotEquals(unScrambledWord, gameUiState.currentScrambleWord) // 원래 단어와 섞인 단어가 다른지(잘 섞여 있는지) 확인한다
        assertTrue(gameUiState.currentWordCount ==1)
        assertTrue(gameUiState.score == 0)
        assertFalse(gameUiState.isGuessedWordWrong)
        assertFalse(gameUiState.isGameOver)
    }

    // MEMO : 경계 상태의 단위 테스트 작성 - 모든 문제를 풀었을 때
    @Test
    fun gameViewModel_AllWordsGuessed_UiStateUpdatedCorrectly(){
        var expectedScore = 0
        var currentGameUiState = viewModel.uiState.value
        var correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambleWord)

        repeat(MAX_NO_OF_WORDS){
            expectedScore += SCORE_INCREASE
            viewModel.updateUserGuess(correctPlayerWord) // 맞는 단어를 제출했을때
            viewModel.checkUserGuess() // 유저의 추측이 맞는지 확인
            currentGameUiState = viewModel.uiState.value
            correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambleWord)
            assertEquals(expectedScore, currentGameUiState.score)
        }
        assertEquals(MAX_NO_OF_WORDS, currentGameUiState.currentWordCount)
        assertTrue(currentGameUiState.isGameOver)
    }

    // MEMO : GameViewModel에서 테스트 하지 않은 함수에 대해 작성 - 단어를 건너 뛰었을때
    @Test
    fun gameViewModel_WordSkipped_ScoreUnChangedAndWordCountIncreased(){
        var currentGameUiState = viewModel.uiState.value
        val correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambleWord)

        viewModel.updateUserGuess(correctPlayerWord)
        viewModel.checkUserGuess()

        currentGameUiState = viewModel.uiState.value
        val lastWordCount = currentGameUiState.currentWordCount
        viewModel.skipWord()
        currentGameUiState = viewModel.uiState.value
        assertEquals(SCORE_AFTER_FIRST_CORRECT_ANSWER, currentGameUiState.score)
        assertEquals(lastWordCount + 1, currentGameUiState.currentWordCount)
    }
}