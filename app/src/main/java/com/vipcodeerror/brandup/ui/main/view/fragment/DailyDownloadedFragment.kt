package com.vipcodeerror.brandup.ui.main.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.AdapterListUpdateCallback
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

        recyclerViewDownload = view.findViewById(R.id.recycler_download)

        var fList = AppUtils.getFilesList(requireActivity())
        downloadAdapter = DownloadFileListAdapter(requireActivity(), fList)
        recyclerViewDownload.adapter = downloadAdapter
        recyclerViewDownload.layoutManager = LinearLayoutManager(requireActivity())
        return view
    }
}