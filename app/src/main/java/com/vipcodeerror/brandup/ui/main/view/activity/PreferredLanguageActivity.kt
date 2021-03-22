package com.vipcodeerror.brandup.ui.main.view.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.ChipGroup
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.util.SharedPreferenceUtil

class PreferredLanguageActivity : AppCompatActivity() {

    private lateinit var chipGroup: ChipGroup
    private lateinit var lanNextBtn: ImageButton
    private lateinit var sharedPreferenceUtil : SharedPreferenceUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.preferred_language_activity)

        sharedPreferenceUtil = SharedPreferenceUtil(this)

        chipGroup = findViewById(R.id.chip_lan_group)
        lanNextBtn = findViewById(R.id.ln_next_btn)

        chipGroup.setOnCheckedChangeListener { group, checkedId ->

            when (checkedId) {
                R.id.lan_default -> {
                    sharedPreferenceUtil.save("app_lan", "default")
                }
                R.id.lan_english -> {
                    sharedPreferenceUtil.save("app_lan", "en_lan")
                }
                R.id.lan_hindi -> {
                    sharedPreferenceUtil.save("app_lan", "hi_lan")
                }
            }
        }

        lanNextBtn.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this@PreferredLanguageActivity, BusinessCategory::class.java))
        })
    }
}