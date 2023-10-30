package com.glion.bugreenactment

import android.content.Context
import android.content.res.Configuration

object FixConfiguration {
    fun wrap(context: Context): Context{
        val res = context.resources
        val configuration: Configuration = res.configuration
        // MEMO : 앱의 폰트 스케일을 1로 고정
        configuration.fontScale = 1.toFloat() //0.85 small size, 1 normal size, 1,15 big etc
        // MEMO : 앱의 DPI를 x축 인치당 도트 수로 설정함
        configuration.densityDpi = res.displayMetrics.xdpi.toInt()
        // MEMO : 위에서 설정한 구성을 앱 전체에 적용시킴
        return context.createConfigurationContext(configuration)
    }
}