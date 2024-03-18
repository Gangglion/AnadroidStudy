package com.glion.scheduletest

import android.content.Context
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.FrameLayout
import android.widget.TextView


class Cell : FrameLayout {
    private lateinit var mTextView: TextView
    private var row = 0
    private var column = 0
    // MEMO: 스케쥴 합치면서 사라진 cell 보관용 - 복구하기 위함
    private var spannedCells: ArrayList<Cell> = arrayListOf()
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
        mTextView = TextView(context)
        val typedValue = TypedValue()
        getContext().theme.resolveAttribute(androidx.appcompat.R.attr.selectableItemBackground, typedValue, true)
        val resId = typedValue.resourceId
        mTextView.setBackgroundResource(resId)

        isClickable = false
        addView(mTextView)

        val layoutParams = mTextView.layoutParams as LayoutParams
        layoutParams.width = LayoutParams.MATCH_PARENT
        layoutParams.height = LayoutParams.MATCH_PARENT
        mTextView.layoutParams = layoutParams
    }

    override fun setOnClickListener(l: OnClickListener?) {
        super.setOnClickListener(l)
        isClickable = true
        mTextView.setOnClickListener(l)
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
    fun addSpannedCells(cell: Cell?) {
        spannedCells.add(cell!!)
    }
}