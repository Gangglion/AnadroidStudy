package com.glion.scheduletest.ScheduleGrid

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import com.glion.scheduletest.R
import com.glion.scheduletest.Utility
import java.util.Arrays


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
//        mergeTimeCell() // 시간 셀 병합
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
                if (rowIdx % 2 == 1 && columnIdx == 1) {
                    gridItem.background = AppCompatResources.getDrawable(
                        mContext,
                        R.drawable.bottom_line
                    )
                }
//                gridItem.setText("${rowNames[rowIdx]}/${columnNames[columnIdx]}")

                if(columnIdx == 0){
                    val layoutParams = LayoutParams(spec(rowIdx, 1.0f), spec(columnIdx, 0.4f))
                    layoutParams.apply{
                        setGravity(Gravity.FILL)
                        width = 0
                        height = Utility.dpToPx(mContext, 32)
                        setMargins(cellMarginStart, cellMarginTop, cellMarginEnd, cellMarginBottom)
                    }
                    gridItem.layoutParams = layoutParams
                } else{
                    val layoutParams = LayoutParams(spec(rowIdx, 1.0f), spec(columnIdx, 1.0f))
                    layoutParams.apply{
                        setGravity(Gravity.FILL)
                        width = 0
                        height = Utility.dpToPx(mContext, 32)
                        setMargins(cellMarginStart, cellMarginTop, cellMarginEnd, cellMarginBottom)
                    }
                    gridItem.layoutParams = layoutParams
                }
                addView(gridItem)
            }
        }
    }

    private fun mergeTimeCell(){
        for(rowIdx in 0 until rowCount){
            if(rowIdx % 2 == 0){ // 짝수일때
                val mergeCell = findCell("${rowNames[rowIdx]}", "0")
                val deletedCell = findCell("${rowNames[rowIdx+1]}", "0")
                Log.d("shhan", "breakPoint")
                deletedCell!!.visibility = View.GONE
                val layoutParams = mergeCell!!.layoutParams as LayoutParams
                layoutParams.rowSpec = spec(mergeCell.getRow(), 2, 1.0f)
                mergeCell.layoutParams = layoutParams
            }
        }
    }

    fun findCell(rowName: String, columnName: String) : GridItem?{
        return findViewWithTag("$rowName/$columnName")
    }

    /**
     * 스케쥴 등록
     */
    fun addSchedule(item: String, startTime: String, blocks: Int){
        // TODO : 시작시간과 끝 시간에 맞는 셀 찾기 - Column 은 1로 고정됨
        val newCell = findCell(startTime, "1")
        // 스케쥴 있는지 확인
        if(newCell?.visibility == View.GONE || newCell?.isScheduled() == true ){ // 넣고자 하는 시간표에 스케쥴이 들어가 있는지 확인
            Toast.makeText(mContext, "해당 시간에 스케쥴이 존재함!", Toast.LENGTH_SHORT).show()
            return
        }
        val rowArrayList: List<Array<String?>> = listOf(rowNames)
        val originIndex = rowArrayList.indexOf(rowNames)
        val startIndex = rowNames.indexOf(startTime)
        // 해당하는 칸만큼 삭제
        for(i in 1 until blocks){
            val cell = findCell(rowNames[startIndex + i]!!, "1")
            cell?.visibility = View.GONE
            newCell?.addSpannedCells(cell)
        }

        newCell?.apply{
            setText(item)
            setScheduled(true)
            isClickable = true
        }
        val layoutParams = newCell?.layoutParams as GridLayout.LayoutParams
        layoutParams.apply{
            rowSpec = spec(startIndex, blocks, 1.0f)
        }
        newCell.layoutParams = layoutParams
    }
}