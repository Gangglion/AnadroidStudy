package com.glion.scheduletest

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import java.time.LocalDateTime
import java.time.LocalTime

// 라이브러리 화 해서 사용하면 편하지 않을까?
class MainActivity : AppCompatActivity() {
    private lateinit var mContext: Context
    private lateinit var mScTable: ScheduleTable
    // 일어나는 시간, 자는 시간 예시 세팅
    private var wakeUp = "0"
    private var sleepDown = "0"

    // TODO : layout_time 미리 선언 24개
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mContext = this
        wakeUp = "08:00"
        sleepDown = "05:00"
        init()
//        mScTable.changeColor()
    }

    private fun init(){
//        mScTable = findViewById(R.id.sc_table)
//        mScTable.initTable(wakeUp, sleepDown)
    }
}