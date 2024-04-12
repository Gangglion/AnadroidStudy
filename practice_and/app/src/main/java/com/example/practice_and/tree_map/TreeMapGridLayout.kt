package com.example.practice_and.tree_map

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
import com.example.practice_and.R
import java.text.DecimalFormat
import kotlin.math.pow
import kotlin.math.round

class TreeMapGridLayout: GridLayout {
    private var mContext: Context
    private lateinit var columnNames: Array<String?> // GridItem 의 Tag 에 사용되는 배열 - columnCount 까지 순서대로 이루어져있음
    private lateinit var rowNames: Array<String?> // GridItem 의 Tag 에 사용되는 배열 - rowCount까지 순서대로 이루어져있음

    private var mData: MutableList<GetData>? = null
    private var mDataCopy: MutableList<GetData> = mutableListOf()
    private var mDataWithArea: MutableList<GetData> = mutableListOf()

    private var cursorRow: Int = 0
    private var cursorCol: Int = 0
    private var isRowDiv: Boolean = true
    private var isAllValueSame: Boolean = false

    constructor(context: Context?) : super(context!!) {
        mContext = context
        initView(context)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        mContext = context
        initView(context, attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!,
        attrs,
        defStyleAttr
    ) {
        mContext = context
        initView(context, attrs, defStyleAttr)
    }

    @SuppressLint("CustomViewStyleable")
    private fun initView(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int? = null) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.layout_grid_layout, this)

