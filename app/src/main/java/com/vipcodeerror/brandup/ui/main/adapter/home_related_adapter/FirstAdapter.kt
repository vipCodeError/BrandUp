package com.vipcodeerror.brandup.ui.main.adapter.home_related_adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vipcodeerror.brandup.R

class FirstAdapter(var context: Context, var imgUrl: MutableList<String>) : RecyclerView.Adapter<FirstAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FirstAdapter.MyViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.first_adapter_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: FirstAdapter.MyViewHolder, position: Int) {
        Glide.with(context).load(imgUrl[position]).into(holder.imageUrlView)
    }

    override fun getItemCount(): Int {
        return imgUrl.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageUrlView = itemView.findViewById<ImageView>(R.id.image_url_for_home)
    }
}