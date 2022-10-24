package com.example.marketcarrot

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kakao.sdk.common.KakaoSdk.init

/** 리스트 데이터를 넘겨받아야 한다. */
class AddressDataRVAdapter(private val context: Context) : RecyclerView.Adapter<AddressDataRVAdapter.ViewHolder>() {

    var datas = mutableListOf<AddressData>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.address_data,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = datas.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(datas[position])
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val tvAddress: Button = itemView.findViewById(R.id.btn_address)

        fun bind(item: AddressData) {
            tvAddress.text = item.Address


        }
    }

    fun removeItem(position: Int) {
        if(position > 0) {
            datas.removeAt(position)
            notifyItemRemoved(position)
            notifyDataSetChanged()
        }
    }
}
