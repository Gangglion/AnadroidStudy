package com.glion.scheduletest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import android.widget.Toast
import com.glion.scheduletest.schedule_grid.GridItem
import com.glion.scheduletest.schedule_grid.GridItemInterface
import com.glion.scheduletest.schedule_grid.ScheduleGridLayout
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.abs

class ScheduleFragment : Fragment(), GridItemInterface {
    private lateinit var mContext: Context
    private lateinit var mScheduleGridLayout: ScheduleGridLayout
    private lateinit var mScrollView: ScrollView

    private var wakeUp = "0"
    private var sleepDown = "0"

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_schedule, container, false).apply{
            // 프래그먼트에서 addOnWindowFocusChangeListener 사용하는 방법 - view.viewTreeObserver 사용
            // view 가 그려진 뒤 알림을 받을 수 있는 옵저버이다
            viewTreeObserver?.addOnWindowFocusChangeListener {
                if(it) scrollToNowTime() // 현재 뷰가 그려지면 스크롤 진행하도록
            }
        }

        mScrollView = view.findViewById(R.id.sc_view)
        mScheduleGridLayout = view.findViewById(R.id.sc_layout)
        // 스케쥴 시작시간과 종료시간 세팅
        wakeUp = "06:00"
        sleepDown = "23:00"

        initSchedule()

        // MEMO : 추가 예시
        mScheduleGridLayout.addSchedule("치료 일정", "07:00", "09:00", Define.CURE)
        mScheduleGridLayout.addSchedule("회고활동 일정", "11:00", "15:00", Define.REVIEW)
        mScheduleGridLayout.addSchedule("개인 일정", "23:00", "23:30", Define.PRIVATE)

        // MEMO : 삭제 예시
        Handler(Looper.getMainLooper()).postDelayed({
            mScheduleGridLayout.deleteSchedule("회고활동 일정")
        }, 1000)

        // MEMO : 수정 예시
        Handler(Looper.getMainLooper()).postDelayed({
            mScheduleGridLayout.updateSchedule("치료 일정", "치료 일정_수정", "10:00", "11:00", Define.CURE)
        }, 2000)
        return view
    }

    private fun initSchedule(){
        mScheduleGridLayout.setListener(this)
        mScheduleGridLayout.setStartEndTime(wakeUp, sleepDown)
    }

    override fun itemLongClick(item: GridItem) {
        Log.d("shhan", "수정 : ${item.getText()}")
        Toast.makeText(mContext, "길게 누르면 수정 다이어로그 뜸", Toast.LENGTH_SHORT).show()
        startActivity(Intent(mContext, ScheduleEditActivity::class.java))
    }

    // 화면에서의 ScrollView Top 좌표에서 ScrollView의 현재 스크롤상태인 Y좌표와 대상 뷰의 Top 좌표만큼 더한것을 뺀다 -> 스크롤해야할 만큼의 y좌표
    // 2로 나누어주어, 대상 뷰가 화면 중간쯤에 오도록 함.
    // 사용되는 getLocationOnScreen 은 onCreate 에서 호출 시 뷰가 완전히 그려지기 이전에 좌표값을 구하려고 하기 때문에 0을 리턴한다.
    private fun scrollToNowTime() {
        // 현재 시간 계산
        val formatter = DateTimeFormatter.ofPattern("HH:00")
        val nowTime = LocalDateTime.now().format(formatter)
        Log.d("shhan", "nowTime : $nowTime")
        val rect = mScheduleGridLayout.getNowTimeItemLocation(nowTime)
        val y = abs(mScheduleGridLayout.calculateRectOnScreen(mScrollView).top - (mScrollView.scrollY + rect.top)) / 2
        mScrollView.smoothScrollTo(0, y)
    }
}