package com.vipcodeerror.brandup.ui.main.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.data.model.BusinessDetailsModel
import com.vipcodeerror.brandup.util.SharedPreferenceUtil

class BussDialogAdapter(val bData : MutableList<BusinessDetailsModel>) : RecyclerView.Adapter<BussDialogAdapter.MyViewHolder>() {

    interface OnClickListItem{
        fun setBId(bId : String)
    }

    private lateinit var sharedPreferenceUtil : SharedPreferenceUtil

    private lateinit var onClickListener : OnClickListItem

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BussDialogAdapter.MyViewHolder {
        sharedPreferenceUtil = SharedPreferenceUtil(parent.context)
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.buss_dialog_text, parent, false))
    }

    override fun onBindViewHolder(holder: BussDialogAdapter.MyViewHolder, position: Int) {
        holder.bussTitleTxt.text = bData[position].bName
        holder.caTitleTxt.text = bData[position].catName.toUpperCase()
        if(bData[position].id == sharedPreferenceUtil.getValueString("pref_buss").toString()){
            holder.checkMark.visibility = View.VISIBLE
        }else {
            holder.checkMark.visibility = View.GONE
        }

        holder.bLLayout.setOnClickListener {
            onClickListener.setBId(bData[position].id)
        }
    }

    override fun getItemCount(): Int {
        return bData.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val bussTitleTxt = itemView.findViewById<TextView>(R.id.buss_title_txt)
        val caTitleTxt = itemView.findViewById<TextView>(R.id.cat_title_txt)
        var bLLayout = itemView.findViewById<LinearLayout>(R.id.b_l_lay)
        var checkMark = itemView.findViewById<ImageView>(R.id.select_buss_mark)
    }

}