        initRowColumnNames() //행,열 이름 초기화
        addCells() //cell추가
    }

    private fun initRowColumnNames() {
        columnNames = arrayOfNulls(columnCount)
        rowNames = arrayOfNulls(rowCount)
        for (i in 0 until columnCount) {
            columnNames[i] = "$i"
        }
        for (i in 0 until rowCount) {
            rowNames[i] = "$i"
        }
    }

    private fun addCells() {
        for (rowIdx in 0 until rowCount) {
            for (columnIdx in 0 until columnCount) {
                var gridItem: GridItem? = null
                gridItem = GridItem(mContext)
                gridItem.tag = "${rowNames[rowIdx]}/${columnNames[columnIdx]}"
                gridItem.apply {
                    column = columnIdx
                    row = rowIdx
                }

                val layoutParams = LayoutParams(spec(rowIdx, 1.0f), spec(columnIdx, 1.0f))
                layoutParams.apply {
                    setGravity(Gravity.FILL)
                    width = 0
                    height = 0
                }
                gridItem.layoutParams = layoutParams
                addView(gridItem)
            }
        }
    }

    fun setData(data: List<GetData>){
        mData = data.toMutableList()
        orderDesc()
        calculateArea()
        calculateGridArea()
    }

    private fun orderDesc(){
        // 내림차순으로 mData 정렬 - 버블정렬 사용
        for(i in mData!!.indices){
            for(j in 0 until mData!!.size -i -1 step(1)){
                if(mData!![j+1].value.toInt() > mData!![j].value.toInt()){
                    val temp = mData!![j]
                    mData!![j] = mData!![j+1]
                    mData!![j+1] = temp
                }
            }
        }
        mDataCopy = mData!!.map { it.copy() }.toMutableList()
        Log.d("shhan", "originData : ${mData.toString()}")
        Log.d("shhan", "orderDesc : ${mDataCopy}")
    }

    private fun calculateArea(){
        val valueSum = mDataCopy.sumOf { it.value.toInt() }
        for(i in mDataCopy.indices){
            mDataCopy[i].value = (roundToDigit((mDataCopy[i].value.toFloat() / valueSum), 2) * 100).toInt().toString()
        }
        isAllValueSame = mDataCopy.all{ it.value == mDataCopy[0].value }
        Log.d("shhan", "originData : $mData")
        Log.d("shhan", "calculateArea : $mDataCopy")
    }

    private fun roundToDigit(number: Float, digits: Int): Double{
        return Math.round(number * 10.0.pow(digits.toDouble())) / 10.0.pow(digits.toDouble())
    }

    private fun calculateGridArea(){
        if(isAllValueSame){ // 4개의 값이 모두 같을때
            mergeGridAllSame()
            return
        }
        for(idx in mDataCopy.indices){
            if(idx == 0 && isRowDiv){
                val roundArea = roundToDigit(mDataCopy[idx].value.toFloat(), -1).toInt()
                val changeCursorRow = roundArea / rowCount
                mergeGrid(row = cursorRow, column = cursorCol, limitRow = changeCursorRow, itemIdx = idx)
                cursorRow += changeCursorRow
                Log.d("shhan", "cursorRow : $cursorRow / cursorCol : $cursorCol / mDataWithArea : $roundArea")
            }
            if(idx == 1){
                if(mDataCopy[0].value.toInt() == mDataCopy[1].value.toInt()){
                    val roundArea = roundToDigit(mDataCopy[idx].value.toFloat(), -1).toInt()
                    val changeCursorRow = (roundArea / rowCount) + cursorRow
                    mergeGrid(row = cursorRow, column = cursorCol, limitRow = changeCursorRow, itemIdx = idx)
                    cursorRow = changeCursorRow
                    Log.d("shhan", "changeCursorRow : $changeCursorRow / mDataWithArea : $roundArea")
                    isRowDiv = !isRowDiv // 다음 행분할 진행
                } else{
                    isRowDiv = !isRowDiv // 같지 않으므로 행 분할로 변경
                    val resultCalDiffArea = calDiffArea(mDataCopy[idx].value.toInt())
                    val cursorMove = resultCalDiffArea.second + cursorCol
                    mergeGrid(row = cursorRow, column = cursorCol, limitCol = cursorMove, itemIdx = idx)
                    cursorCol += cursorMove
                    Log.d("shhan", "cursorRow : $cursorRow / cursorCol : $cursorCol / mDataWithArea : ${resultCalDiffArea.first}")
                    isRowDiv = !isRowDiv // 다음 분할을 위해 열 분할로 변경
                }
            }
            if(idx == 2){
                val resultCalDiffArea = calDiffArea(mDataCopy[idx].value.toInt())
                val cursorMove = resultCalDiffArea.second
                if(isRowDiv){
                    mergeGrid(row = cursorRow, column = cursorCol, limitRow = cursorMove + cursorRow, itemIdx = idx)
                    cursorRow += cursorMove
                } else{
                    mergeGrid(row = cursorRow, column = cursorCol, limitCol = cursorMove + cursorCol, itemIdx = idx)
                    cursorCol += cursorMove
                }
                isRowDiv = !isRowDiv
                Log.d("shhan", "cursorRow : $cursorRow / cursorCol : $cursorCol / mDataWithArea : ${resultCalDiffArea.first}")
            }
            if(idx == 3){
                Log.d("shhan", "나머지 공간 합치기")
                mergeGrid(row = cursorRow, column = cursorCol, itemIdx = idx)
            }
        }
    }

    // 열 분할일때는 남은 행 수로 계산, 행 분할일때는 남은 열 수로 계산
    private fun calDiffArea(value: Int): Pair<Int, Int>{
        var result = 0
        if(isRowDiv){ // 열분할일때
            val remain = columnCount - cursorCol
            if (value % remain >= remain / 2) {
                result = value + (value % remain)
            } else {
                result = value - (value % remain)
            }
            val cursorMove = (result / remain)
            return Pair(result, cursorMove)
        } else{ // 행분할일때
            val remain = rowCount - cursorRow
            if (value % remain >= remain / 2) {
                result = value + (value % remain)
            } else {
                result = value - (value % remain)
            }
            val cursorMove = result / remain
            return Pair(result, cursorMove)
        }
    }

    fun findItem(rowName: Int, columnName: Int) : GridItem?{
        return findViewWithTag("$rowName/$columnName")
    }

    private fun mergeGrid(row: Int, column: Int, limitRow: Int = 10, limitCol: Int = 10, itemIdx: Int){
        val item = findItem(row, column)
        item?.setValue(mData!![itemIdx].title, mData!![itemIdx].value, item.tag.toString())
        setItemColor(mData!![itemIdx].title, item!!)

        for(rowIdx in row until limitRow){
            for(colIdx in column until limitCol){
                if(rowIdx == row && colIdx == column)
                    continue
                val deleteItem = findItem(rowIdx, colIdx)
                deleteItem!!.visibility = View.INVISIBLE
                item.addSpannedItems(deleteItem)
            }
        }
        val layoutParam = item.layoutParams as LayoutParams
        layoutParam.apply{
            rowSpec = spec(column, limitCol - column, 1.0f) // rowSpec - 세로 병합 지정
            columnSpec = spec(row, limitRow - row, 1.0f) // columnSpec - 가로 병합 지정
        }
        item.layoutParams = layoutParam
    }

    private fun mergeGridAllSame(){
        val item1 = findItem(0,0)
        item1.apply{
            setItemColor(mData!![0].title, this!!)
            setValue(mData!![0].title, mData!![0].value, this.tag.toString())
        }
        for(i in 0 until 5){
            for(j in 0 until 5){
                if(i == 0 && j == 0)
                    continue
                val deleteItem = findItem(i,j)
                deleteItem!!.visibility = View.GONE
                item1!!.addSpannedItems(deleteItem)
            }
        }
        val layoutParams1 = item1?.layoutParams as LayoutParams
        layoutParams1.apply{
            rowSpec = spec(0, 5, 1.0f)
            columnSpec = spec(0, 5, 1.0f)
        }
        item1.layoutParams = layoutParams1

        val item2 = findItem(0,5)
        item2.apply{
            setItemColor(mData!![1].title, this!!)
            setValue(mData!![1].title, mData!![1].value, this.tag.toString())
        }
        for(i in 0 until 5){
            for(j in 5 until 10){
                if(i == 0 && j == 5)
                    continue
                val deleteItem = findItem(i,j)
                deleteItem!!.visibility = View.GONE
                item2!!.addSpannedItems(deleteItem)
            }
        }
        val layoutParams2 = item2?.layoutParams as LayoutParams
        layoutParams2.apply{
            rowSpec = spec(0, 5, 1.0f)
            columnSpec = spec(5, 5, 1.0f)
        }
        item2.layoutParams = layoutParams2


        val item3 = findItem(5,0)
        item3.apply{
            setItemColor(mData!![2].title, this!!)
            setValue(mData!![2].title, mData!![2].value, this.tag.toString())
        }
        for(i in 5 until 10){
            for(j in 0 until 5){
                if(i == 5 && j == 0)
                    continue
                val deleteItem = findItem(i,j)
                deleteItem!!.visibility = View.GONE
                item3!!.addSpannedItems(deleteItem)
            }
        }
        val layoutParams3 = item3?.layoutParams as LayoutParams
        layoutParams3.apply{
            rowSpec = spec(5, 5, 1.0f)
            columnSpec = spec(0, 5, 1.0f)
        }
        item3.layoutParams = layoutParams3

        val item4 = findItem(5,5)
        item4.apply{
            setItemColor(mData!![3].title, this!!)
            setValue(mData!![3].title, mData!![3].value, this.tag.toString())
        }
        for(i in 5 until 10){
            for(j in 5 until 10){
                if(i == 5 && j == 5)
                    continue
                val deleteItem = findItem(i,j)
                deleteItem!!.visibility = View.GONE
                item4!!.addSpannedItems(deleteItem)
            }
        }
        val layoutParams4 = item4?.layoutParams as LayoutParams
        layoutParams4.apply{
            rowSpec = spec(5, 5, 1.0f)
            columnSpec = spec(5, 5, 1.0f)
        }
        item4.layoutParams = layoutParams4
    }

    private fun setItemColor(value: String, item: GridItem){
        when(value){
            "항목1" -> item.setBackgroundColor(mContext.getColor(R.color.indigo_200))
            "항목2" -> item.setBackgroundColor(mContext.getColor(R.color.orange_chart))
            "항목3" -> item.setBackgroundColor(mContext.getColor(R.color.gray))
            "항목4" -> item.setBackgroundColor(mContext.getColor(R.color.btn_color))
        }
    }
}
