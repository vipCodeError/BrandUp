package com.vipcodeerror.brandup.ui.main.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.asdev.phoneedittext.PhoneEditText
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.data.api.ApiHelper
import com.vipcodeerror.brandup.data.api.ApiServiceImpl
import com.vipcodeerror.brandup.ui.base.ViewModelFactory
import com.vipcodeerror.brandup.ui.main.viewmodel.MainViewModel
import com.vipcodeerror.brandup.util.Status
import java.util.*

/*
* Phone Edit text does not work with koltin code
* so better stick with java implementaion
*
* */
class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        val myPhoneEditText = findViewById<PhoneEditText>(R.id.phoneEditText)
        val sendOtpBtn = findViewById<AppCompatButton>(R.id.send_otp_btn)
        sendOtpBtn.setOnClickListener {
            val intent = Intent(this@LoginActivity, OtpVerficationActivity::class.java)
            intent.putExtra("phone_num", Objects.requireNonNull(myPhoneEditText.text).toString())
            startActivity(intent)
        }


    }


}