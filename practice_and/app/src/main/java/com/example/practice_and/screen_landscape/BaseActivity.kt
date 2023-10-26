package com.example.practice_and.screen_landscape

import android.content.res.Configuration
import android.os.Bundle
import android.os.PersistableBundle
import android.util.DisplayMetrics
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        // shhan(231026) : 시스템 화면크기, 텍스트크기 상관 없이 항상 고정된 크기의 레이아웃을 유지하기 위한 코드 추가
        val configuration: Configuration = resources.configuration
        // MEMO : 앱의 폰트 스케일을 1로 고정
        configuration.fontScale = 1.toFloat() //0.85 small size, 1 normal size, 1,15 big etc

        val metrics = DisplayMetrics()
        // MEMO : 디스플레이의 디스플레이 측정값을 가져옴
        windowManager.defaultDisplay.getMetrics(metrics)
        // MEMO : 앱의 DPI를 x축 인치당 도트 수로 설정함
        configuration.densityDpi = resources.displayMetrics.xdpi.toInt()
        // MEMO : 위에서 설정한 구성을 앱 전체에 적용시킴
        baseContext.resources.updateConfiguration(configuration, metrics)
    }
}