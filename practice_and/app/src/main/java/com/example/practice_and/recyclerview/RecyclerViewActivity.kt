package com.example.practice_and.recyclerview

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practice_and.App
import com.example.practice_and.R

class RecyclerViewActivity : AppCompatActivity() {
    private lateinit var mRecyclerViewTop: RecyclerView
    private lateinit var mRecyclerViewBottom: RecyclerView
    private lateinit var mRecyclerViewBasic: RecyclerView
    private lateinit var mTopItemList: ArrayList<ExampleData>
    private lateinit var mBottomItemList: ArrayList<ExampleData>
    private lateinit var mBasicItemList: ArrayList<ExampleData>
    private lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        mContext = this
        mRecyclerViewBasic = findViewById(R.id.rc_basic)
        mRecyclerViewTop = findViewById(R.id.rv_temp)
        mRecyclerViewBottom = findViewById(R.id.rv_temp2)

        // 리사이클러뷰 준비
        mRecyclerViewBasic.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        mBasicItemList = ArrayList()
        for(i: Int in 1..100){
            mBasicItemList.add(ExampleData(i, false))
        }
        mRecyclerViewBasic.adapter = BasicRecyclerViewAdapter(mContext, mBasicItemList, object : BasicRecyclerViewAdapter.OnItemClick{
            override fun onClick(pos: Int) {
                Toast.makeText(mContext, "항목 ${pos+1} 선택", Toast.LENGTH_SHORT).show()
            }
        })

        // 리사이클러뷰 준비
        mRecyclerViewTop.setHasFixedSize(true)
        mRecyclerViewTop.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        // 아이템 준비
        mTopItemList = ArrayList()
        for(i:Int in 1..100)
            mTopItemList.add(ExampleData(i,false))

        mRecyclerViewTop.adapter = TopRecyclerViewAdapter(mTopItemList, this, object: TopRecyclerViewAdapter.OnItemClick{
            @SuppressLint("NotifyDataSetChanged")
            override fun click(pos: Int) {
                Toast.makeText(mContext, "항목 ${pos+1} 선택", Toast.LENGTH_SHORT).show()
            }
        })

        mRecyclerViewBottom.setHasFixedSize(true)
        mRecyclerViewBottom.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        mBottomItemList = ArrayList()
        for(i: Int in 1..100){
            mBottomItemList.add(ExampleData(i,false))
        }
        mRecyclerViewBottom.adapter = BottomRecyclerViewAdapter(mBottomItemList, this, object: BottomRecyclerViewAdapter.OnItemClickListener{
            override fun onClick(position: Int) {
                // 리사이클러뷰에서 아이템의 포지션을 사용할 수 있음. 지금은 content.setOnClickListener에 달아서 position을 넘겨주기 때문에 클릭한 위치의 포지션이 넘어옴
                App.instance.makeToast("선택한 항목의 포지션 : $position")
            }
        })
    }
}