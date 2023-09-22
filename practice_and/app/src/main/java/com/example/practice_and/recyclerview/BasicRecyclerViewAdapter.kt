package com.example.practice_and.recyclerview

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practice_and.R

class BasicRecyclerViewAdapter(private val context: Context, private val itemList: ArrayList<ExampleData>, private val mListener: OnItemClick) : RecyclerView.Adapter<BasicRecyclerViewAdapter.ViewHolder>(){

    private var oldPosition = -1
    interface OnItemClick{
        fun onClick(pos: Int)
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val title: AppCompatTextView = itemView.findViewById(R.id.tv_title)
        val content: AppCompatTextView = itemView.findViewById(R.id.tv_content)
        fun setColor(pos: Int){
            if(itemList[pos].isChecked){
                content.setBackgroundColor(context.getColor(R.color.line_color))
            } else{
                content.background = null
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("shhan", "onCreateViewHolder")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_temp_recycler, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = context.getString(R.string.temp_title).format(itemList[position].value)
        holder.content.text = context.getString(R.string.temp_content).format(itemList[position].value)
        holder.setColor(position)

        holder.content.setOnClickListener {
            itemList[position].isChecked = true
            if(oldPosition != -1){ // oldPosition 초기값 -1일때 에러 방지
                if(position != oldPosition){ // 다른거 클릭했을때
                    itemList[oldPosition].isChecked = false // 이전 위치의 클릭 해제
                }
            }
            notifyItemChanged(oldPosition)
            notifyItemChanged(position)
            oldPosition = holder.adapterPosition // 이전 클릭위치에 현재 클릭 위치 넣어줌
        }
    }
}