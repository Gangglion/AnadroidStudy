package com.glion.scheduletest

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.ScrollView
import android.widget.Toast
import com.glion.scheduletest.Define.calculateRectOnScreen
import com.glion.scheduletest.schedule_grid.GridItem
import com.glion.scheduletest.schedule_grid.GridItemInterface
import com.glion.scheduletest.schedule_grid.ScheduleGridLayout
import kotlin.math.abs

// 라이브러리 화 해서 사용하면 편하지 않을까?
class MainActivity : AppCompatActivity(), GridItemInterface {
    private lateinit var mContext: Context
    private lateinit var mScheduleGridLayout: ScheduleGridLayout
    private lateinit var mScrollView: ScrollView
//    private lateinit var mScTable: ScheduleTable

    private var wakeUp = "0"
    private var sleepDown = "0"

    var isScroll = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mContext = this
        mScrollView = findViewById(R.id.sc_view)
        // 스케쥴 시작시간과 종료시간 세팅
        wakeUp = "06:00"
        sleepDown = "23:00"
        initSchedule()
//        Handler(mainLooper).postDelayed({
//            scrollToNowTime()
//        }, 1000)





        // MEMO : 추가 예시
        mScheduleGridLayout.addSchedule("치료 일정", "07:00", 2, Define.CURE)
        mScheduleGridLayout.addSchedule("회고활동 일정", "11:00", 1, Define.REVIEW)
        mScheduleGridLayout.addSchedule("개인 일정", "15:00", 3, Define.PRIVATE)

//        init()
//        mScTable.changeColor()
    }

    override fun onResume() {
        super.onResume()

    }

    private fun initSchedule(){
        mScheduleGridLayout = findViewById(R.id.sc_layout)
        mScheduleGridLayout.setListener(this)
        mScheduleGridLayout.setStartEndTime(wakeUp, sleepDown)
    }

    override fun itemLongClick(item: GridItem) {
        Log.d("shhan", "수정 : ${item.getText()}")
        Toast.makeText(mContext, "길게 누르면 수정 다이어로그 뜸", Toast.LENGTH_SHORT).show()
        startActivity(Intent(mContext, MainActivity2::class.java))
        isScroll = true
    }

    fun scrollToNowTime(){
        if(isScroll){
            val rect = mScheduleGridLayout.getNowTimeItemLocation("18:00")
            val y = abs(calculateRectOnScreen(mScrollView).top - (mScrollView.scrollY + rect.top)) / 2
            mScrollView.smoothScrollTo(0, y)
        }
    }


//    private fun init(){
//        mScTable = findViewById(R.id.sc_table)
//        mScTable.initTable(wakeUp, sleepDown)
//    }
}