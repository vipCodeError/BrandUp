package com.vipcodeerror.brandup.ui.main.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.data.api.ApiHelper
import com.vipcodeerror.brandup.data.api.ApiServiceImpl
import com.vipcodeerror.brandup.data.model.PlanDataModel
import com.vipcodeerror.brandup.data.model.home_modal.HomeSelectedModel
import com.vipcodeerror.brandup.ui.base.ViewModelFactory
import com.vipcodeerror.brandup.ui.main.adapter.PlanSelectorAdapter
import com.vipcodeerror.brandup.ui.main.viewmodel.MainViewModel
import com.vipcodeerror.brandup.util.SharedPreferenceUtil
import com.vipcodeerror.brandup.util.Status
import recycler.coverflow.RecyclerCoverFlow

class PlanSelectorActivity : AppCompatActivity() {
    private lateinit var planSelectorAdapter : PlanSelectorAdapter
    private lateinit var recyclerView: RecyclerCoverFlow
    private lateinit var mainViewModel: MainViewModel
    private lateinit var sharedPreferenceUtil : SharedPreferenceUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.plan_selector_activity)

        mainViewModel = setupViewModel()
        sharedPreferenceUtil = SharedPreferenceUtil(this)

        recyclerView = findViewById(R.id.plan_recycler)
        recyclerView.setAlphaItem(false)
        recyclerView.setFlatFlow(false)
        recyclerView.setGreyItem(false)

        getAllPlandData(sharedPreferenceUtil.getValueString("token").toString())
    }

    private fun setupViewModel() :MainViewModel {
        return  ViewModelProviders.of(
                this,
                ViewModelFactory(ApiHelper(ApiServiceImpl()))
        ).get(MainViewModel::class.java)
    }

    private fun getAllPlandData(token: String) {
        mainViewModel.getAllPlan(token).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let {
                        val pData = it.data
                        planSelectorAdapter = PlanSelectorAdapter(this, pData.toMutableList())
                        recyclerView.adapter = planSelectorAdapter
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