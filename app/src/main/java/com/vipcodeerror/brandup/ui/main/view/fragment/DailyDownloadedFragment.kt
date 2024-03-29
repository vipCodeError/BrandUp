package com.vipcodeerror.brandup.ui.main.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.ui.main.adapter.DownloadFileListAdapter
import com.vipcodeerror.brandup.util.AppUtils

class DailyDownloadedFragment : Fragment() {

    private lateinit var recyclerViewDownload : RecyclerView
    private lateinit var downloadAdapter : DownloadFileListAdapter

    override fun onResume() {
        super.onResume()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view =  inflater.inflate(R.layout.fragment_downloaded, container, false)

        var messageTxt = view.findViewById<TextView>(R.id.message)
        recyclerViewDownload = view.findViewById(R.id.recycler_download)

        var fList = AppUtils.getFilesList(requireActivity())
        if (fList.size == 0){
            messageTxt.visibility = View.VISIBLE
        }else {
            messageTxt.visibility = View.GONE
        }
        downloadAdapter = DownloadFileListAdapter(requireActivity(), fList)
        recyclerViewDownload.adapter = downloadAdapter
        recyclerViewDownload.layoutManager = GridLayoutManager(requireActivity(), 2, GridLayoutManager.VERTICAL, false)
        return view
    }
}