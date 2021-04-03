package com.vipcodeerror.brandup.ui.main.view.activity

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.util.AppUtils

class ImageOpenActivity : AppCompatActivity() {

    private lateinit var imageThumbnail : ImageView
    private lateinit var imageShare : ImageView
    var fileStr = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.image_open_activity)
        imageThumbnail = findViewById(R.id.image_thumbnail)
        imageShare = findViewById(R.id.share)

        if(intent != null){
            fileStr = intent.getStringExtra("filePath").toString()
            Glide.with(this).load(fileStr).into(imageThumbnail)
        }

        imageShare.setOnClickListener {
            AppUtils.launchShareIntent(this, imageThumbnail)
        }
    }
}