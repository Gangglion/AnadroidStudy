package com.example.practice_and.fragment

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.example.practice_and.R
import com.example.practice_and.activity.StepActivity
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener

@RequiresApi(Build.VERSION_CODES.O)
class BarChartFragment : Fragment(), OnChartValueSelectedListener, OnClickListener {
    private var mContext: Context? = null
    private var mBackPressedCallback: OnBackPressedCallback? = null
    private var mTvChartInfo: TextView? = null
    private var mBarChart: BarChart? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                val intent = Intent(mContext, StepActivity::class.java)
                intent.putExtra("recent", mTvChartInfo!!.text ?: "No Chart")
                activity!!.setResult(9001, intent)
                activity!!.finish()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, mBackPressedCallback!!)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val rootView = inflater.inflate(R.layout.fragment_bar_chart, container, false)
        mContext = context
        mTvChartInfo = rootView.findViewById(R.id.tv_chartinfo)
        rootView.findViewById<ImageView>(R.id.btn_refresh).setOnClickListener(this)
        // 차트 가져옴
        mBarChart = rootView.findViewById(R.id.mp_barchart)
        mBarChart?.setOnChartValueSelectedListener(this)
        mBarChart?.description?.isEnabled = false // 차트 오른쪽 아래 "Description label" text 활성/비활성 화 설정

        // BarChart 애니메이션 설정
        mBarChart?.animateY(500)
        mBarChart?.animateX(500)
        mBarChart?.isDragEnabled = true
        mBarChart?.setPinchZoom(false)
        mBarChart?.zoom(2f,0f,1f,1f)


        // x축
        val xAxis = mBarChart?.xAxis
        xAxis?.position = XAxis.XAxisPosition.BOTTOM // x축 이름이 나오는 위치 지정 -> Bottom
        xAxis?.setDrawGridLines(false) // x축 grid 노출 여부
        xAxis?.setLabelCount(10,true) // x축의 레이블 항목 수를 설정. force를 false로 두어 숫자가 고정되지 않도록 함 => 총 10개 항목에서 입력한 숫자만큼만 x축 레이블 항목이 나타나게 됨. 5면 2,4,6,8,10에 해당하는 항목만 나옴
        // xAxis?.granularity = 5f; // 설정한 값 마다 레이블 표시 - 이 부분 설정해도 레이블 항목수 조절 가능. setLabelCount와 이 속성 둘중에 하나만 사용해도 적용됨
        xAxis?.textColor = Color.WHITE // x축 레이블 텍스트 색상
        xAxis?.labelCount = 10
        // y축
        val yAxis = mBarChart?.axisLeft
        yAxis?.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART)
        yAxis?.setLabelCount(5,true)
        yAxis?.mAxisMinimum = 0f
        yAxis?.mAxisMaximum = 100f
        yAxis?.setDrawGridLines(true) // y축 grid 노출 여부
        yAxis?.granularity = 2f // 값 만큼 라인선 설정?
        yAxis?.textColor = Color.WHITE // y축 레이블 텍스트 색상
        yAxis?.axisMaximum = 10f // y축 최대값 고정
        yAxis?.axisMinimum = 0f // y축 최소값 고정

        // y축 오른쪽 비활성화
        val yAxisRight = mBarChart?.axisRight
        yAxisRight?.isEnabled = false // y축 오른쪽 숨기기

        // 범례
        val legend = mBarChart?.legend
        legend?.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT // 수평정렬 세팅
        legend?.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM // 수직정렬 세팅
        legend?.form = Legend.LegendForm.DEFAULT // 범례모양
        legend?.textColor = Color.WHITE // 범례 텍스트 색상

        mBarChart?.data = setData()

        mBarChart?.invalidate() // 차트가 새로고침되고, 위에 세팅한 데이터로 차트가 그려짐
        return rootView
    }

    override fun onDetach() {
        super.onDetach()
        mBackPressedCallback!!.remove()
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) { // 차트 항목을 클릭하면 동작
        Log.d("tmdguq", "Value Selected => x: ${e?.x}, y: ${e?.y}")
    }

    override fun onNothingSelected() {}

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btn_refresh->{ // 차트 새로고침
                Log.d("tmdguq", "RefreshBtn Click")
                // BarChart 애니메이션 설정
                mBarChart?.animateY(500)
                mBarChart?.animateX(500)
                mBarChart?.data = setData()
                mBarChart?.notifyDataSetChanged()
                mBarChart?.invalidate()
            }
        }
    }

    private fun setData() : BarData{
        // 데이터 세팅 - BarEntry 타입의 데이터 ArrayList에 삽입. 이걸 BarDataSet에 인자로 전달, 함꼐 전달되는 label은 하단 범례 제목. BarDataSet은 IBarDataSet타입의 ArrayList. 마지막으로 BarData의 인자로 dataset을 넣은 data객체를 차트객체의 데이터에 넣어줌
        val values = ArrayList<BarEntry>()
        for(i: Int in 1..20){
            val randomVal = Math.random() * 9
            values.add(BarEntry(i.toFloat(),randomVal.toFloat()))
        }
        val set = BarDataSet(values, "It is Legend")
        set.color = ContextCompat.getColor(mContext!!, R.color.orange_chart) // 그냥 R.color.orange_chart 하면 리소스 아이디가 전달되므로, 실제 색상을 가져오기 위해 이와 같이 사용함
        val dataSets = ArrayList<IBarDataSet>()
        dataSets.add(set)
        val data = BarData(dataSets)
        data.setValueTextColor(Color.WHITE) // 데이터 값에 대한 색상 설정
        data.setDrawValues(true) // 데이터 값 표기 활성/비활성화 설정

        return data
    }
}