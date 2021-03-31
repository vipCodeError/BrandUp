package com.vipcodeerror.brandup.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.data.model.BottomBannerModel
import com.vipcodeerror.brandup.data.model.home_modal.HomeModel

class BottomBannerAdapter (var context: Context, var imgUrlList: MutableList<BottomBannerModel>) : RecyclerView.Adapter<BottomBannerAdapter.MyViewHolder>() {
    interface ClickOnFrameUrl {
        fun setUrlImage(url : String)
    }


    public lateinit var clickOnFrameUrl: ClickOnFrameUrl

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomBannerAdapter.MyViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.frame_bottom_activity_adapter_lay, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: BottomBannerAdapter.MyViewHolder, position: Int) {
        Glide.with(context).load("https://d4f9k68hk754p.cloudfront.net/fit-in/300x400/images/brandup.png").into(holder.frameImg)
        Glide.with(context).load("https://d4f9k68hk754p.cloudfront.net/fit-in/300x400/" + imgUrlList[position].urlBottomBanner).into(holder.bottomFrame)
        holder.frameImg.setOnClickListener {
            clickOnFrameUrl.setUrlImage(imgUrlList[position].urlBottomBanner)
        }
    }

    override fun getItemCount(): Int {
        return imgUrlList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var frameImg = itemView.findViewById<ImageView>(R.id.frame_img)
        var bottomFrame = itemView.findViewById<ImageView>(R.id.bottom_frame)
    }
}