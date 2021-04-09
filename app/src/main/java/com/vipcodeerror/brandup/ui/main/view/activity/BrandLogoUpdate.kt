package com.vipcodeerror.brandup.ui.main.view.activity

import android.Manifest
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.Toolbar
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
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
import com.vipcodeerror.brandup.ui.main.viewmodel.MainViewModel
import com.vipcodeerror.brandup.util.SharedPreferenceUtil
import com.vipcodeerror.brandup.util.Status
import com.vipcodeerror.brandup.util.ValueAnimatorUtil
import java.io.File

class BrandLogoUpdate : AppCompatActivity(){

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

    private  var actualUri: String? = null

    private var logoUrlTemp = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.brand_logo_update_layout)

        sharedPreferenceUtil = SharedPreferenceUtil(this)

        var bId = intent.getStringExtra("b_id")

        saveBtn = findViewById(R.id.save_btn)

        emailTxt = findViewById(R.id.business_email)
        phoneTxt = findViewById(R.id.business_phone)
        locationTxt = findViewById(R.id.bussiness_location)
        addrTxt = findViewById(R.id.business_addr)
        businessNameTxt = findViewById(R.id.bussiness_name)
        websiteNameTxt = findViewById(R.id.business_website)
        loadingHand = findViewById(R.id.loading_hand_anim)

        setupViewModel()

        getBusinnessForHomeData(mainViewModel, sharedPreferenceUtil.getValueString("user_id").toString(),
                bId.toString(), sharedPreferenceUtil.getValueString("token").toString());

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
                    .getIntent(this@BrandLogoUpdate)
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
                        nextBtn.visibility = View.VISIBLE
                        saveBtn.visibility = View.GONE
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

                nextBtn.visibility = View.GONE
                saveBtn.visibility = View.VISIBLE
            }
        })

        saveBtn.setOnClickListener(View.OnClickListener {


            val token = sharedPreferenceUtil.getValueString("token").toString()
            if (actualUri == null){
                var bName = businessNameTxt.text.toString()
                var bEmail = emailTxt.text.toString()
                var bPhone = phoneTxt.text.toString()
                var bLoc = locationTxt.text.toString()
                var bAddr = addrTxt.text.toString()
                var bWeb = websiteNameTxt.text.toString()

                val userId = sharedPreferenceUtil.getValueString("user_id").toString()
                val catId = intent.getStringExtra("cat_id").toString()
                postUpdateBusinessDetails(bId!!, bName, bPhone, bAddr, bEmail, bWeb, logoUrlTemp, bLoc, userId , catId, token)

            }else {
                val file = File(actualUri)
                uploadLogoUrl(bId!!, file, token)
            }

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
                logoEmbedImg.background = null
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error = result.error
            }
        }
    }

    private fun postUpdateBusinessDetails(user_id: String, bussName : String, phone: String, address : String, email: String, webN : String,
                                    logoUrl: String, location: String,
                                    belongToWhichUser : String, catIdBelongTo: String, token: String){

        mainViewModel.postUpdateBussDetailsData(user_id, bussName, phone, address,email, webN ,logoUrl, location,
                belongToWhichUser, catIdBelongTo, token).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let {
                        var catdata = it.status
                        Toast.makeText(this@BrandLogoUpdate, "Your Business Details Updated !!!", Toast.LENGTH_SHORT).show()
                        finish()
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

    private fun uploadLogoUrl(user_id: String, logosUrl : File, token : String){
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
                        postUpdateBusinessDetails(user_id, bName, bPhone, bAddr, bEmail, bWeb,  it.imageUrl , bLoc, userId , catId, token)
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
                        startActivity(Intent(this@BrandLogoUpdate, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                        finish();
                    }
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {
                    startActivity(Intent(this@BrandLogoUpdate, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))
                    finish();
                }
            }
        })
    }

    private fun getBusinnessForHomeData(
            mVModel: MainViewModel,
            catId: String,
            id: String,
            token: String
    ) {
        mVModel.getBussinessDetailsForHome(catId, id, token).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let {
                        var data = it.data
                        emailTxt.setText(data[0].email)
                        phoneTxt.setText(data[0].bPhone)
                        locationTxt.setText(data[0].bLocation)
                        addrTxt.setText(data[0].bAddress)
                        businessNameTxt.setText(data[0].bName)
                        websiteNameTxt.setText(data[0].webName)
                        logoUrlTemp = data[0].logoUrl
                        Glide.with(this@BrandLogoUpdate).load("https://d4f9k68hk754p.cloudfront.net/fit-in/300x400/images/" + data[0].logoUrl).into(logoEmbedImg)
                    }
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {

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