package com.vipcodeerror.brandup.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.data.model.BusinessDetailsModel

class DownloadedAdapter(var listData: MutableList<BusinessDetailsModel>) : RecyclerView.Adapter<DownloadedAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadedAdapter.MyViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.downloaded_adapter_lay, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: DownloadedAdapter.MyViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return listData.size
    }
}