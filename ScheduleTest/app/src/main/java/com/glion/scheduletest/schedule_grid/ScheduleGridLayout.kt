package com.glion.scheduletest.schedule_grid

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.get
import com.glion.scheduletest.Define
import com.glion.scheduletest.R
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import kotlin.math.roundToInt


class ScheduleGridLayout : GridLayout {
    private var mContext: Context
    private var cellColor = 0
    private var cellTextColor = 0
    private var cellMarginTop = 0
    private var cellMarginStart = 0
    private var cellMarginBottom = 0
    private var cellMarginEnd = 0
    private lateinit var rowNames: Array<String?> // GridItem 의 Tag 에 사용되는 배열 - rowCount까지 순서대로 이루어져있음
    private lateinit var displayRowNames: Array<String?> // 실제 표시될 열 이름 배열
    private lateinit var columnNames: Array<String?> // GridItem 의 Tag 에 사용되는 배열 - columnCount 까지 순서대로 이루어져있음

    private var rowIdxForTime = 0

    private var mListener: GridItemInterface? = null

    constructor(context: Context?) : super(context!!) {
        mContext = context
        initView(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        mContext = context
        initView(context, attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context!!, attrs, defStyleAttr) {
        mContext = context
        initView(context, attrs, defStyleAttr)
    }

    @SuppressLint("CustomViewStyleable")
    private fun initView(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int? = null){
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.schedule_grid_layout, this)
        if(attrs != null){
            val styles = context.obtainStyledAttributes(attrs, R.styleable.ScheduleGridLayout)
            cellColor = styles.getColor(
                R.styleable.ScheduleGridLayout_itemColor, resources.getColor(
                    R.color.white, null))
            cellTextColor = styles.getColor(
                R.styleable.ScheduleGridLayout_itemTextColor, resources.getColor(
                    R.color.black, null))
            cellMarginTop = styles.getInt(R.styleable.ScheduleGridLayout_itemMarginTop, 0)
            cellMarginStart = styles.getInt(R.styleable.ScheduleGridLayout_itemMarginLeft, 0)
            cellMarginBottom = styles.getInt(R.styleable.ScheduleGridLayout_itemMarginBottom, 0)
            cellMarginEnd = styles.getInt(R.styleable.ScheduleGridLayout_itemMarginRight, 0)
            styles.recycle()
        }
    }

    fun setListener(listener: GridItemInterface){
        mListener = listener
    }

    /**
     * 시간표 시작시간과 종료시간을 세팅
     * @param startTime "MM:dd" 형태
     * @param endTime "MM:dd" 형태
     */
    fun setStartEndTime(startTime: String, endTime: String){
        val startHour = startTime.split(":")[0].toInt()
        val endHour = endTime.split(":")[0].toInt()

        val length = if (startHour < endHour) {
            endHour - startHour + 1
        } else {
            (endHour + 24) - startHour + 1
        }

        rowCount = length * 2

        initRowColumnNames(startHour) //행,열 이름 초기화
        addCells() //cell추가
    }

    private fun initRowColumnNames(startTime: Int){
        rowNames = arrayOfNulls(rowCount)
        columnNames = arrayOfNulls(columnCount)
        displayRowNames = arrayOfNulls(rowCount)
        for(i in 0 until rowCount){
            rowNames[i] = "$i"
        }
        for((idx, i) in (0 until rowCount).withIndex()){
            displayRowNames[i] = if(startTime + idx < 24) "%02d:00".format(startTime + idx) else "%02d:00".format((startTime + idx) - 24)
        }
        for(i in 0 until columnCount){
            columnNames[i] = "$i"
        }
    }

    private fun addCells(){
        for(rowIdx in 0 until rowCount){
            for(columnIdx in 0 until columnCount){
                val gridItem = GridItem(mContext)
                gridItem.tag = "${rowNames[rowIdx]}/${columnNames[columnIdx]}"
                gridItem.apply{
                    setRow(rowIdx)
                    setColumn(columnIdx)
                    setBackgroundColor(cellColor)
                    setTextColor(cellTextColor)
                    setGravity(Gravity.CENTER)
                    setText("")
                }

                // displayRowNames 에서 원하는 값만 가져오게 하기 위한 인덱스
                if(rowIdx != 0 && rowIdx % 2 == 0 && columnIdx == 0){
                    rowIdxForTime++
                }

                // MEMO : 캘린더/시간표 부분의 왼쪽 시간Item 속성 설정
                if(rowIdx % 2 == 0 && columnIdx == 0) {
                    // temp : D-STOP 예시
                    gridItem.setText(displayRowNames[rowIdxForTime]!!)
                }
                // MEMO : 캘린더/시간표 부분의 오른쪽 일정Item 항목 설정
                if(columnIdx == 1) {
                    // temp : D-STOP 예시
                    gridItem.setText("")
                }
                if (rowIdx % 2 == 0 && columnIdx == 1) {
                    gridItem.background = AppCompatResources.getDrawable(
                        mContext,
                        R.drawable.top_line
                    )
                }
//                gridItem.setText("${rowNames[rowIdx]}/${columnNames[columnIdx]}")

                if(columnIdx == 0){
                    val layoutParams = LayoutParams(spec(rowIdx, 1.0f), spec(columnIdx, 0.4f))
                    layoutParams.apply{
                        setGravity(Gravity.FILL)
                        width = 0
                        height = dpToPx(mContext, 32)
                        setMargins(cellMarginStart, cellMarginTop, cellMarginEnd, cellMarginBottom)
                    }
                    gridItem.layoutParams = layoutParams
                } else{
                    val layoutParams = LayoutParams(spec(rowIdx, 1.0f), spec(columnIdx, 1.0f))
                    layoutParams.apply{
                        setGravity(Gravity.FILL)
                        width = 0
                        height = dpToPx(mContext, 32)
                        setMargins(cellMarginStart, cellMarginTop, cellMarginEnd, cellMarginBottom)
                    }
                    gridItem.layoutParams = layoutParams
                }
                addView(gridItem)
            }
        }
    }

    /**
     * 현재 시간에 일치하는 GridItem 의 좌표가 있는 Rect 리턴
     */
    fun getNowTimeItemLocation(nowTime: String): Rect {
        val nowTimeItem = findItem(nowTime)
        return calculateRectOnScreen(nowTimeItem!!)
    }

    fun findItem(rowName: String, columnName: String) : GridItem?{
        return findViewWithTag("$rowName/$columnName")
    }
    fun findItem(row: Int, column: Int){

    }

    /**
     * 시간을 클릭했을때, 해당 시간대의 gridItem 을 리턴해줌
     */
    fun findItem(time: String): GridItem?{
        for(rowIdx in 0 until rowCount){
            val gridItem = findItem(rowNames[rowIdx]!!, "0")
            if(gridItem?.getText() == time){
                return findItem(rowNames[rowIdx]!!, "1")
            }
        }
        return null
    }

    fun findItemWithContentName(name: String): GridItem?{
        for(rowIdx in 0 until rowCount){
            val gridItem = findItem(rowNames[rowIdx]!!, "1")
            if(gridItem?.getText() == name){
                return gridItem
            }
        }
        return null
    }

    /**
     * 스케쥴 등록
     * @param blocks 30분 단위로 1 / 1시간 등록 2, 2시간 등록 4 ...
     */
    fun addSchedule(item: String, startTime: String, endTime: String, type: Int){
        val blocks = getTimeDifferenceToBlocks(startTime, endTime) // 시간 차이로 블럭 계산
        val newGridItem = findItem(startTime)
        // 스케쥴 있는지 확인
        if(newGridItem?.visibility == View.GONE || newGridItem?.isScheduled() == true ){ // 넣고자 하는 시간표에 스케쥴이 들어가 있는지 확인
            Toast.makeText(mContext, "해당 시간에 스케쥴이 존재함!", Toast.LENGTH_SHORT).show()
            return
        }
        val startIndex = newGridItem!!.getRow()
        // 해당하는 칸만큼 삭제
        for(i in 1 until blocks){
            val cell = findItem(rowNames[startIndex + i]!!, "1")
            cell?.visibility = View.GONE
            newGridItem?.addSpannedCells(cell)
        }

        newGridItem?.apply{
            setText(item)
            setScheduled(true)
            isClickable = true
            // 롱 클릭 이벤트
            setOnLongClickListener{
                mListener?.itemLongClick(this)
                return@setOnLongClickListener true
            }

            // 활동 종류 따라서 색이 바뀜
            if(type == Define.CURE){ // 치료활동
                setBackgroundColor(mContext.getColor(R.color.blue))
            } else if(type == Define.REVIEW){ // 회고활동 일때
                setBackgroundColor(mContext.getColor(R.color.red))
            } else if(type == Define.PRIVATE){ // 개인/고정일정 일때
                setBackgroundColor(mContext.getColor(R.color.purple))
            }
        }
        val layoutParams = newGridItem?.layoutParams as LayoutParams
        layoutParams.apply{
            rowSpec = spec(startIndex, blocks, 1.0f)
        }
        newGridItem.layoutParams = layoutParams
    }

    /**
     * 스케쥴 삭제
     * @param item 삭제할 item 이름
     */
    fun deleteSchedule(item: String){
        val targetItem = findItemWithContentName(item)
        val row = targetItem?.getRow()
        // 셀 삭제
        targetItem?.apply{
            setText("")
            setScheduled(false)
            isClickable = false
            setBackgroundColor(mContext.getColor(R.color.white))
            background = AppCompatResources.getDrawable(mContext, R.drawable.top_line)
        }
        // 병합된 item 복구
        val layoutParams: LayoutParams = targetItem?.layoutParams as LayoutParams
        layoutParams.rowSpec = spec(row!!, 1, 1.0f)
        targetItem.layoutParams = layoutParams

        // 삭제된 셀 visibility 변경
        val deletedItems = targetItem.getSpannedCells()
        for(i in deletedItems.indices){
            deletedItems[i].visibility = View.VISIBLE
        }
        deletedItems.clear()
    }

    /**
     * 스케쥴 수정
     */
    fun updateSchedule(originName: String, changedName: String, changedStartTime: String, changedEndTime: String, type: Int){
        deleteSchedule(originName)
        addSchedule(changedName, changedStartTime, changedEndTime, type)
    }

    private fun dpToPx(context: Context, dp: Int): Int{
        val density = context.resources.displayMetrics.density
        return (dp.toFloat() * density).roundToInt()
    }

    fun calculateRectOnScreen(view: View): Rect {
        val location = IntArray(2)
        view.getLocationInWindow(location) // MEMO : onCreate 에서 호출 시, getLocationOnScreen 을 계산하기 너무 이른 시점이기 때문에 0이 리턴되는 문제가 존재한다.
        return Rect(
            location[0],
            location[1],
            location[0] + view.measuredWidth,
            location[1] + view.measuredHeight
        )
    }

    /**
     * 시작시간과 종료시간으로 병합해야 하는 칸 수 리턴
     * @param startTime 시작시간(HH:mm)
     * @param endTime 종료시간(HH:MM)
     */
    private fun getTimeDifferenceToBlocks(startTime: String, endTime: String): Int{
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val start = LocalTime.parse(startTime, formatter)
        val end = LocalTime.parse(endTime, formatter)
        val difference = ChronoUnit.MINUTES.between(start, end) // 시간 차이가 분 단위로 계산된다(13:00 - 12:00 = 60)
        Log.d("shhan", "blocks : ${(difference / 30).toInt()}")
        return (difference / 30).toInt()
    }
}