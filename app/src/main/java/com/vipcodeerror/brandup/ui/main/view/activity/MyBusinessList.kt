package com.vipcodeerror.brandup.ui.main.view.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.data.api.ApiHelper
import com.vipcodeerror.brandup.data.api.ApiServiceImpl
import com.vipcodeerror.brandup.data.model.BusinessDetailsModel
import com.vipcodeerror.brandup.ui.base.ViewModelFactory
import com.vipcodeerror.brandup.ui.main.adapter.BussDialogAdapter
import com.vipcodeerror.brandup.ui.main.adapter.MyBusinessListAdapter
import com.vipcodeerror.brandup.ui.main.viewmodel.MainViewModel
import com.vipcodeerror.brandup.util.SharedPreferenceUtil
import com.vipcodeerror.brandup.util.Status

public class MyBusinessList : AppCompatActivity(){
    private lateinit var toolbar: Toolbar
    private lateinit var myBusinessAdapter: MyBusinessListAdapter
    private lateinit var myBusinessRecyclerView : RecyclerView
    private lateinit var mainViewModel: MainViewModel
    private lateinit var sharedPreferenceUtil : SharedPreferenceUtil

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


        mainViewModel = setupViewModel()
        sharedPreferenceUtil = SharedPreferenceUtil(this)

        getBusinnessData(mainViewModel, sharedPreferenceUtil.getValueString("user_id").toString(),
                sharedPreferenceUtil.getValueString("token").toString())

    }

    private fun setupViewModel() :MainViewModel {
        return  ViewModelProviders.of(
                this,
                ViewModelFactory(ApiHelper(ApiServiceImpl()))
        ).get(MainViewModel::class.java)
    }


    private fun getBusinnessData(mVModel : MainViewModel, catId: String, token: String) {
        mVModel.getBussinessDetails(catId, token).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let {
                        myBusinessAdapter = MyBusinessListAdapter(this@MyBusinessList, it.data.toMutableList())
                        myBusinessRecyclerView = findViewById(R.id.my_business_recyclerview)
                        myBusinessRecyclerView.adapter = myBusinessAdapter
                        myBusinessRecyclerView.layoutManager = LinearLayoutManager(this@MyBusinessList)
                    }
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {

                }
            }
        })
    }


}