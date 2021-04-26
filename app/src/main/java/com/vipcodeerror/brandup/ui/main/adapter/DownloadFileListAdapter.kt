package com.vipcodeerror.brandup.ui.main.adapter

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.ui.main.view.activity.ImageOpenActivity
import com.vipcodeerror.brandup.util.AppUtils
import java.io.File

class DownloadFileListAdapter(var context : Context, var fListData : MutableList<String>) : RecyclerView.Adapter<DownloadFileListAdapter.MyViewHolder>() {
    val file = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES + File.separator + "downloaded_img")?.toURI()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadFileListAdapter.MyViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.download_list_adapter, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: DownloadFileListAdapter.MyViewHolder, position: Int) {

        Glide.with(context).load(file?.path + fListData[position]).into(holder.fileImg)
        //holder.fileText.text = fListData[position]
        holder.fileSelection.setOnClickListener {
            var intent = Intent(context, ImageOpenActivity::class.java)
            intent.putExtra("filePath", file?.path + fListData[position])
            context.startActivity(intent)
        }
        holder.fileSelection.setOnLongClickListener {
            var showDelete = androidx.appcompat.app.AlertDialog.Builder(context)
            showDelete.setMessage("Do you want to delete this banner ?")
            showDelete.setPositiveButton("OK") { dialog, which ->
                AppUtils.deleteFile(context, fListData[position])
                notifyItemRemoved(position) }

            showDelete.setNeutralButton("NO") { dialog, which -> dialog.dismiss() }
            showDelete.show()

            true
        }
    }

    override fun getItemCount(): Int {
        return fListData.size
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var fileImg = itemView.findViewById<ImageView>(R.id.file_img)
        var fileText = itemView.findViewById<TextView>(R.id.file_text)
        var fileSelection = itemView.findViewById<CardView>(R.id.file_l123)
    }

}