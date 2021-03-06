package com.vipcodeerror.brandup.ui.main.view.activity

import `in`.aabhasjindal.otptextview.OTPListener
import `in`.aabhasjindal.otptextview.OtpTextView
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vipcodeerror.brandup.R

class OtpVerficationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.otp_layout);

        val otpView = findViewById<OtpTextView>(R.id.otp_view);
        otpView.otpListener = object: OTPListener {
            override fun onInteractionListener() {

            }

            override fun onOTPComplete(otp: String) {
                startActivity(Intent(this@OtpVerficationActivity, PreferredLanguageActivity::class.java))
                finish()
            }

        }

    }
}