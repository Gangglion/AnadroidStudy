package com.glion.scheduletest

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.glion.scheduletest.ScheduleGrid.ScheduleGridLayout

// 라이브러리 화 해서 사용하면 편하지 않을까?
class MainActivity : AppCompatActivity() {
    private lateinit var mContext: Context
//    private lateinit var mScTable: ScheduleTable

    // 일어나는 시간, 자는 시간 예시 세팅
    private var wakeUp = "0"
    private var sleepDown = "0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mContext = this
        wakeUp = "06:00"
        sleepDown = "23:00"

        val scheduleGridLayout = findViewById<ScheduleGridLayout>(R.id.sc_layout)
        scheduleGridLayout.setStartEndTime(wakeUp, sleepDown)

//        scheduleGridLayout.addSchedule("테스트 일정", "2", 2)

//        init()
//        mScTable.changeColor()
    }

//    private fun init(){
//        mScTable = findViewById(R.id.sc_table)
//        mScTable.initTable(wakeUp, sleepDown)
//    }
}