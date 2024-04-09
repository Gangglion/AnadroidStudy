package com.example.practice_and.tree_map

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.GridLayout
import androidx.appcompat.content.res.AppCompatResources
import com.example.practice_and.R

class TreeMapGridLayout: GridLayout {
    private var mContext: Context
    private lateinit var columnNames: Array<String?> // GridItem 의 Tag 에 사용되는 배열 - columnCount 까지 순서대로 이루어져있음
    private lateinit var rowNames: Array<String?> // GridItem 의 Tag 에 사용되는 배열 - rowCount까지 순서대로 이루어져있음

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
        inflater.inflate(R.layout.layout_grid_layout, this)

        initRowColumnNames() //행,열 이름 초기화
        addCells() //cell추가
    }

    private fun initRowColumnNames(){
        columnNames = arrayOfNulls(columnCount)
        rowNames = arrayOfNulls(rowCount)
        for(i in 0 until columnCount){
            columnNames[i] = "$i"
        }
        for(i in 0 until rowCount){
            rowNames[i] = "$i"
        }
    }

    private fun addCells(){
        for(columnIdx in 0 until rowCount){
            for(rowIdx in 0 until columnCount){
                var gridItem: GridItem? = null
                gridItem = GridItem(mContext)
                gridItem.tag = "${columnNames[columnIdx]}/${rowNames[rowIdx]}"
                gridItem.apply{
                    column = columnIdx
                    row = rowIdx
                    setValue(column.toString(), row.toString())
                }

                val layoutParams = LayoutParams(spec(columnIdx, 1.0f), spec(rowIdx, 1.0f))
                layoutParams.apply{
                    setGravity(Gravity.FILL)
                    width = 0
                    height = 0
                }
                gridItem.layoutParams = layoutParams
                addView(gridItem)
            }
        }
    }
}
