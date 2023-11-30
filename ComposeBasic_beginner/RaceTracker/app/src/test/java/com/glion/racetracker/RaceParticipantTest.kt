package com.glion.racetracker

import com.glion.racetracker.ui.RaceParticipant
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class RaceParticipantTest {
    private val raceParticipant = RaceParticipant(
        name = "Test",
        maxProgress = 100,
        progressDelayMillis = 500L,
        initialProgress = 0,
        progressIncrement = 1
    )

    // MEMO : 레이스 시작 후 진행률 테스트
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun raceParticipant_RaceStarted_ProgressUpdated() = runTest { // MEMO : 테스트블록은 runTest 빌더에 배치되어야 한다.
        val expectedProgress = 1
        // 레이스 시작 시뮬레이션. launch 사용하여 코루틴 실행 후 raceParticipant.run 실행
        launch { raceParticipant.run() }
        // RaceParticipant.run() 은 지연시간 이후에 진행률이 업데이트 된다. 테스트할때는 advandeTimeBy 를 이용하여 지연시간을 줄이는데 도와준다.
        advanceTimeBy(raceParticipant.progressDelayMillis) // advanceTimeBy - 주어진 인수만큼의 가상의 시간을 진행 후 예약된 코루틴을 실행한다.
        runCurrent() // 현재 가상 시간에 예약된 코루틴을 실행한다. advanceTimeBy에서 예약된 코루틴이 없기 때문에 수동으로 실행시켜준다.

        // MEMO : 진행률 업데이트 되었는지 확인. 예상값과 raceParticipant.currentProgress 값 비교
        assertEquals(expectedProgress, raceParticipant.currentProgress)
    }

    // MEMO : 레이스 완료 후 진행률 테스트
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun raceParticipant_RaceEnded_ProgressUpdated() = runTest{
        launch{ raceParticipant.run() }
        advanceTimeBy(raceParticipant.maxProgress * raceParticipant.progressDelayMillis) // 완료까지 걸리는 시간. 가상의 시간 진행
        runCurrent()
        assertEquals(100, raceParticipant.currentProgress)
    }

    @Test
    fun raceParticipant_RacePaused_ProgressUpdated() = runTest{
        val expectedProgress = 5
        val job = launch { raceParticipant.run() }
        advanceTimeBy(raceParticipant.progressDelayMillis * expectedProgress)
        runCurrent()
//        job.cancelAndJoin() // 코루틴을 종료하라는 신호를 보내고, 정상적으로 종료될때까지 기다린다. 실제 RaceParticipant 에서는 종료 시 throw를 던지고 있기 때문에, 해당 코드는 에러가 난다. 테스트를 위해서 주석처리하거나, 예외를 던지지 말자.
        assertEquals(expectedProgress, raceParticipant.currentProgress)
    }

    @Test(expected = IllegalArgumentException::class)
    fun raceParticipant_ProgressIncrementZero_ExceptionThrown() = runTest {
        RaceParticipant(name = "Progress Test", progressIncrement = 0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun raceParticipant_MaxProgressZero_ExceptionThrown() {
        RaceParticipant(name = "Progress Test", maxProgress = 0)
    }
}