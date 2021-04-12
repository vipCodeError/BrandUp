package com.vipcodeerror.brandup.ui.main.view.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.util.SharedPreferenceUtil
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class SplashScreen : AppCompatActivity() {

    private lateinit var sharedPreferenceUtil : SharedPreferenceUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen);
        sharedPreferenceUtil = SharedPreferenceUtil(this)

        GlobalScope.launch(Dispatchers.Main){
            delay(TimeUnit.SECONDS.toMillis(3))
            if(sharedPreferenceUtil.getValueBoolean("is_logged")){
                startActivity(Intent(this@SplashScreen, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))
                finish()
            }else {
                startActivity(Intent(this@SplashScreen, LoginActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK))
                finish()
            }

        }
    }
}