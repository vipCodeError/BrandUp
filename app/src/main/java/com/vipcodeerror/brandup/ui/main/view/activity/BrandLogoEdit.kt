package com.vipcodeerror.brandup.ui.main.view.activity

import android.Manifest
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.Toolbar
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.airbnb.lottie.LottieAnimationView
import com.google.android.material.textfield.TextInputEditText
import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.multi.DialogOnAnyDeniedMultiplePermissionsListener
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.data.api.ApiHelper
import com.vipcodeerror.brandup.data.api.ApiServiceImpl
import com.vipcodeerror.brandup.ui.base.ViewModelFactory
import com.vipcodeerror.brandup.ui.main.adapter.PopularCategoryAdapter
import com.vipcodeerror.brandup.ui.main.viewmodel.MainViewModel
import com.vipcodeerror.brandup.util.SharedPreferenceUtil
import com.vipcodeerror.brandup.util.Status
import com.vipcodeerror.brandup.util.ValueAnimatorUtil
import java.io.File
import java.net.URI


class BrandLogoEdit : AppCompatActivity(){

    private lateinit var toolbar: Toolbar
    private lateinit var logoEmbedImg : ImageView
    private lateinit var mainViewModel: MainViewModel
    private lateinit var saveBtn : AppCompatButton
    private lateinit var emailTxt : TextInputEditText
    private lateinit var phoneTxt : TextInputEditText
    private lateinit var locationTxt : TextInputEditText
    private lateinit var addrTxt : TextInputEditText
    private lateinit var businessNameTxt : TextInputEditText
    private lateinit var websiteNameTxt : TextInputEditText
    private lateinit var loadingHand : LottieAnimationView

    private lateinit var sharedPreferenceUtil : SharedPreferenceUtil

    private lateinit var actualUri: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.brand_logo_layout)

        sharedPreferenceUtil = SharedPreferenceUtil(this)

        saveBtn = findViewById(R.id.save_btn)

        emailTxt = findViewById(R.id.business_email)
        phoneTxt = findViewById(R.id.business_phone)
        locationTxt = findViewById(R.id.bussiness_location)
        addrTxt = findViewById(R.id.business_addr)
        businessNameTxt = findViewById(R.id.bussiness_name)
        websiteNameTxt = findViewById(R.id.business_website)
        loadingHand = findViewById(R.id.loading_hand_anim)
        setupViewModel()

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
                        nextBtn.visibility = VISIBLE
                        saveBtn.visibility = GONE
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

                nextBtn.visibility = GONE
                saveBtn.visibility = VISIBLE
            }
        })

        saveBtn.setOnClickListener(View.OnClickListener {
            val file = File(actualUri)
            val token = sharedPreferenceUtil.getValueString("token").toString()
            uploadLogoUrl(file, token)
            loadingHand.visibility = View.VISIBLE

        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            val result = CropImage.getActivityResult(data)
            if (resultCode == RESULT_OK) {
                val resultUri: Uri = result.uri
                actualUri = resultUri.path!!
                logoEmbedImg.setImageURI(resultUri)
                logoEmbedImg.setBackgroundResource(0)
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }

    private fun postBusinessDetails(bussName : String, phone: String, address : String, email: String, webN : String,
                                    logoUrl: String, location: String,
                                    belongToWhichUser : String, catIdBelongTo: String, token: String){

        mainViewModel.postBussDetailsData(bussName, phone, address,email, webN ,logoUrl, location,
                belongToWhichUser, catIdBelongTo, token).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let {
                        var catdata = it.status
                        setUserBussPref(phone,
                            it.id.toString(), sharedPreferenceUtil.getValueString("token").toString())

                    }
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    // Toast.makeText(this@OtpVerficationActivity, "Token ID is :: " + it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun uploadLogoUrl(logosUrl : File, token : String){
        mainViewModel.uploadImageData(logosUrl, token).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let {
                        var bName = businessNameTxt.text.toString()
                        var bEmail = emailTxt.text.toString()
                        var bPhone = phoneTxt.text.toString()
                        var bLoc = locationTxt.text.toString()
                        var bAddr = addrTxt.text.toString()
                        var bWeb = websiteNameTxt.text.toString()

                        val userId = sharedPreferenceUtil.getValueString("user_id").toString()
                        val catId = intent.getStringExtra("cat_id").toString()
                        postBusinessDetails(bName, bPhone, bAddr, bEmail, bWeb,  it.imageUrl , bLoc, userId , catId, token)
                    }
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    // Toast.makeText(this@OtpVerficationActivity, "Token ID is :: " + it.message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun setUserBussPref(userId:String, pref_id: String, token: String){
        mainViewModel.postUserBussPref(userId, pref_id, token).observe(this, Observer{
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let {
                        loadingHand.visibility = View.GONE
                        startActivity(Intent(this@BrandLogoEdit, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        finish();
                    }
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    startActivity(Intent(this@BrandLogoEdit, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    finish();
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