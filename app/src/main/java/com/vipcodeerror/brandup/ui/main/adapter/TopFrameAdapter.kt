package com.vipcodeerror.brandup.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vipcodeerror.brandup.R

class TopFrameAdapter(var context: Context, var imgUrl : MutableList<String>) : RecyclerView.Adapter<TopFrameAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopFrameAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.top_frame_adapter_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: TopFrameAdapter.MyViewHolder, position: Int) {
        Glide.with(context).load(imgUrl[position]).into(holder.topFrameImageView)
    }

    override fun getItemCount(): Int {
        return imgUrl.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var topFrameImageView = itemView.findViewById<ImageView>(R.id.top_frame_imgview)
    }
}