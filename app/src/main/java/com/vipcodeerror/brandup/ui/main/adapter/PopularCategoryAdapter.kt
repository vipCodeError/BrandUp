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
import com.vipcodeerror.brandup.ui.main.view.activity.BrandLogoEdit

class PopularCategoryAdapter(var context : Context, var poplularCatList: MutableList<String>) : RecyclerView.Adapter<PopularCategoryAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularCategoryAdapter.MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.popular_cat_adapter_layout, parent, false))
    }

    override fun onBindViewHolder(holder: PopularCategoryAdapter.MyViewHolder, position: Int) {
        holder.popularCatLayout.setOnClickListener(View.OnClickListener {
            context.startActivity(Intent(context, BrandLogoEdit::class.java))
        })

        holder.popularCatTitle.text = poplularCatList[position]
    }

    override fun getItemCount(): Int {
        return poplularCatList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val popularCatLayout = itemView.findViewById<LinearLayout>(R.id.popular_cat_layout)
        val popularCatTitle = itemView.findViewById<TextView>(R.id.popular_cat_title)
    }
}