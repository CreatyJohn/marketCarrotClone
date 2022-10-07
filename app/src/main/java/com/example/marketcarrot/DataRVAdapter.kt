package com.example.marketcarrot

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView

// 리스트 데이터를 넘겨받아야 한다.
class DataRVAdapter(val dataList: ArrayList<Data>, val checkList: ArrayList<checkboxData>):
    RecyclerView.Adapter<DataRVAdapter.DataRVHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataRVHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_data,
            parent, false)
        return DataRVHolder(itemView)
    }

    override fun onBindViewHolder(holder: DataRVHolder, position: Int) {

        holder.bind(dataList[position], position)

    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    private var ck = 0

    inner class DataRVHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    {

        var chkBox : CheckBox = itemView.findViewById<CheckBox>(R.id.cb_homework)
        val ivImage : ImageView = itemView.findViewById<ImageView>(R.id.iv_Image)
        val tvTitle : TextView = itemView.findViewById<TextView>(R.id.tv_Title)
        val tvInfo : TextView = itemView.findViewById<TextView>(R.id.tv_Info)
        val tvPrice : TextView = itemView.findViewById<TextView>(R.id.tv_Price)

        fun bind(data: Data, num: Int) {

            ivImage.setImageResource(dataList[position].Image)
            tvTitle.text = dataList[position].Title
            tvInfo.text = dataList[position].Info
            tvPrice.text = dataList[position].Price

            if (ck == 1) {
                chkBox.visibility = View.VISIBLE
            } else
                chkBox.visibility = View.GONE

            if (num >= checkList.size)
                checkList.add(num, checkboxData(data.Image, false))

            chkBox.setOnClickListener {
                if (chkBox.isChecked) {
                    checkList[num].Checked = true
                } else {
                    checkList[num].Checked = false
                }
            }
        }

    }
}
