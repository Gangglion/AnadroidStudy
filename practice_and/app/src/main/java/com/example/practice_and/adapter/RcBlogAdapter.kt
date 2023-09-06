package com.example.practice_and.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.practice_and.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RcBlogAdapter(data: ArrayList<String>, context: Context) : RecyclerView.Adapter<RcBlogAdapter.ViewHolder>() {
    private var mResourceData: ArrayList<String>
    private val mContext: Context
    init{
        mResourceData = data
        mContext = context
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val ivType: AppCompatImageView = itemView.findViewById(R.id.iv_type)
        val tvName: AppCompatTextView = itemView.findViewById(R.id.tv_file_name)
        val tvDate: AppCompatTextView = itemView.findViewById(R.id.tv_date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder { // 만들어둔 아이템 레이아웃 infalte 해서 ViewHolder에 전달
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_rcblog, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mResourceData.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when((Math.random()*3).toInt()){
            0-> holder.ivType.setImageDrawable(AppCompatResources.getDrawable(mContext, R.drawable.rc_image))
            1-> holder.ivType.setImageDrawable(AppCompatResources.getDrawable(mContext, R.drawable.rc_file))
            2-> holder.ivType.setImageDrawable(AppCompatResources.getDrawable(mContext, R.drawable.rc_video))
        }
        holder.tvName.text = mResourceData[position]
        val localDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
        holder.tvDate.text = localDateTime
    }

    fun setData(data: ArrayList<String>){
        mResourceData = data
        notifyDataSetChanged()
    }
}