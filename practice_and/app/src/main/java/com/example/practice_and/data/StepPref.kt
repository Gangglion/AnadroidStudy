package com.example.practice_and.data

import com.chibatching.kotpref.KotprefModel

object StepPref : KotprefModel() {
    var status : Boolean by booleanPref(false) // 센서 동작 여부
    var step : Int by intPref(0) // 총 걸음 수
}