package com.example.mvvmactivity.ui.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.DiffUtil.DiffResult
import androidx.recyclerview.widget.RecyclerView
import com.example.mvvmactivity.R
import com.example.mvvmactivity.databinding.ItemTempBinding
import com.example.mvvmactivity.ui.recyclerview.model.TempData


class TempAdapter(
    private var dataSet: List<TempData>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<TempAdapter.ViewHolder>() {

    private lateinit var mBinding: ItemTempBinding
    class ViewHolder(val binding: ItemTempBinding): RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        mBinding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.context), R.layout.item_temp, viewGroup, false)

        return ViewHolder(mBinding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.binding.item = dataSet[position]
        viewHolder.binding.listener = listener
    }

    override fun getItemCount() = dataSet.size

    fun updateData(newData: List<TempData>){
        val diffResult = DiffUtil.calculateDiff(TempDataDiffCallback(dataSet, newData))
        dataSet = newData
        diffResult.dispatchUpdatesTo(this)
    }

    // DiffCallback 으로 리사이클러뷰 깜빡거림 제거
    class TempDataDiffCallback(
        private val oldData: List<TempData>,
        private val newData: List<TempData>
    ) : DiffUtil.Callback(){
        override fun getOldListSize(): Int = oldData.size

        override fun getNewListSize(): Int = newData.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldData[oldItemPosition].title == newData[newItemPosition].title

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
            oldData[oldItemPosition] == newData[newItemPosition]
    }
}
