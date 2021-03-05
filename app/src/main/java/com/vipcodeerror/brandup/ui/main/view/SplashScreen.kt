package com.vipcodeerror.brandup.ui.main.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vipcodeerror.brandup.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Thread.sleep
import java.util.concurrent.TimeUnit

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen);

        GlobalScope.launch(Dispatchers.Main){
            delay(TimeUnit.SECONDS.toMillis(3))
            startActivity(Intent(this@SplashScreen, LoginActivity::class.java))
            finish()
        }
    }
}