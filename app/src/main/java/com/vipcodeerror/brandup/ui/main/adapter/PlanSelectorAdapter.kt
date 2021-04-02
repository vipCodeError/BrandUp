package com.vipcodeerror.brandup.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.data.model.PlanDataModel

class PlanSelectorAdapter(var context: Context, var pDataList : MutableList<PlanDataModel>) : RecyclerView.Adapter<PlanSelectorAdapter.MyViewHolder>() {

    var listOfColor  = mutableListOf<String>("#09438f","#01295d", "#000812")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanSelectorAdapter.MyViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.plan_adapter_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlanSelectorAdapter.MyViewHolder, position: Int) {
        holder.dLimit.text = pDataList[position].downloadLimit
        holder.sLimit.text = pDataList[position].shareLimit
        holder.bCardLimit.text = pDataList[position].bCardLimit
        holder.dayLimit.text = pDataList[position].dayLimit
        holder.price.text = pDataList[position].planPrice
    }

    override fun getItemCount(): Int {
        return pDataList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var dLimit = itemView.findViewById<TextView>(R.id.ttl_download)
        var sLimit = itemView.findViewById<TextView>(R.id.ttl_share)
        var dayLimit = itemView.findViewById<TextView>(R.id.ttl_days)
        var bCardLimit = itemView.findViewById<TextView>(R.id.ttl_bcard)
        var price = itemView.findViewById<TextView>(R.id.ttl_price)
    }
}