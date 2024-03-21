package com.glion.scheduletest

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.util.Log
import android.widget.ScrollView

class CustomScrollView : ScrollView {
    private var mContext: Context
    constructor(context: Context?) : super(context!!) {
        mContext = context
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs) {
        mContext = context
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context!!, attrs, defStyleAttr) {
        mContext = context
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val mainActivity = this.context as MainActivity
        mainActivity.scrollToNowTime()
        mainActivity.isScroll = false
    }
}