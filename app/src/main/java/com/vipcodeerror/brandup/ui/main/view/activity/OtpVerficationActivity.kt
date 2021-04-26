package com.vipcodeerror.brandup.ui.main.view.activity

import `in`.aabhasjindal.otptextview.OTPListener
import `in`.aabhasjindal.otptextview.OtpTextView
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.data.api.ApiHelper
import com.vipcodeerror.brandup.data.api.ApiServiceImpl
import com.vipcodeerror.brandup.ui.base.ViewModelFactory
import com.vipcodeerror.brandup.ui.main.viewmodel.MainViewModel
import com.vipcodeerror.brandup.util.SharedPreferenceUtil
import com.vipcodeerror.brandup.util.Status
import java.util.concurrent.TimeUnit

class OtpVerficationActivity : AppCompatActivity() {
    private lateinit var callbacks : PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var auth: FirebaseAuth
    private lateinit var phoneNumText : TextView
    private var storedVerificationId : String? = null
    private lateinit var mainViewModel: MainViewModel
    private lateinit var phoneNumber : String
    private lateinit var countDownTxt : TextView
    private lateinit var sharedPreferenceUtil : SharedPreferenceUtil

    companion object {
        private val TAG = LoginActivity::class.java.canonicalName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.otp_layout)
        sharedPreferenceUtil = SharedPreferenceUtil(this)

        setupViewModel()

        phoneNumText = findViewById(R.id.phone_num_txt)
        countDownTxt = findViewById(R.id.otp_countdown)
        auth = FirebaseAuth.getInstance()
        callBackPhoneOtp()

        phoneNumber = intent.getStringExtra("phone_num").toString()
        phoneNumberAuthentication(phoneNumber.replace(" ", ""))

        val otpView = findViewById<OtpTextView>(R.id.otp_view);
        otpView.otpListener = object: OTPListener {
            override fun onInteractionListener() {

            }

            override fun onOTPComplete(otp: String) {
                if (storedVerificationId != null){
                    var credential = PhoneAuthProvider.getCredential(storedVerificationId, otp)
                    signInWithPhoneAuthCredential(credential)
                }else {
                    Toast.makeText(this@OtpVerficationActivity, "Wait while OTP comes", Toast.LENGTH_SHORT).show()
                }
            }
        }

        phoneNumText.text = phoneNumber.toString()

        val timer = object: CountDownTimer(60000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                countDownTxt.text = (millisUntilFinished / 1000).toString()
            }

            override fun onFinish() {
                countDownTxt.text = "Resend OTP again"
            }
        }
        timer.start()
    }

    private fun phoneNumberAuthentication(phoneNumber: String){
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }


    private fun callBackPhoneOtp(){
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            override fun onVerificationCompleted(credential: PhoneAuthCredential){
                signInWithPhoneAuthCredential(credential)
                Log.d(TAG, "onVerificationCompleted:$credential")
            }

            override fun onVerificationFailed(e: FirebaseException) {

                Log.w(TAG, "onVerificationFailed", e)

                if (e is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                }

            }

            override fun onCodeSent(
                verificationId: String,
                token: PhoneAuthProvider.ForceResendingToken
            ) {
                Log.d(TAG, "onCodeSent:$verificationId")
                // Save verification ID and resending token so we can use them later
                storedVerificationId = verificationId
                // resendToken = token
            }
        }
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    loginObserver(phoneNumber.replace("+91 ", ""))

                } else {
                    // Sign in failed, display a message and update the UI
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        // The verification code entered was invalid
                    }
                    // Update UI
                }
            }
    }

    private fun loginObserver(phone : String) {
        mainViewModel.postLoginUser(phone, Build.MANUFACTURER).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let {
                        sharedPreferenceUtil.save("token", it.token)
                        sharedPreferenceUtil.save("user_id", it.id.toString())
                        if (it.prefBusiness != null){
                            sharedPreferenceUtil.save("pref_buss", it.prefBusiness)
                        }

                        sharedPreferenceUtil.save("is_logged", true)
                        sharedPreferenceUtil.save("plan_id", it.planId)
                        // sharedPreferenceUtil.save("plan_name", it.planName)

                        getPlanById(it.isAlreadyExist, it.planId, sharedPreferenceUtil.getValueString("token").toString())
                    }
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    Toast.makeText(this@OtpVerficationActivity, "Token ID is :: " + it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun getPlanById(isAlreadyExist : String, plan_id : String, token : String) {
        mainViewModel.getPlanById(plan_id, token).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let {
                        sharedPreferenceUtil.save("plan_name", it?.planName)
                        sharedPreferenceUtil.save("download_limit", it?.downloadLimit)
                        sharedPreferenceUtil.save("share_limit", it?.shareLimit)
                        sharedPreferenceUtil.save("days_limit", it?.dayLimit)
                        sharedPreferenceUtil.save("business_card_limit", it?.bCardLimit)

                        if(isAlreadyExist == "0"){
                            Toast.makeText(this@OtpVerficationActivity, "Token ID is :: " +  sharedPreferenceUtil.getValueString("token").toString(), Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@OtpVerficationActivity, PreferredLanguageActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                            finish()
                        }else {
                            Toast.makeText(this@OtpVerficationActivity, "Token ID is :: " +  sharedPreferenceUtil.getValueString("token").toString(), Toast.LENGTH_SHORT).show()
                            startActivity(Intent(this@OtpVerficationActivity, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                            finish()
                        }

                    }
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    Toast.makeText(this@OtpVerficationActivity, "Token ID is :: " + it.message, Toast.LENGTH_SHORT).show()
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