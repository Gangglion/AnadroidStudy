package com.glion.scheduletest

import android.graphics.Rect
import android.util.Log
import android.view.View

object Define {
    const val CURE = 0
    const val REVIEW = 1
    const val PRIVATE = 2

    fun calculateRectOnScreen(view: View): Rect {
        val location = IntArray(2)
        view.getLocationInWindow(location) // MEMO : onCreate 에서 호출 시, getLocationOnScreen 을 계산하기 너무 이른 시점이기 때문에 0이 리턴되는 문제가 존재한다.
        return Rect(
            location[0],
            location[1],
            location[0] + view.measuredWidth,
            location[1] + view.measuredHeight
        )
    }
}