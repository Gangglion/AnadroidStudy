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
import androidx.annotation.ColorRes
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
                    gridItem.setText(displayRowNames[rowIdxForTime]!!)
                }
                // MEMO : 캘린더/시간표 부분의 오른쪽 일정Item 항목 설정
                if(columnIdx == 1) {
                    gridItem.setText("")
                }
                if (rowIdx % 2 == 0 && columnIdx == 1) {
                    gridItem.background = AppCompatResources.getDrawable(
                        mContext,
                        R.drawable.top_line
                    )
                }

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
     * 열이름/행이름 의 태그를 가진 gridItem 리턴
     * @param rowName 열이름(0,1,2,3...)
     * @param columnName 행이름(0,1)
     */
    fun findItem(rowName: String, columnName: String) : GridItem?{
        return findViewWithTag("$rowName/$columnName")
    }

    /**
     * 내용이 들어가는 전체 GridItem List 리턴
     */
    fun findAllContentItem(): List<GridItem>{
        val gridItemList: MutableList<GridItem> = mutableListOf()
        for(rowIdx in 0 until rowCount){
            val item = findItem(rowNames[rowIdx]!!, "1")
            gridItemList.add(item!!)
        }
        return gridItemList
    }

    /**
     * 시간을 클릭했을때, 해당 시간대의 gridItem 을 리턴해줌
     */
    fun findItem(time: String, isLeft: Boolean): GridItem?{
        for(rowIdx in 0 until rowCount){
            val gridItem = findItem(rowNames[rowIdx]!!, "0")
            if(gridItem?.getText() == time){
                return if(isLeft){
                    findItem(rowNames[rowIdx]!!, "0")
                } else{
                    findItem(rowNames[rowIdx]!!, "1")
                }
            }
        }
        return null
    }

    /**
     * 일치하는 text를 가진 특정 gridItem 리턴
     */
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
     * @param title 스케쥴 이름
     * @param startTime 스케쥴 시작시간
     * @param endTime 스케쥴 종료시간
     */
    fun addSchedule(title: String, startTime: String, endTime: String){
        val blocks = getTimeDifferenceToBlocks(startTime, endTime) // 시간 차이로 블럭 계산
        val newGridItem = findItem(startTime, false)
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

        newGridItem.apply{
            setText(title)
            setScheduled(true)
            isClickable = true
            // 롱 클릭 이벤트
            setOnLongClickListener{
                mListener?.itemLongClick(this)
                return@setOnLongClickListener true
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
     * @param title 삭제할 title
     */
    fun deleteSchedule(title: String){
        val targetItem = findItemWithContentName(title)
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
     * @param originName 수정 전 원래이름
     * @param changedName 수정하려는 이름
     * @param changedStartTime 수정하려는 시작 시간
     * @param changedEndTime 수정하려는 종료 시간
     */
    fun updateSchedule(originName: String, changedName: String, changedStartTime: String, changedEndTime: String){
        deleteSchedule(originName)
        addSchedule(changedName, changedStartTime, changedEndTime)
    }

    /**
     * 특정 item의 배경색 변경
     * @param item 배경색을 변경할 gridItem
     * @param bgColor 컬러 ID
     */
    fun changeBackground(item: GridItem, @ColorRes bgColor: Int){
        item.setBackgroundColor(mContext.getColor(bgColor))
    }

    private fun dpToPx(context: Context, dp: Int): Int{
        val density = context.resources.displayMetrics.density
        return (dp.toFloat() * density).roundToInt()
    }

    /**
     * view의 절대위치
     * @param 대상 view
     */
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

    private fun getTimeDifferenceToBlocks(startTime: String, endTime: String): Int{
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val start = LocalTime.parse(startTime, formatter)
        val end = LocalTime.parse(endTime, formatter)
        val difference = ChronoUnit.MINUTES.between(start, end) // 시간 차이가 분 단위로 계산된다(13:00 - 12:00 = 60)
        Log.d("shhan", "blocks : ${(difference / 30).toInt()}")
        return (difference / 30).toInt()
    }
}