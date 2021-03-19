package com.vipcodeerror.brandup.ui.main.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.ChipGroup
import com.vipcodeerror.brandup.R

class PreferredLanguageActivity : AppCompatActivity() {

    private lateinit var chipGroup: ChipGroup
    private lateinit var lanNextBtn: ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.preferred_language_activity)

        chipGroup = findViewById(R.id.chip_lan_group)
        lanNextBtn = findViewById(R.id.ln_next_btn)

        chipGroup.setOnCheckedChangeListener { group, checkedId ->

            when (checkedId) {
                R.id.lan_default -> {

                }
                R.id.lan_english -> {

                }
                R.id.lan_hindi -> {

                }
            }
        }

        lanNextBtn.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@PreferredLanguageActivity, BusinessCategory::class.java))
        })
    }
}