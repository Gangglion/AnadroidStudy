package com.glion.unscramble.ui

data class GameUiState( // UI State 의 데이터 클래스(모델)
    val currentScrambleWord: String = "",
    val isGuessedWordWrong: Boolean = false,
    val score: Int = 0,
    val currentWordCount: Int = 1,
    val isGameOver: Boolean = false
)