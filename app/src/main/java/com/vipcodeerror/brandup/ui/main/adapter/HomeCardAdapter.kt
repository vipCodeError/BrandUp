package com.vipcodeerror.brandup.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.data.model.home_modal.HomeModel
import com.vipcodeerror.brandup.ui.main.view.activity.FrameTemplateSelectorActivity

class HomeCardAdapter(var context: Context, var isSubShown : String,var catId : String,var subId : String, var hData: MutableList<HomeModel>) : RecyclerView.Adapter<HomeCardAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.first_adapter_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Glide.with(context).load("https://d4f9k68hk754p.cloudfront.net/fit-in/300x400/images/"+hData[position].urlImage).into(holder.imageUrlView)
        holder.imageUrlView.setOnClickListener(View.OnClickListener {
            val intent = Intent(context, FrameTemplateSelectorActivity::class.java)
            intent.putExtra("is_sub_shown", isSubShown)
            intent.putExtra("cat_id", catId)
            intent.putExtra("sub_id", subId)
            context.startActivity(intent)
        })
    }

    override fun getItemCount(): Int {
        return hData.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageUrlView = itemView.findViewById<ImageView>(R.id.image_url_for_home)
    }
}