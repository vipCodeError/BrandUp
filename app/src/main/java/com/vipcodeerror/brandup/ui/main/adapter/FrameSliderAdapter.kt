package com.vipcodeerror.brandup.ui.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.smarteist.autoimageslider.SliderViewAdapter
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.data.model.BottomBannerModel
import com.vipcodeerror.brandup.data.model.SliderItem

class FrameSliderAdapter(var context: Context) : SliderViewAdapter<FrameSliderAdapter.SliderAdapterVH>() {
    private lateinit var mSliderItems: MutableList<BottomBannerModel>

    init {
        mSliderItems = mutableListOf()
    }

    fun renewItems(sliderItems: MutableList<BottomBannerModel>) {
        mSliderItems = sliderItems
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        mSliderItems.removeAt(position)
        notifyDataSetChanged()
    }

    fun addItem(sliderItem: MutableList<BottomBannerModel>) {
        mSliderItems.addAll(sliderItem)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate: View =
                LayoutInflater.from(parent.context).inflate(R.layout.image_frame_slider, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        Glide.with(context).load("https://d4f9k68hk754p.cloudfront.net/fit-in/512x512/${mSliderItems[position].urlBottomBanner}").apply( RequestOptions()
        .fitCenter()
        .format(DecodeFormat.PREFER_ARGB_8888)
        .override(SIZE_ORIGINAL))
        .into(viewHolder.imageViewBackground);

        viewHolder.itemView.setOnClickListener {
//            Toast.makeText(context, "This is item in position $position", Toast.LENGTH_SHORT)
//                    .show()
        }
    }

    override fun getCount(): Int {
        return mSliderItems.size
    }

    inner class SliderAdapterVH(itemView: View) : ViewHolder(itemView) {
        var imageViewBackground: ImageView = itemView.findViewById(R.id.slider_images)
    }
}