package com.example.practice_and

enum class AppTempData(val parameter: String) {
    FIRST("첫번째"),
    SECOND("두번째"),
    THIRD("세번째"),
    FOURTH("네번째"),
    FIFTH("다섯번째");

    fun getParameterCombine(): String {
        return "${parameter}<- Parameter 에 무언가를 조합해서 호출한 상수의 parameter 를 이용할 수 있음"
    }

    fun useThisCombine() = run { "${this}<- Constant 에 무언가를 조합해서 현재 Enum Constant 를 이용할 수 있음" }
}