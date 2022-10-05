package com.example.marketcarrot

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.marketcarrot.databinding.ItemDataBinding

// 리스트 데이터를 넘겨받아야 한다.
class DataRVAdapter(private var dataList: MutableList<Data>): RecyclerView.Adapter<DataRVAdapter.ViewHolder>() {
    // inner class로 viewHolder 정의
    inner class ViewHolder(private val viewBinding: ItemDataBinding) : RecyclerView.ViewHolder(viewBinding.root) {
        // onBindViewHolder의 역할을 대신한다. 아래에서 선언해준다.

        fun bind(data: Data) {
            viewBinding.ivImage.setImageResource(data.image)
            viewBinding.tvTitle.text = data.text
            viewBinding.tvInfo.text = data.text
            viewBinding.tvPrice.text = data.text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinding = ItemDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(viewBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int = dataList.size


}