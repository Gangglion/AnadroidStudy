package com.glion.racetracker.ui

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay

/**
 * This class represents a state holder for race participant.
 */
class RaceParticipant(
    val name: String,
    val maxProgress: Int = 100,
    val progressDelayMillis: Long = 100L,
    private val progressIncrement: Int = 1,
    private val initialProgress: Int = 0
) {
    init {
        // MEMO : 매개변수의 값이 참인지 체크한다 - 생성자에서 실행. 참이 아니면 illegalArgumentException 발생. requireNotNUll() 은 null이 아니면 그 값을 반환하고, null이면 동일한 Exception 발생시킨다.
        require(maxProgress > 0) { "maxProgress=$maxProgress; must be > 0" }
        require(progressIncrement > 0) { "progressIncrement=$progressIncrement; must be > 0" }
    }

    /**
     * Indicates the race participant's current progress
     */
    var currentProgress by mutableStateOf(initialProgress)
        private set

    suspend fun run(){
        try{
            while(currentProgress < maxProgress){
                delay(progressDelayMillis)
                currentProgress += progressIncrement
            }
        } catch(e: CancellationException){
            Log.e("shhan", "$name : ${e.message}")
            throw e
        }

    }

    /**
     * Regardless of the value of [initialProgress] the reset function will reset the
     * [currentProgress] to 0
     */
    fun reset() {
        currentProgress = 0
    }
}

/**
 * The Linear progress indicator expects progress value in the range of 0-1. This property
 * calculate the progress factor to satisfy the indicator requirements.
 */
val RaceParticipant.progressFactor: Float
    get() = currentProgress / maxProgress.toFloat()
