package com.glion.scheduletest

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.view.View.OnLayoutChangeListener
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mContext = this
        mScrollView = findViewById(R.id.sc_view)
        // 스케쥴 시작시간과 종료시간 세팅
        wakeUp = "06:00"
        sleepDown = "23:00"

        initSchedule()
        // MEMO : 추가 예시
//        mScheduleGridLayout.addSchedule("치료 일정", "07:00", 4, Define.CURE)
//        mScheduleGridLayout.addSchedule("회고활동 일정", "11:00", 2, Define.REVIEW)
        mScheduleGridLayout.addSchedule("개인 일정", "23:00", 1, Define.PRIVATE)
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
    }

    // onWindowFocusChanged : 현재 Activity의 포커스 여부를 확인(hasFocus true - Activity에 포커스 존재, false - Activity 에 포커스 없음)
    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if(hasFocus){ // 현재 액티비티에 포커스가 존재할때, 스크롤 동작
            scrollToNowTime()
        }
    }

    // 화면에서의 ScrollView Top 좌표에서 ScrollView의 현재 스크롤상태인 Y좌표와 대상 뷰의 Top 좌표만큼 더한것을 뺀다 -> 스크롤해야할 만큼의 y좌표
    // 2로 나누어주어, 대상 뷰가 화면 중간쯤에 오도록 함.
    // 사용되는 getLocationOnScreen 은 onCreate 에서 호출 시 뷰가 완전히 그려지기 이전에 좌표값을 구하려고 하기 때문에 0을 리턴한다.
    private fun scrollToNowTime() {
        val rect = mScheduleGridLayout.getNowTimeItemLocation("18:00")
        val y = abs(calculateRectOnScreen(mScrollView).top - (mScrollView.scrollY + rect.top)) / 2
        mScrollView.smoothScrollTo(0, y)
    }
}