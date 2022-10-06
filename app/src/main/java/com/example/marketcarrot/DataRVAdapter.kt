package com.example.marketcarrot

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView

// 리스트 데이터를 넘겨받아야 한다.
class DataRVAdapter(private val dataList: ArrayList<Data>): RecyclerView.Adapter<DataRVAdapter.DataRVHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataRVHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_data,
            parent, false)
        return DataRVHolder(itemView)
    }

    override fun onBindViewHolder(holder: DataRVHolder, position: Int) {
        val currentItem = dataList[position]
        holder.ivImage.setImageResource(currentItem.Image)
        holder.tvTitle.text = currentItem.Title
        holder.tvInfo.text = currentItem.Info
        holder.tvPrice.text = currentItem.Price
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class DataRVHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {
        val ivImage : ImageView = itemView.findViewById(R.id.iv_Image)
        val tvTitle : TextView = itemView.findViewById(R.id.tv_Title)
        val tvInfo : TextView = itemView.findViewById(R.id.tv_Info)
        val tvPrice : TextView = itemView.findViewById(R.id.tv_Price)
    }

}