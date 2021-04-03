package com.vipcodeerror.brandup.ui.main.view.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.target.Target.SIZE_ORIGINAL
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.data.api.ApiHelper
import com.vipcodeerror.brandup.data.api.ApiServiceImpl
import com.vipcodeerror.brandup.data.model.BottomBannerModel
import com.vipcodeerror.brandup.data.model.SliderItem
import com.vipcodeerror.brandup.data.model.home_modal.ApiHomeDataResponse
import com.vipcodeerror.brandup.data.model.home_modal.HomeModel
import com.vipcodeerror.brandup.ui.base.ViewModelFactory
import com.vipcodeerror.brandup.ui.main.adapter.BottomBannerAdapter
import com.vipcodeerror.brandup.ui.main.adapter.ClickOnFrameUrl
import com.vipcodeerror.brandup.ui.main.adapter.FrameSelectorAdapter
import com.vipcodeerror.brandup.ui.main.adapter.FrameSliderAdapter
import com.vipcodeerror.brandup.ui.main.viewmodel.MainViewModel
import com.vipcodeerror.brandup.util.Resource
import com.vipcodeerror.brandup.util.SharedPreferenceUtil
import com.vipcodeerror.brandup.util.Status

class BottomFrameSelectorActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var sharedPreferenceUtil : SharedPreferenceUtil
    private lateinit var frameSelectorRecycler : RecyclerView
    private lateinit var backFrame : ImageView
    private lateinit var bottomFrame: ImageView
    private lateinit var logoFrame : ImageView
    private lateinit var lLayoutRoot : LinearLayout
    private lateinit var lurkingCatAnim : LottieAnimationView

    private lateinit var frameSelectorAdapter: BottomBannerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bottom_frame_selector)

        mainViewModel = setupViewModel()
        sharedPreferenceUtil = SharedPreferenceUtil(this)


        val toolbar: Toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowCustomEnabled(false)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        toolbar.title = "Select Your Pref Image"

        backFrame = findViewById(R.id.back_frame)
        bottomFrame = findViewById(R.id.bottom_frame)
        logoFrame = findViewById(R.id.logo_img)
        frameSelectorRecycler = findViewById(R.id.frame_selector_recycler)
        lLayoutRoot = findViewById(R.id.r_l_12345)
        lurkingCatAnim = findViewById(R.id.lurking_cat_anim)

        requestForImageGeneration(mainViewModel, sharedPreferenceUtil.getValueString("user_id").toString(),
                sharedPreferenceUtil.getValueString("pref_buss").toString(), sharedPreferenceUtil.getValueString("token").toString())
    }

    private fun setupViewModel() :MainViewModel {
        return  ViewModelProviders.of(
                this,
                ViewModelFactory(ApiHelper(ApiServiceImpl()))
        ).get(MainViewModel::class.java)
    }


    private fun requestForImageGeneration(mVModel: MainViewModel, user_id: String, pref_id: String, token: String) {
        mVModel.requestForImageGeneration(user_id, pref_id, token).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let {
                        val catdata = it.status
                        if (catdata){
                            getBottomBannerData(mVModel, pref_id, token)
                        }
                    }
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {

                }
            }
        })
    }

    private fun getBottomBannerData(mVModel: MainViewModel, pref_id: String, token: String) {
        mVModel.getBottomBannerDatas(pref_id, token).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let {
                        val catdata = it.data
                        frameRecycler(catdata.toMutableList())
                        Glide.with(this@BottomFrameSelectorActivity)
                                .load("https://d4f9k68hk754p.cloudfront.net/fit-in/800x800/images/48074.jpg")
                                .into(
                                        backFrame
                                )
                        Glide.with(this@BottomFrameSelectorActivity)
                                .load("https://d4f9k68hk754p.cloudfront.net/fit-in/300x400/images/"+ sharedPreferenceUtil.getValueString("logoUrl").toString())
                                .into(logoFrame)

                        Glide.with(this@BottomFrameSelectorActivity).load("https://d4f9k68hk754p.cloudfront.net/fit-in/900x400/${catdata[0].urlBottomBanner}").apply( RequestOptions()
                            .fitCenter()
                            .format(DecodeFormat.PREFER_ARGB_8888)
                            .override(SIZE_ORIGINAL))
                            .into(bottomFrame);

                        lurkingCatAnim.visibility = View.GONE
                        lLayoutRoot.visibility = View.VISIBLE
                    }
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {

                }
            }
        })
    }
    private fun frameRecycler(hData: MutableList<BottomBannerModel>){

        frameSelectorAdapter = BottomBannerAdapter(this, hData)

        frameSelectorRecycler.adapter = frameSelectorAdapter
        frameSelectorRecycler.layoutManager = GridLayoutManager(
                this,
                3,
                LinearLayoutManager.VERTICAL,
                false
        )

        frameSelectorAdapter.clickOnFrameUrl = object : BottomBannerAdapter.ClickOnFrameUrl {
            override fun setUrlImage(url: String) {
                Glide.with(this@BottomFrameSelectorActivity).load("https://d4f9k68hk754p.cloudfront.net/fit-in/900x400/$url").apply( RequestOptions()
                        .fitCenter()
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .override(SIZE_ORIGINAL))
                        .into(bottomFrame);
            }
        }
    }
}