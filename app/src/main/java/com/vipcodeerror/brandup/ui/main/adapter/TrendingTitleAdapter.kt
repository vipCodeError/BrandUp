package com.vipcodeerror.brandup.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vipcodeerror.brandup.R

class TrendingTitleAdapter(var strList: MutableList<String>) : RecyclerView.Adapter<TrendingTitleAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrendingTitleAdapter.MyViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.treding_title_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrendingTitleAdapter.MyViewHolder, position: Int) {
        holder.trendingTitleText.text = strList[position]
    }

    override fun getItemCount(): Int {
        return strList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var trendingTitleText = itemView.findViewById<TextView>(R.id.trend_title_txt)
    }
}