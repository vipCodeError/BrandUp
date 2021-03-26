package com.vipcodeerror.brandup.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.data.model.BusinessDetailsModel
import com.vipcodeerror.brandup.data.model.MyBusiness
import com.vipcodeerror.brandup.ui.main.view.activity.BusinessCategory

class MyBusinessListAdapter(var context: Context, var businessList : MutableList<BusinessDetailsModel>) : RecyclerView.Adapter<MyBusinessListAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyBusinessListAdapter.MyViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.my_business_list_adapter_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyBusinessListAdapter.MyViewHolder, position: Int) {
        Glide.with(context).load("https://d4f9k68hk754p.cloudfront.net/fit-in/300x400/images/" + businessList[position].logoUrl).into(holder.logoImageView)
        holder.businessName.text = businessList[position].bName
        holder.whichCatBelongTo.text = businessList[position].catName
        holder.businessLocation.text = businessList[position].bLocation
        holder.editBrand.setOnClickListener {
            context.startActivity(Intent(context, BusinessCategory::class.java))
        }
    }

    override fun getItemCount(): Int {
        return businessList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var logoImageView = itemView.findViewById<ImageView>(R.id.your_logo)
        var businessName = itemView.findViewById<TextView>(R.id.business_name)
        var whichCatBelongTo = itemView.findViewById<TextView>(R.id.which_cat_belong_to)
        var businessLocation = itemView.findViewById<TextView>(R.id.addr_of_business)
        var editBrand = itemView.findViewById<TextView>(R.id.edit_your_business)
    }
}