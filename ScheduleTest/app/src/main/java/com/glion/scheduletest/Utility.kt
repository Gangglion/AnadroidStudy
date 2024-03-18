package com.glion.scheduletest

import android.content.Context
import kotlin.math.roundToInt

object Utility {
    fun dpToPx(context: Context, dp: Int): Int{
        val density = context.resources.displayMetrics.density
        return (dp.toFloat() * density).roundToInt()
    }
}