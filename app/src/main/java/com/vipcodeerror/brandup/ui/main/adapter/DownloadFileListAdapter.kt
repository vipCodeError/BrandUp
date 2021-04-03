package com.vipcodeerror.brandup.ui.main.adapter

import android.content.Context
import android.content.Intent
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.ui.main.view.activity.ImageOpenActivity
import java.io.File

class DownloadFileListAdapter(var context : Context, var fListData : MutableList<String>) : RecyclerView.Adapter<DownloadFileListAdapter.MyViewHolder>() {
    val file = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.toURI()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadFileListAdapter.MyViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.download_list_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: DownloadFileListAdapter.MyViewHolder, position: Int) {

        Glide.with(context).load(file?.path + fListData[position]).into(holder.fileImg)
        holder.fileText.text = fListData[position]
        holder.fileSelection.setOnClickListener {
            var intent = Intent(context, ImageOpenActivity::class.java)
            intent.putExtra("filePath", file?.path + fListData[position])
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return fListData.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var fileImg = itemView.findViewById<ImageView>(R.id.file_img)
        var fileText = itemView.findViewById<TextView>(R.id.file_text)
        var fileSelection = itemView.findViewById<LinearLayout>(R.id.file_l123)
    }

}