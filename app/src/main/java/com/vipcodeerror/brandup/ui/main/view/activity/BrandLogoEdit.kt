package com.vipcodeerror.brandup.ui.main.view.activity

import android.Manifest
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.multi.DialogOnAnyDeniedMultiplePermissionsListener
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.util.ValueAnimatorUtil


class BrandLogoEdit : AppCompatActivity(){

    private lateinit var toolbar: Toolbar
    private lateinit var logoEmbedImg : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.brand_logo_layout)

        val dialogMultiplePermissionsListener: MultiplePermissionsListener =
            DialogOnAnyDeniedMultiplePermissionsListener.Builder
                .withContext(this)
                .withTitle("Storage read & write permission")
                .withMessage("Both read and write permission are needed to take pictures of your cat")
                .withButtonText(android.R.string.ok)
                .withIcon(R.drawable.brandup)
                .build()

        Dexter.withContext(this)
            .withPermissions(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .withListener(dialogMultiplePermissionsListener).check()


        val logoLayout = findViewById<LinearLayout>(R.id.logo_layout)
        val businessDetailLayout = findViewById<LinearLayout>(R.id.business_detail_lay)
        val frameImgLayout = findViewById<RelativeLayout>(R.id.frameImgLay)
        val nextBtn = findViewById<ImageButton>(R.id.log_next_btn)
        val detailInfoLayout = findViewById<LinearLayout>(R.id.detail_info_layout)
        val uploadButton = findViewById<ImageButton>(R.id.upload_logos)
        logoEmbedImg = findViewById(R.id.logo_embed_img)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(
            ContextCompat.getDrawable(
                this,
                R.drawable.arrow_back
            )
        )

        uploadButton.setOnClickListener {
            val intent = CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .getIntent(this@BrandLogoEdit)
            startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        }

        toolbar.setNavigationOnClickListener(View.OnClickListener {
            if (businessDetailLayout.isVisible) {
                val firstObject = ObjectAnimator.ofFloat(
                    businessDetailLayout,
                    "translationX",
                    16f,
                    1000f
                ).apply {
                    duration = 500

                }

                val secObject =
                    ObjectAnimator.ofFloat(detailInfoLayout, "translationY", -60f, -1f).apply {
                        duration = 500

                    }

                AnimatorSet().apply {
                    play(secObject).before(firstObject)
                    start()
                    doOnEnd {
                        businessDetailLayout.visibility = View.GONE
                        logoLayout.visibility = View.VISIBLE
                    }
                }

                ValueAnimatorUtil.valueAnimatorScaleUtil(0.8f, 1f, frameImgLayout)


            } else {
                super.onBackPressed()
            }
        })

        nextBtn.setOnClickListener(View.OnClickListener {
            if (logoLayout.isVisible) {
                logoLayout.visibility = View.GONE
                businessDetailLayout.visibility = View.VISIBLE
                ValueAnimatorUtil.valueAnimatorScaleUtil(1f, 0.8f, frameImgLayout)

                val firstObject = ObjectAnimator.ofFloat(
                    businessDetailLayout,
                    "translationX",
                    600f,
                    16f
                ).apply {
                    duration = 500

                }

                val secObject =
                    ObjectAnimator.ofFloat(detailInfoLayout, "translationY", 1f, -60f).apply {
                        duration = 500

                    }

                AnimatorSet().apply {
                    play(firstObject).before(secObject)
                    start()
                }
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val resultUri: Uri = result.uri
                logoEmbedImg.setImageURI(resultUri)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }
}