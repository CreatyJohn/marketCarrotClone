package com.example.marketcarrot

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView

/** 리스트 데이터를 넘겨받아야 한다. */
class DataRVAdapter(private var dataList: MutableList<Data>): RecyclerView.Adapter<DataRVAdapter.ListItemViewHolder>() {

    /** inner class로 viewHolder 정의 */
    inner class ListItemViewHolder(itemView: View?): RecyclerView.ViewHolder(itemView!!) {

        /** ItemView */
        var ivImage : ImageView = itemView!!.findViewById(R.id.iv_Image)
        var tvTitle : TextView = itemView!!.findViewById(R.id.tv_Title)
        var tvInfo : TextView = itemView!!.findViewById(R.id.tv_Info)
        var tvPrice : TextView = itemView!!.findViewById(R.id.tv_Price)

        /** onBindViewHolder의 역할을 대신한다.*/
        fun bind(data: Data) {
            ivImage.setImageResource(data.Image)
            tvTitle.text = data.Title
            tvInfo.text = data.Info
            tvPrice.text = data.Price
        }
    }

    /** ViewHolder에게 item을 보여줄 View로 쓰일 item_data_list.xml를 넘기면서 ViewHolder 생성 */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_data, parent, false)
        return ListItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.count()
    }

    /** ViewHolder의 bind 메소드를 호출한다. */
    override fun onBindViewHolder(holder: DataRVAdapter.ListItemViewHolder, position: Int) {
        Log.d("ListAdapter", "===== ===== onBindViewHolder ===== =====")
        holder.bind(dataList[position])
    }

    // 리사이클러뷰 갱신처리

    var mPosition = 0

    fun getPosition(): Int {
        return mPosition
    }

    fun setPosition(position: Int) {
        mPosition = position
    }

    fun additem(data: Data) {
        dataList.add(data)
        notifyDataSetChanged()
    }

    fun removeItem(position: Int) {
        if(position > 0) {
            dataList.removeAt(position)
            notifyItemRemoved(position)
            notifyDataSetChanged()
        }
    }

}
