package com.glion.scheduletest.experiment

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.glion.scheduletest.R

// NotUse
class ScheduleTable : LinearLayout {
    private lateinit var mTime00: LinearLayout
    private lateinit var mTime01: LinearLayout
    private lateinit var mTime02: LinearLayout
    private lateinit var mTime03: LinearLayout
    private lateinit var mTime04: LinearLayout
    private lateinit var mTime05: LinearLayout
    private lateinit var mTime06: LinearLayout
    private lateinit var mTime07: LinearLayout
    private lateinit var mTime08: LinearLayout
    private lateinit var mTime09: LinearLayout
    private lateinit var mTime10: LinearLayout
    private lateinit var mTime11: LinearLayout
    private lateinit var mTime12: LinearLayout
    private lateinit var mTime13: LinearLayout
    private lateinit var mTime14: LinearLayout
    private lateinit var mTime15: LinearLayout
    private lateinit var mTime16: LinearLayout
    private lateinit var mTime17: LinearLayout
    private lateinit var mTime18: LinearLayout
    private lateinit var mTime19: LinearLayout
    private lateinit var mTime20: LinearLayout
    private lateinit var mTime21: LinearLayout
    private lateinit var mTime22: LinearLayout
    private lateinit var mTime23: LinearLayout

    private val mTimeTableList: MutableList<LinearLayout> = mutableListOf()

    val mTimeTableMap: MutableMap<String, LinearLayout> = mutableMapOf()

    private lateinit var mContext: Context
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

    private fun initView(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int? = null){
        val inflaterService: String = Context.LAYOUT_INFLATER_SERVICE
        val layoutInflater = context.getSystemService(inflaterService) as LayoutInflater
        val view: View = layoutInflater.inflate(R.layout.layout_schedule_table, this, false)
        addView(view)

        mTime00 = view.findViewById(R.id.time_00)
        mTime01 = view.findViewById(R.id.time_01)
        mTime02 = view.findViewById(R.id.time_02)
        mTime03 = view.findViewById(R.id.time_03)
        mTime04 = view.findViewById(R.id.time_04)
        mTime05 = view.findViewById(R.id.time_05)
        mTime06 = view.findViewById(R.id.time_06)
        mTime07 = view.findViewById(R.id.time_07)
        mTime08 = view.findViewById(R.id.time_08)
        mTime09 = view.findViewById(R.id.time_09)
        mTime10 = view.findViewById(R.id.time_10)
        mTime11 = view.findViewById(R.id.time_11)
        mTime12 = view.findViewById(R.id.time_12)
        mTime13 = view.findViewById(R.id.time_13)
        mTime14 = view.findViewById(R.id.time_14)
        mTime15 = view.findViewById(R.id.time_15)
        mTime16 = view.findViewById(R.id.time_16)
        mTime17 = view.findViewById(R.id.time_17)
        mTime18 = view.findViewById(R.id.time_18)
        mTime19 = view.findViewById(R.id.time_19)
        mTime20 = view.findViewById(R.id.time_20)
        mTime21 = view.findViewById(R.id.time_21)
        mTime22 = view.findViewById(R.id.time_22)
        mTime23 = view.findViewById(R.id.time_23)
        mTimeTableList.apply {
            add(mTime00)
            add(mTime01)
            add(mTime02)
            add(mTime03)
            add(mTime04)
            add(mTime05)
            add(mTime06)
            add(mTime07)
            add(mTime08)
            add(mTime09)
            add(mTime10)
            add(mTime11)
            add(mTime12)
            add(mTime13)
            add(mTime14)
            add(mTime15)
            add(mTime16)
            add(mTime17)
            add(mTime18)
            add(mTime19)
            add(mTime20)
            add(mTime21)
            add(mTime22)
            add(mTime23)
        }
    }
    fun initTable(start: String, end: String){
        val startTime = start.split(":")[0].toInt()
        val endTime = end.split(":")[0].toInt()

        val lange = if(startTime < endTime){
            endTime - startTime + 1
        } else{
            (endTime + 24) - startTime + 1
        }
        for(idx in 0 until lange){
            mTimeTableList[idx].visibility = View.VISIBLE
            mTimeTableList[idx].findViewById<TextView>(R.id.tv_time).text = makeTimeString(startTime + idx)
            mTimeTableMap["%02d".format(startTime + idx)] = mTimeTableList[idx]
        }
    }

    fun initEvent(){

    }
    private fun makeTimeString(time: Int): String{
        return if(time < 24)
            "%02d:00".format(time)
        else
            "%02d:00".format(time-24)
    }

    fun putSchedule(startTime: String, endTime: String, type: String, content: String){
        val startTimeHour = startTime.split(":")[0]
        val startTimeMin = startTime.split(":")[1]
        val endTimeHour = endTime.split(":")[0]
        val endTimeMin = endTime.split(":")[1]

    }

    fun changeColor(){

    }
}