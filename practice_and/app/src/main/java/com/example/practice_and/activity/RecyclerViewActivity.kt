package com.example.practice_and.activity

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.practice_and.App
import com.example.practice_and.R
import com.example.practice_and.adapter.BottomRecyclerViewAdapter
import com.example.practice_and.adapter.TopRecyclerViewAdapter
import com.example.practice_and.data.ExampleList

class RecyclerViewActivity : AppCompatActivity() {
    private lateinit var mRecyclerViewTop: RecyclerView
    private lateinit var mRecyclerViewBottom: RecyclerView
    private lateinit var mTopItemList: ArrayList<ExampleList>
    private lateinit var mBottomItemList: ArrayList<ExampleList>
    private lateinit var mContext: Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        mContext = this
        mRecyclerViewTop = findViewById(R.id.rv_temp)
        mRecyclerViewBottom = findViewById(R.id.rv_temp2)
        // 리사이클러뷰 준비
        mRecyclerViewTop.setHasFixedSize(true)
        mRecyclerViewTop.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        // 아이템 준비
        mTopItemList = ArrayList()
        for(i:Int in 1..100)
            mTopItemList.add(ExampleList(i,false))

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
            mBottomItemList.add(ExampleList(i,false))
        }
        mRecyclerViewBottom.adapter = BottomRecyclerViewAdapter(mBottomItemList, this, object: BottomRecyclerViewAdapter.OnItemClickListener{
            override fun onClick(position: Int) {
                // 리사이클러뷰에서 아이템의 포지션을 사용할 수 있음. 지금은 content.setOnClickListener에 달아서 position을 넘겨주기 때문에 클릭한 위치의 포지션이 넘어옴
                App.instance.makeToast("선택한 항목의 포지션 : $position")
            }
        })
    }
}