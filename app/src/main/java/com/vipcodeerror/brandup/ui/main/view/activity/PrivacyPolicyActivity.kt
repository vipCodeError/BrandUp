package com.vipcodeerror.brandup.ui.main.view.activity

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.data.api.ApiHelper
import com.vipcodeerror.brandup.data.api.ApiServiceImpl
import com.vipcodeerror.brandup.ui.base.ViewModelFactory
import com.vipcodeerror.brandup.ui.main.viewmodel.MainViewModel
import com.vipcodeerror.brandup.util.Status

class PrivacyPolicyActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    private lateinit var privacyTxt : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.support_privacy)
        mainViewModel = setupViewModel()

        privacyTxt = findViewById(R.id.policy_t)
        getPrivacyData(mainViewModel)
    }

    private fun setupViewModel() :MainViewModel {
        return  ViewModelProviders.of(
                this,
                ViewModelFactory(ApiHelper(ApiServiceImpl()))
        ).get(MainViewModel::class.java)
    }

    private fun getPrivacyData(mVModel: MainViewModel) {
        mVModel.getPrivacyData().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let {
                        privacyTxt.text = HtmlCompat.fromHtml(it.data[0].policyData, HtmlCompat.FROM_HTML_MODE_COMPACT)
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