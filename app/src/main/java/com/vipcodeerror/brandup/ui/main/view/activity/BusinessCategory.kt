package com.vipcodeerror.brandup.ui.main.view.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.data.api.ApiHelper
import com.vipcodeerror.brandup.data.api.ApiServiceImpl
import com.vipcodeerror.brandup.ui.base.ViewModelFactory
import com.vipcodeerror.brandup.ui.main.adapter.PopularCategoryAdapter
import com.vipcodeerror.brandup.ui.main.viewmodel.MainViewModel
import com.vipcodeerror.brandup.util.SharedPreferenceUtil
import com.vipcodeerror.brandup.util.Status

class BusinessCategory : AppCompatActivity() {

    private lateinit var businessRecyclerView : RecyclerView
    private lateinit var popularCategoryAdapter : PopularCategoryAdapter
    private lateinit var toolbar: Toolbar
    private lateinit var mainViewModel: MainViewModel
    private lateinit var sharedPreferenceUtil : SharedPreferenceUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bussiness_activity)
        setupViewModel()

        sharedPreferenceUtil = SharedPreferenceUtil(this)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar);

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.arrow_back))
        supportActionBar?.title = "Select your business category"
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            super.onBackPressed()
        })

        catDataObserver(sharedPreferenceUtil.getValueString("token").toString())

    }

    private fun catDataObserver(token: String) {
        mainViewModel.getCatData(token).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let {
                        var catdata = it.data
                        businessRecyclerView = findViewById(R.id.popular_cat_recycler)
                        popularCategoryAdapter = PopularCategoryAdapter(this, catdata)
                        businessRecyclerView.layoutManager =  GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
                        businessRecyclerView.adapter = popularCategoryAdapter
                       // sharedPreferenceUtil.save("token", it.token)
                        Toast.makeText(this@BusinessCategory, "Token ID is :: " + it.data, Toast.LENGTH_SHORT).show()
//                        startActivity(Intent(this@OtpVerficationActivity, PreferredLanguageActivity::class.java))
//                        finish()
                    }
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                   // Toast.makeText(this@OtpVerficationActivity, "Token ID is :: " + it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(
                this,
                ViewModelFactory(ApiHelper(ApiServiceImpl()))
        ).get(MainViewModel::class.java)
    }
}