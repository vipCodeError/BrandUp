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
import com.vipcodeerror.brandup.data.model.SearchDataModel
import com.vipcodeerror.brandup.data.model.SearchDataResponse
import com.vipcodeerror.brandup.ui.main.view.activity.FrameTemplateSelectorActivity

class SearchStringAdapter(var context : Context, var dData : MutableList<SearchDataModel>) : RecyclerView.Adapter<SearchStringAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchStringAdapter.MyViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.search_str_lay, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchStringAdapter.MyViewHolder, position: Int) {
        holder.searchedTitle.text = dData[position].catName
        holder.linearLayout.setOnClickListener {
            val intent = Intent(context, FrameTemplateSelectorActivity::class.java)
            intent.putExtra("is_sub_shown", "0")
            intent.putExtra("cat_id", dData[position].id)
            intent.putExtra("sub_id", "0")
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return dData.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var searchedTitle = itemView.findViewById<TextView>(R.id.searched_title)
        var linearLayout = itemView.findViewById<LinearLayout>(R.id.ln_lay)
    }

}