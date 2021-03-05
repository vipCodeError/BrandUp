package com.vipcodeerror.brandup.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.data.model.MyBusiness

class MyBusinessListAdapter(var businessList : MutableList<MyBusiness>) : RecyclerView.Adapter<MyBusinessListAdapter.MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyBusinessListAdapter.MyViewHolder {
       val view = LayoutInflater.from(parent.context).inflate(R.layout.my_business_list_adapter_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyBusinessListAdapter.MyViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return businessList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var logoImageView = itemView.findViewById<ImageView>(R.id.your_logo)
        var businessName = itemView.findViewById<TextView>(R.id.business_name)
        var whichCatBelongTo = itemView.findViewById<TextView>(R.id.which_cat_belong_to)
        var businessLocation = itemView.findViewById<TextView>(R.id.addr_of_business)
    }
}