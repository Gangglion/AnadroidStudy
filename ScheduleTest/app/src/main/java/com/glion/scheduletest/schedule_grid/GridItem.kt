package com.glion.scheduletest.schedule_grid

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.FrameLayout
import android.widget.TextView


class GridItem : FrameLayout {
    private lateinit var mContext: Context
    private lateinit var mTextView: TextView
    private var row = 0
    private var column = 0
    // MEMO: 스케쥴 합치면서 사라진 cell 보관용 - 복구하기 위함
    private var spannedCells: ArrayList<GridItem> = arrayListOf()

    private var isScheduled = false
    constructor(context: Context?) : super(context!!) {
        initView(context, null)
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs){
        initView(context, attrs)
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context!!, attrs, defStyleAttr) {
        initView(context, attrs)
    }

    private fun initView(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int? = null){
        mContext = context
        mTextView = TextView(context)
        val typedValue = TypedValue()
        getContext().theme.resolveAttribute(androidx.appcompat.R.attr.selectableItemBackground, typedValue, true)
        val resId = typedValue.resourceId
        mTextView.setBackgroundResource(resId)

        isClickable = false
        addView(mTextView)

        val layoutParams = mTextView.layoutParams as LayoutParams
        layoutParams.apply{
            width = LayoutParams.MATCH_PARENT
            height = LayoutParams.MATCH_PARENT
        }
        mTextView.layoutParams = layoutParams
    }

    override fun setClickable(clickable: Boolean) {
        super.setClickable(clickable)
        mTextView.isClickable = clickable
    }
    override fun setOnClickListener(l: OnClickListener?) {
        isClickable = true
        mTextView.setOnClickListener(l)
    }

    override fun setOnLongClickListener(l: OnLongClickListener?) {
        isClickable = true
        mTextView.setOnLongClickListener(l)
    }

    fun getRow(): Int {
        return row
    }

    fun setRow(row: Int) {
        this.row = row
    }

    fun getColumn(): Int {
        return column
    }

    fun setColumn(column: Int) {
        this.column = column
    }
    fun setTextColor(color: Int) {
        mTextView.setTextColor(color)
    }

    fun setGravity(gravity: Int){
        mTextView.gravity = gravity
    }

    fun setText(text: String){
        mTextView.text = text
    }
    fun getText(): String{
        return mTextView.text.toString()
    }
    fun addSpannedCells(gridItem: GridItem?) {
        spannedCells.add(gridItem!!)
    }

    fun isScheduled(): Boolean{
        return isScheduled
    }

    fun setScheduled(isScheduled: Boolean){
        this.isScheduled = isScheduled
    }
}