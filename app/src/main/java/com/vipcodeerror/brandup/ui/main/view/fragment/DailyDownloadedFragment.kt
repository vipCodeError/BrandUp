package com.vipcodeerror.brandup.ui.main.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.vipcodeerror.brandup.R

class DailyDownloadedFragment : Fragment() {

    private lateinit var recyclerViewDownload : RecyclerView
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view =  inflater.inflate(R.layout.fragment_downloaded, container, false)

        recyclerViewDownload = view.findViewById(R.id.recycler_download)

        return view;
    }
}