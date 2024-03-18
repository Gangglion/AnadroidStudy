package com.glion.scheduletest

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import kotlinx.coroutines.flow.merge
import kotlin.math.roundToInt

class ScheduleGridLayout : GridLayout {
    private var mContext: Context
    private var cellColor = 0
    private var cellTextColor = 0
    private var cellMarginTop = 0
    private var cellMarginStart = 0
    private var cellMarginBottom = 0
    private var cellMarginEnd = 0
    private lateinit var rowNames: Array<String?>
    private lateinit var columnNames: Array<String?>

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
            val styles = context.obtainStyledAttributes(attrs, R.styleable.CollegeTimeTableLayout)
            cellColor = styles.getColor(R.styleable.CollegeTimeTableLayout_cellColor, resources.getColor(R.color.white, null))
            cellTextColor = styles.getColor(R.styleable.CollegeTimeTableLayout_cellTextColor, resources.getColor(R.color.black, null))
            cellMarginTop = styles.getInt(R.styleable.CollegeTimeTableLayout_cellMarginTop, 5)
            cellMarginStart = styles.getInt(R.styleable.CollegeTimeTableLayout_cellMarginLeft, 5)
            cellMarginBottom = styles.getInt(R.styleable.CollegeTimeTableLayout_cellMarginBottom, 5)
            cellMarginEnd = styles.getInt(R.styleable.CollegeTimeTableLayout_cellMarginRight, 5)
            styles.recycle()
        }

        initRowColumnNames() //행,열 이름 초기화
        addCells() //cell추가
        mergeTimeCell() // 시간 셀 병합
    }

    private fun initRowColumnNames(){
        rowNames = arrayOfNulls(rowCount)
        columnNames = arrayOfNulls(columnCount)
        for(i in 0 until rowCount/2){
            rowNames[i] = "%02d:00".format(i)
        }
        for(i in 0 until columnCount){
            columnNames[i] = "$i"
        }
    }

    private fun addCells(){
        for(rowIdx in 0 until rowCount){
            for(columnIdx in 0 until columnCount){
                val cell = Cell(mContext)
                cell.tag = "${rowNames[rowIdx]}-${columnNames[columnIdx]}"
                cell.apply{
                    setRow(rowIdx)
                    setColumn(columnIdx)
                    setBackgroundColor(cellColor)
                    setTextColor(cellTextColor)
                    setGravity(Gravity.CENTER)
                    setText("")
                }

                if(rowIdx != 0 && rowIdx % 2 == 0 && columnIdx == 0){
                    rowIdxForTime++
                }

                if(rowIdx % 2 == 0 && columnIdx == 0) {
                    cell.setText(rowNames[rowIdxForTime]!!)
                }
                if(columnIdx == 1) {
                    cell.setText("")
                }
                // 0, 2, 4, 6 ... 짝수 Row 셀 병합

                if(columnIdx == 0){
                    val layoutParams = LayoutParams(spec(rowIdx, 1.0f), spec(columnIdx, 0.4f))
                    layoutParams.apply{
                        setGravity(Gravity.FILL)
                        width = 0
                        height = dpToPx(32)
                        setMargins(cellMarginStart, cellMarginTop, cellMarginEnd, cellMarginBottom)
                        background = AppCompatResources.getDrawable(mContext, R.drawable.bottom_line)
                    }
                    cell.layoutParams = layoutParams
                } else{
                    val layoutParams = LayoutParams(spec(rowIdx, 1.0f), spec(columnIdx, 1.0f))
                    layoutParams.apply{
                        setGravity(Gravity.FILL)
                        width = 0
                        height = dpToPx(32)
                        setMargins(cellMarginStart, cellMarginTop, cellMarginEnd, cellMarginBottom)
                    }
                    cell.layoutParams = layoutParams
                }
                addView(cell)
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
    private fun dpToPx(dp: Int): Int{
        val density = context.resources.displayMetrics.density
        return (dp.toFloat() * density).roundToInt()
    }

    fun findCell(rowName: String, columnName: String) : Cell?{
        return findViewWithTag("$rowName-$columnName")
    }
}