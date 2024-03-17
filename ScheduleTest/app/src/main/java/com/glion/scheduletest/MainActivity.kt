package com.glion.scheduletest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

// 라이브러리 화 해서 사용하면 편하지 않을까?
class MainActivity : AppCompatActivity() {
    // 일어나는 시간, 자는 시간 예시 세팅
    private var wakeUp = "0"
    private var sleepDown = "0"

    // TODO : layout_time 미리 선언 24개
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        wakeUp = "08:00"
        sleepDown = "23:00"
        // 08시부터 23시까지 보여줘야 함 - 간격 16개 -> (sleepDown - wakeUp + 1)
        for(i in 0 until 16){

        }
    }
}