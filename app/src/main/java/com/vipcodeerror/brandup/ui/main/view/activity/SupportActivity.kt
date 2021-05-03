package com.vipcodeerror.brandup.ui.main.view.activity

import android.R.id.message
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.data.api.ApiHelper
import com.vipcodeerror.brandup.data.api.ApiServiceImpl
import com.vipcodeerror.brandup.ui.base.ViewModelFactory
import com.vipcodeerror.brandup.ui.main.viewmodel.MainViewModel
import com.vipcodeerror.brandup.util.Status


class SupportActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var emailTxt : TextView
    private lateinit var sendBtn : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.support_activity)
        mainViewModel = setupViewModel()

        getSupportData(mainViewModel)
        emailTxt = findViewById(R.id.email)
        sendBtn = findViewById(R.id.send_email_img)

        sendBtn.setOnClickListener{
            val email = Intent(Intent.ACTION_SEND)
            email.putExtra(Intent.EXTRA_EMAIL, arrayOf<String>(emailTxt.text.toString()))
            email.putExtra(Intent.EXTRA_SUBJECT, "Enquiry")
            email.putExtra(Intent.EXTRA_TEXT, "Type your query here")
            email.type = "message/rfc822"

            startActivity(Intent.createChooser(email, "Choose an Email client :"))

        }


    }

    private fun setupViewModel() :MainViewModel {
        return  ViewModelProviders.of(
                this,
                ViewModelFactory(ApiHelper(ApiServiceImpl()))
        ).get(MainViewModel::class.java)
    }

    private fun getSupportData(mVModel: MainViewModel) {
        mVModel.getSupportData().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let {
                        emailTxt.text = it.data[0].email
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