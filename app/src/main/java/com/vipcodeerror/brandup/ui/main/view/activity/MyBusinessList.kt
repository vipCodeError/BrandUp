package com.vipcodeerror.brandup.ui.main.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.ui.main.adapter.MyBusinessListAdapter

public class MyBusinessList : AppCompatActivity(){
    private lateinit var toolbar: Toolbar
    private lateinit var myBusinessAdapter: MyBusinessListAdapter
    private lateinit var myBusinessRecyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mybusinesslist_activity)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(
                ContextCompat.getDrawable(
                        this,
                        R.drawable.arrow_back
                )
        )
        supportActionBar?.title = "My Businesses"

//        myBusinessRecyclerView = findViewById(R.id.my_business_recyclerview)
//        myBusinessAdapter = MyBusinessListAdapter()
//        myBusinessRecyclerView.adapter = myBusinessAdapter
    }
}