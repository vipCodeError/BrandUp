package com.vipcodeerror.brandup.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.data.model.CatModel
import com.vipcodeerror.brandup.ui.main.view.activity.BrandLogoEdit
import com.vipcodeerror.brandup.util.SharedPreferenceUtil
import de.hdodenhof.circleimageview.CircleImageView

class PopularCategoryAdapter(var context : Context, var poplularCatList: List<CatModel>) : RecyclerView.Adapter<PopularCategoryAdapter.MyViewHolder>() {
    private lateinit var sharedPreferenceUtil : SharedPreferenceUtil

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PopularCategoryAdapter.MyViewHolder {
        sharedPreferenceUtil = SharedPreferenceUtil(context)
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.popular_cat_adapter_layout, parent, false))
    }

    override fun onBindViewHolder(holder: PopularCategoryAdapter.MyViewHolder, position: Int) {
        holder.popularCatLayout.setOnClickListener(View.OnClickListener {
            sharedPreferenceUtil.save("cat_id", poplularCatList[position].id.toString())
            context.startActivity(Intent(context, BrandLogoEdit::class.java))
        })

        Glide.with(context).load("https://practicebuckett123.s3.ap-south-1.amazonaws.com/images/"+poplularCatList[position].imgUrl).into(holder.circleImageView)

        holder.popularCatTitle.text = poplularCatList[position].catName
    }

    override fun getItemCount(): Int {
        return poplularCatList.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val popularCatLayout = itemView.findViewById<LinearLayout>(R.id.popular_cat_layout)
        val popularCatTitle = itemView.findViewById<TextView>(R.id.popular_cat_title)
        val circleImageView = itemView.findViewById<CircleImageView>(R.id.profile_image)
    }
}