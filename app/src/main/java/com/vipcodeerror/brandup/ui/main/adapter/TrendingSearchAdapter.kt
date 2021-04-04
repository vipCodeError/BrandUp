package com.vipcodeerror.brandup.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.data.model.TrendingDataModel
import com.vipcodeerror.brandup.ui.main.view.activity.FrameTemplateSelectorActivity

class TrendingSearchAdapter(var context : Context, var strList: MutableList<TrendingDataModel>) : RecyclerView.Adapter<TrendingSearchAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): TrendingSearchAdapter.MyViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.treding_search_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrendingSearchAdapter.MyViewHolder, position: Int) {
        if (strList[position].isSubcat == "0"){
            holder.trendingTitleText.text = strList[position].catName
        }else {
            holder.trendingTitleText.text = strList[position].subName
        }

        holder.trendLay.setOnClickListener {

            val intent = Intent(context, FrameTemplateSelectorActivity::class.java)
            intent.putExtra("is_sub_shown", strList[position].isSubcat)
            intent.putExtra("cat_id", strList[position].catId)
            intent.putExtra("sub_id", strList[position].subId)
            context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        return strList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var trendingTitleText = itemView.findViewById<TextView>(R.id.trend_title)
        var trendLay = itemView.findViewById<LinearLayout>(R.id.trend_lay)
    }
}