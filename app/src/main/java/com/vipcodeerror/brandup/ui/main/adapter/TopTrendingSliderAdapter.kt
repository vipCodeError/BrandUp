package com.vipcodeerror.brandup.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.SliderViewAdapter
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.data.model.BannerDataModel


class TopTrendingSliderAdapter(var context: Context) : SliderViewAdapter<TopTrendingSliderAdapter.SliderAdapterVH>() {
    private lateinit var mSliderItems: MutableList<BannerDataModel>

    init {
        mSliderItems = mutableListOf()
    }

    fun renewItems(sliderItems: MutableList<BannerDataModel>) {
        mSliderItems = sliderItems
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        mSliderItems.removeAt(position)
        notifyDataSetChanged()
    }

    fun addItem(sliderItem: MutableList<BannerDataModel>) {
        mSliderItems.addAll(sliderItem)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH {
        val inflate: View =
            LayoutInflater.from(parent.context).inflate(R.layout.image_slider_layout_item, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        // val sliderItem: SliderItem = mSliderItems[position]

        Glide.with(viewHolder.itemView)
            .load("https://d4f9k68hk754p.cloudfront.net/fit-in/512x400/images/" + mSliderItems[position].url)
            .fitCenter()
            .into(viewHolder.imageViewBackground)

        viewHolder.itemView.setOnClickListener {
            Toast.makeText(context, "This is item in position $position", Toast.LENGTH_SHORT)
                .show()
        }

        viewHolder.imageViewBackground.setOnClickListener {
            val browseintent = Intent(Intent.ACTION_VIEW,
                    Uri.parse(mSliderItems[0].redirectUrl))
            context.startActivity(browseintent)
            Toast.makeText(context, mSliderItems[0].redirectUrl, Toast.LENGTH_SHORT).show()

        }
    }

    override fun getCount(): Int {
        return mSliderItems.size
    }

    inner class SliderAdapterVH(itemView: View) : ViewHolder(itemView) {
        var imageViewBackground: ImageView = itemView.findViewById(R.id.slider_images)
    }
}