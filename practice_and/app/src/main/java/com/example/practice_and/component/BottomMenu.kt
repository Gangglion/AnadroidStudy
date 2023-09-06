package com.example.practice_and.component

import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentManager
import com.example.practice_and.R
import com.example.practice_and.fragment.BarChartFragment
import com.example.practice_and.fragment.CandleChartFragment
import com.example.practice_and.fragment.CombineChartFragment
import com.example.practice_and.fragment.LineChartFragment

@RequiresApi(Build.VERSION_CODES.O)
class BottomMenu : ConstraintLayout {

    private var mContext: Context? = null
    private var mVisibleMenu: Int = 0

    private var mIvBarChart: ImageView? = null
    private var mIvLineChart: ImageView? = null
    private var mIvCandleChart: ImageView? = null
    private var mIvCombineChart: ImageView? = null
    private var mFragmentManager: FragmentManager? = null

    @RequiresApi(Build.VERSION_CODES.S)
    constructor(context: Context) : super(context) {
        mContext = context
        init()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        mContext = context
        init()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        mContext = context
        init()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun init() {
        val inflaterService: String = Context.LAYOUT_INFLATER_SERVICE
        val layoutInflater = context.getSystemService(inflaterService) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.layout_bottom_menu, this, false)
        addView(view)

        mIvBarChart = findViewById(R.id.iv_barchart)
        mIvLineChart = findViewById(R.id.iv_linechart)
        mIvCandleChart = findViewById(R.id.iv_candlechart)
        mIvCombineChart = findViewById(R.id.iv_combinechart)

        findViewById<LinearLayout>(R.id.ll_barchart).setOnClickListener {
            changeBottomMenu(0)
        }
        findViewById<LinearLayout>(R.id.ll_linechart).setOnClickListener {
            changeBottomMenu(1)
        }
        findViewById<LinearLayout>(R.id.ll_candlechart).setOnClickListener {
            changeBottomMenu(2)
        }
        findViewById<LinearLayout>(R.id.ll_combinechart).setOnClickListener {
            changeBottomMenu(3)
        }
    }
    @RequiresApi(Build.VERSION_CODES.S)
    private fun changeBottomMenu(clickMenu: Int) {
        if (clickMenu != mVisibleMenu) {
            mIvBarChart!!.setImageResource(R.drawable.baseline_bar_chart_off)
            mIvLineChart!!.setImageResource(R.drawable.baseline_show_chart_off)
            mIvCandleChart!!.setImageResource(R.drawable.baseline_candlestick_chart_off)
            mIvCombineChart!!.setImageResource(R.drawable.baseline_combine_chart_off)
            mVisibleMenu = clickMenu
            when (clickMenu) {
                0 -> {
                    mIvBarChart!!.setImageResource(R.drawable.baseline_bar_chart_on)
                    mFragmentManager!!.beginTransaction().replace(R.id.fl_chart, BarChartFragment())
                        .commit()
                }
                1 -> {
                    mIvLineChart!!.setImageResource(R.drawable.baseline_show_chart_on)
                    mFragmentManager!!.beginTransaction().replace(
                        R.id.fl_chart,
                        LineChartFragment()
                    ).commit()
                }
                2 -> {
                    mIvCandleChart!!.setImageResource(R.drawable.baseline_candlestick_chart_on)
                    mFragmentManager!!.beginTransaction().replace(
                        R.id.fl_chart,
                        CandleChartFragment()
                    ).commit()
                }
                3 -> {
                    mIvCombineChart!!.setImageResource(R.drawable.baseline_combine_chart_on)
                    mFragmentManager!!.beginTransaction().replace(
                        R.id.fl_chart,
                        CombineChartFragment()
                    ).commit()
                }
            }
        }
    }

    fun setFragmentManager(fm: FragmentManager) {
        mFragmentManager = fm
    }
}