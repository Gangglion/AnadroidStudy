package com.glion.testgather.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.glion.testgather.R
import com.glion.testgather.databinding.ItemTempRecyclerBinding

class TempRecyclerViewAdapter(private val itemList: ArrayList<Int>, private val mContext: Context) : RecyclerView.Adapter<TempRecyclerViewAdapter.ViewHolder>() {
    private lateinit var mBinding: ItemTempRecyclerBinding
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val title: AppCompatTextView = mBinding.tvTitle
        val content: AppCompatTextView = mBinding.tvContent
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mBinding = ItemTempRecyclerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(mBinding.root)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = mContext.getString(R.string.temp_title).format(position+1)
        holder.content.text = mContext.getString(R.string.temp_content).format(position+1)

        holder.content.setOnClickListener {
            holder.content.setBackgroundColor(mContext.getColor(R.color.yellow))
            Toast.makeText(mContext, "항목 : ${position+1} 선택", Toast.LENGTH_SHORT).show()
        }
    }
}