package com.example.practice_and.tree_map

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.example.practice_and.R

class GridItem  : LinearLayoutCompat{
    private lateinit var mContext: Context
    var row = 0
    var column = 0

    private lateinit var mLlRoot: LinearLayoutCompat
    private lateinit var mTvTitle: AppCompatTextView
    private lateinit var mTvValue: AppCompatTextView

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
        val layoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.layout_grid_item, this, false)
        view.apply{
            mLlRoot = findViewById(R.id.ll_root)
            mTvTitle = findViewById(R.id.tv_title)
            mTvValue = findViewById(R.id.tv_value)
        }
        addView(view)
    }

    fun setValue(title: String, content: String){
        mTvTitle.text = title
        mTvValue.text = content
    }
}