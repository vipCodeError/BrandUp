package com.vipcodeerror.brandup.ui.main.adapter.home_related_adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.data.model.home_modal.HomeModel
import com.vipcodeerror.brandup.ui.main.view.activity.FrameTemplateSelectorActivity

class FirstAdapter(var context: Context, var imgUrl: MutableList<HomeModel>) : RecyclerView.Adapter<FirstAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FirstAdapter.MyViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.first_adapter_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: FirstAdapter.MyViewHolder, position: Int) {
        Glide.with(context).load("https://d4f9k68hk754p.cloudfront.net/fit-in/300x400/images/"+imgUrl[position].urlImage).into(holder.imageUrlView)
        holder.imageUrlView.setOnClickListener(View.OnClickListener {
            context.startActivity(Intent(context, FrameTemplateSelectorActivity::class.java))
        })
    }

    override fun getItemCount(): Int {
        return imgUrl.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageUrlView = itemView.findViewById<ImageView>(R.id.image_url_for_home)
    }
}