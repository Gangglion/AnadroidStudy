package com.example.practice_and.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.NO_POSITION
import com.example.practice_and.R
import com.example.practice_and.databinding.ItemTempRecyclerBinding

class BottomRecyclerViewAdapter(private val itemList: ArrayList<ExampleData>, private val mContext: Context, private val listener: OnItemClickListener) : RecyclerView.Adapter<BottomRecyclerViewAdapter.ViewHolder>() {
    interface OnItemClickListener{
        fun onClick(position: Int)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private var mBinding : ItemTempRecyclerBinding? = null
        init{
            mBinding = ItemTempRecyclerBinding.bind(itemView)
        }
        val title = mBinding!!.tvTitle
        val content = mBinding!!.tvContent
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(mContext).inflate(R.layout.item_temp_recycler, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = itemList[position]
        holder.title.text = mContext.getString(R.string.temp_title, item.value)
        holder.content.text = mContext.getString(R.string.temp_content, item.value)

        // 아이템이 체크되었는지 여부에 따라 배경색 설정
        if(item.isChecked){
            holder.content.setBackgroundColor(mContext.getColor(R.color.line_color))
        } else{
            holder.content.setBackgroundColor(mContext.getColor(android.R.color.transparent))
        }

        holder.content.setOnClickListener {
            listener.onClick(position)
            var oldPosition = -1
            if(position != NO_POSITION){
                for((index, data) in itemList.withIndex()){
                    // 이전에 선택한 항목이라면
                    if(data.isChecked){
                        data.isChecked = false // 선택해제
                        oldPosition = index // oldPosition에 이전에 선택한 항목의 index 넣어줌
                    }
                    if(position == index){
                        data.isChecked = true
                    }
                }
                notifyItemChanged(position) // pos부분의 아이템이 바뀌었다고 알려줌. pos부분의 onBindViewHolder 재 실행.
                notifyItemChanged(oldPosition) // oldPosition부분의 아이템이 바뀌었다고 알려줌. oldPosition부분의 onBindViewHolder 재 실행.
            }
        }
    }
}