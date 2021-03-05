package com.vipcodeerror.brandup.ui.main.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.ui.main.adapter.PopularCategoryAdapter

class PreferredLanguageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.preferred_language_activity)

        val lanNextBtn = findViewById<ImageButton>(R.id.ln_next_btn)
        lanNextBtn.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@PreferredLanguageActivity, BusinessCategory::class.java))
        })
    }
}