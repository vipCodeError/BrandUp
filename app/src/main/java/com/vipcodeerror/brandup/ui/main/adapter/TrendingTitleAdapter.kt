package com.vipcodeerror.brandup.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.data.model.TrendingDataModel
import com.vipcodeerror.brandup.ui.main.view.activity.FrameTemplateSelectorActivity

class TrendingTitleAdapter(var context: Context, var strList: MutableList<TrendingDataModel>) : RecyclerView.Adapter<TrendingTitleAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TrendingTitleAdapter.MyViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.treding_title_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: TrendingTitleAdapter.MyViewHolder, position: Int) {
        if (strList[position].isSubcat == "0"){
            holder.trendingTitleText.text = strList[position].catName
        }else {
            holder.trendingTitleText.text = strList[position].subName
        }

        holder.relativeLayout.setOnClickListener {
            val intent = Intent(context, FrameTemplateSelectorActivity::class.java)
            intent.putExtra("is_sub_shown", "0")
            intent.putExtra("cat_id", strList[position].catId)
            intent.putExtra("sub_id", "0")
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return strList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var trendingTitleText = itemView.findViewById<TextView>(R.id.trend_title_txt)
        var relativeLayout = itemView.findViewById<RelativeLayout>(R.id.ln_lay)
    }
}