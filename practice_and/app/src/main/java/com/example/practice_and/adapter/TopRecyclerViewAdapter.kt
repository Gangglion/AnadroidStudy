package com.example.practice_and.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practice_and.R
import com.example.practice_and.data.ExampleList

class TopRecyclerViewAdapter(private val itemList: ArrayList<ExampleList>, val mContext: Context, private val mListener: OnItemClick): RecyclerView.Adapter<TopRecyclerViewAdapter.ViewHolder>() {
    private var oldPosition = -1
    // 클릭 인터페이스 생성 - 클릭 처리에 대해 MainActivity에서 구현할 수 있음.
    // 아이템 클릭 이벤트에 생성한 OnItemClick을 넣어주어 아이템 클릭 이벤트시에 어댑터에서 수행해야 하는 기능 실행, 이후 Main에서 기능 수행할 수 있음
    // 순서 : ItemClick -> holder.setOnClickListener 실행 -> mListener.click() 실행(메인에서)
    interface OnItemClick{
        fun click(pos: Int)
    }

    // 뷰 홀더에서 아이템 기억
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val title: TextView = itemView.findViewById(R.id.tv_title)
        val content: TextView = itemView.findViewById(R.id.tv_content)

        // 실질적으로 데이터를 Bind 하는 부분
        fun bind(title: String, content: String, isClick: Boolean){
            this.title.text = title
            this.content.text = content
            if(isClick){
                this.content.setBackgroundColor(mContext.getColor(R.color.line_color))
            } else{
                this.content.background = null
            }
        }
    }

    // 화면에 띄울 필요한 뷰 홀더 객체 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_temp_recycler, parent, false)
        return ViewHolder(view)
    }

    // 어댑터 생성시 제일 먼저 실행됨 - 아이템의 총 개수 반환
    override fun getItemCount(): Int {
        return itemList.size
    }
    // 각 위치의 고유한 ID값을 반환
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    // 뷰 홀더에서 기억한 아이템에 데이터만 Bind
    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // 본래 onBindViewHolder에서 데이터가 바인드 되어야 하지만, 변화를 줄 것이기 때문에 ViewHolder에 데이터 할당 양도
        if(oldPosition == position){
            holder.bind(mContext.getString(R.string.temp_title).format(position+1), mContext.getString(R.string.temp_content).format(position+1), true)
        } else{
            holder.bind(mContext.getString(R.string.temp_title).format(position+1), mContext.getString(R.string.temp_content).format(position+1), false)
        }

        holder.content.setOnClickListener {
            // 원래는 oldPosition = position을 사용했지만 warning 이 나옴
            // position은 지속적으로 변하는 값이기 때문에 고정된 값처럼 다른 값에 할당하지 말라는 경고 나옴. 사실상 position은 계속 변하고, click 했을때의 포지션을 저장하는거라 상관은 없지만,
            // 그래도 warning 이 나오기 때문에 adapterPosition을 사용하자.
            oldPosition = holder.adapterPosition
            // mListener.click(position)
            notifyDataSetChanged() // 데이터가 많아지거나, 데이터를 Bind할때 네트워크 통신이 필요한 부분이 있다면 갱신이 어려워 질 수 있음 / 왜? 전체를 갱신하기 때문
        }
    }
}