package com.vipcodeerror.brandup.ui.main.adapter

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.data.model.PlanDataModel

class PlanSelectorAdapter(var context: Context, var pDataList : MutableList<PlanDataModel>) : RecyclerView.Adapter<PlanSelectorAdapter.MyViewHolder>() {

    var listOfColor  = mutableListOf<Int>(R.color.basic_color,R.color.silver_color,R.color.gold_color)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanSelectorAdapter.MyViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.plan_adapter_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlanSelectorAdapter.MyViewHolder, position: Int) {
        holder.dLimit.text = pDataList[position].downloadLimit + " / per day"
        holder.sLimit.text = pDataList[position].shareLimit + " / per day"
        holder.bCardLimit.text = pDataList[position].bCardLimit
        holder.dayLimit.text = pDataList[position].dayLimit
        holder.price.text = "Rs " + pDataList[position].planPrice
        holder.planTitle.text = pDataList[position].planName
        holder.cardViewPlan.setBackgroundResource(listOfColor[position])
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
        var planTitle = itemView.findViewById<TextView>(R.id.plan_title)
        var cardViewPlan = itemView.findViewById<LinearLayout>(R.id.card_view_plan)
    }

}