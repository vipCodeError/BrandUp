package com.vipcodeerror.brandup.ui.main.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import com.asdev.phoneedittext.PhoneEditText
import com.vipcodeerror.brandup.R

class LoginActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)
        val myPhoneEditText = findViewById<PhoneEditText>(R.id.phoneEditText)
        val code = myPhoneEditText.diaL_CODE;

        val sendOtpBtn = findViewById<AppCompatButton>(R.id.send_otp_btn)
        sendOtpBtn.setOnClickListener {
            startActivity(Intent(this@LoginActivity, OtpVerficationActivity::class.java))
            finish()
        }

    }
}