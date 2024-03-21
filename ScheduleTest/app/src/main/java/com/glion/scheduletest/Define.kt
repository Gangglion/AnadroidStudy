package com.glion.scheduletest

import android.graphics.Rect
import android.view.View

object Define {
    const val CURE = 0
    const val REVIEW = 1
    const val PRIVATE = 2

    fun calculateRectOnScreen(view: View): Rect {
        val location = IntArray(2)
        view.getLocationOnScreen(location)
        return Rect(
            location[0],
            location[1],
            location[0] + view.measuredWidth,
            location[1] + view.measuredHeight
        )
    }
}