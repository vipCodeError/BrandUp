package com.vipcodeerror.brandup.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vipcodeerror.brandup.R

interface ClickOnFrameUrl {
    fun setUrlImage(url : String)
}

class FrameSelectorAdapter(var context: Context, var imgUrlList: MutableList<String>) : RecyclerView.Adapter<FrameSelectorAdapter.MyViewHolder>() {
    public lateinit var clickOnFrameUrl: ClickOnFrameUrl

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FrameSelectorAdapter.MyViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.frame_show_activity, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: FrameSelectorAdapter.MyViewHolder, position: Int) {
        Glide.with(context).load(imgUrlList[position]).into(holder.frameImg)
        holder.frameImg.setOnClickListener {
            clickOnFrameUrl.setUrlImage(imgUrlList[position])
        }
    }

    override fun getItemCount(): Int {
        return imgUrlList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var frameImg = itemView.findViewById<ImageView>(R.id.frame_img)
    }
}