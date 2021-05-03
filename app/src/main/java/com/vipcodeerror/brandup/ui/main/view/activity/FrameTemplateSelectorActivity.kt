package com.vipcodeerror.brandup.ui.main.view.activity

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.target.Target
import com.bumptech.glide.request.transition.Transition
import com.mazenrashed.dotsindicator.DotsIndicator
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
import com.vipcodeerror.brandup.ui.main.adapter.*
import com.vipcodeerror.brandup.ui.main.viewmodel.MainViewModel
import com.vipcodeerror.brandup.util.AppUtils
import com.vipcodeerror.brandup.util.Resource
import com.vipcodeerror.brandup.util.SharedPreferenceUtil
import com.vipcodeerror.brandup.util.Status


class FrameTemplateSelectorActivity : AppCompatActivity(){

    private lateinit var topFrameRecycler : RecyclerView
    private lateinit var subCatTitleRecycler : RecyclerView
    private lateinit var frameSelectorRecycler : RecyclerView
    private lateinit var backFrame : ImageView
    private lateinit var logoImg : ImageView

    private lateinit var frameSelectorAdapter: FrameSelectorAdapter
    private lateinit var mainViewModel: MainViewModel
    private lateinit var sharedPreferenceUtil : SharedPreferenceUtil

    private lateinit var isSubShown : String
    private lateinit var subId : String
    private lateinit var catId : String

    private lateinit var currentDotIndicator : DotsIndicator

    private lateinit var frameLayout : RelativeLayout

    private lateinit var backImgUrl : String
    private lateinit var frameImgUrl : String

    private var isDownloadable : Boolean? = false

    // private lateinit var topFrameAdapter : TopFrameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.frame_selector_activity)
        mainViewModel = setupViewModel()
        sharedPreferenceUtil = SharedPreferenceUtil(this)

        val toolbar: Toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayShowCustomEnabled(true)
        supportActionBar!!.setCustomView(R.layout.custom_toolbar)

        val downloadImgView = supportActionBar!!.customView.findViewById<ImageView>(R.id.download)
        val shareImgView = supportActionBar!!.customView.findViewById<ImageView>(R.id.share)
        frameLayout = findViewById(R.id.frame_layout)

        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        isSubShown = intent.getStringExtra("is_sub_shown").toString()
        catId = intent.getStringExtra("cat_id").toString()
        subId = intent.getStringExtra("sub_id").toString()

       // topFrameRecycler = findViewById(R.id.top_frame_recycler)
        backFrame = findViewById(R.id.back_frame)
        subCatTitleRecycler = findViewById(R.id.sub_cat_recycler)
        frameSelectorRecycler = findViewById(R.id.frame_selector_recycler)
        logoImg = findViewById(R.id.logo_img)
        currentDotIndicator = findViewById(R.id.frame_count)

        trendingTitle()

        if (isSubShown == "0"){
            getHomeSelectedUniversal(
                mainViewModel,
                catId,
                sharedPreferenceUtil.getValueString("token").toString()
            )
        }else {
            getHomeSubSelectedUniversal(
                mainViewModel,
                subId,
                sharedPreferenceUtil.getValueString("token").toString()
            )
        }

        shareImgView.setOnClickListener {
            Toast.makeText(this@FrameTemplateSelectorActivity, "Sharing ...", Toast.LENGTH_SHORT).show()
            getHdImageData(mainViewModel, backImgUrl, frameImgUrl,sharedPreferenceUtil.getValueString("logoUrl").toString(), sharedPreferenceUtil.getValueString("token").toString())
        }

        downloadImgView.setOnClickListener {
            Toast.makeText(this@FrameTemplateSelectorActivity, "Downloading ...", Toast.LENGTH_SHORT).show()
            isDownloadable = true;
            getHdImageData(mainViewModel, backImgUrl, frameImgUrl, sharedPreferenceUtil.getValueString("logoUrl").toString(), sharedPreferenceUtil.getValueString("token").toString())
        }

//        getBottomBannerData(mainViewModel, sharedPreferenceUtil.getValueString("pref_buss").toString(),
//            sharedPreferenceUtil.getValueString("token").toString())

        var urlList = sharedPreferenceUtil.getValueString("selected_frame").toString()
        if (urlList == null || urlList == ""){
            val showWarnings = AlertDialog.Builder(this@FrameTemplateSelectorActivity)
            showWarnings.setMessage("You did not select frame. Press Ok to select frame.")
            showWarnings.setCancelable(false)
            showWarnings.setPositiveButton("OK",object: DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    startActivity(Intent(this@FrameTemplateSelectorActivity, BottomFrameSelectorActivity::class.java))
                    finish()
                }
            })
            showWarnings.show()
        }else {
            topFrame(urlList.split(",").toList())
            Glide.with(this@FrameTemplateSelectorActivity)
                    .load("https://d4f9k68hk754p.cloudfront.net/fit-in/712x712/images/"+ sharedPreferenceUtil.getValueString("logoUrl").toString())
                    .into(logoImg)
        }

    }

    private fun topFrame(urlList: List<String>){
        val sliderAdapter = FrameSliderAdapter(this)
        val sliderView: SliderView = findViewById(R.id.top_frame_recycler)
        sliderView.setSliderAdapter(sliderAdapter)
//      sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderAdapter.addItem(urlList.toMutableList())
        //currentPosTxt.text = (sliderView.currentPagePosition + 1).toString() + "/" + urlList.size
        currentDotIndicator.initDots(urlList.size)
        frameImgUrl = urlList[0]
        currentDotIndicator.setDotSelection(0)
        sliderView.setCurrentPageListener { position ->
            frameImgUrl = urlList[position]
            currentDotIndicator.setDotSelection(position)
           // currentPosTxt.text = (position + 1).toString() + "/" + urlList.size
        }

    }

    private fun frameRecycler(hData: MutableList<HomeModel>){

        backImgUrl = hData[0].urlImage

        frameSelectorAdapter = FrameSelectorAdapter(this, hData)

        frameSelectorRecycler.adapter = frameSelectorAdapter
        frameSelectorRecycler.layoutManager = GridLayoutManager(
            this,
            2,
            LinearLayoutManager.VERTICAL,
            false
        )
        frameSelectorAdapter.clickOnFrameUrl = object : ClickOnFrameUrl {
            override fun setUrlImage(url: String) {
                backImgUrl = url
                Glide.with(this@FrameTemplateSelectorActivity).load("https://d4f9k68hk754p.cloudfront.net/fit-in/712x712/images/$url").into(
                    backFrame
                )
            }
        }
    }

    private fun trendingTitle(){
//        var catListStr = mutableListOf<String>(
//            "All", "Marketing and Advertising Agency",
//            "Clothes", "Agriculture", "Education", "Jewelery", "Art and Design", "Mobile Store"
//        );
//        val trendingTitleAdapter  = TrendingTitleAdapter(catListStr)
//        subCatTitleRecycler.adapter = trendingTitleAdapter
//        subCatTitleRecycler.layoutManager = LinearLayoutManager(
//            this,
//            LinearLayoutManager.HORIZONTAL,
//            false
//        )
    }

    private fun setupViewModel() :MainViewModel {
        return  ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(ApiServiceImpl()))
        ).get(MainViewModel::class.java)
    }

    private fun getHomeSelectedUniversal(mVModel: MainViewModel, catId: String, token: String) {
        val homeData = MutableLiveData<Resource<ApiHomeDataResponse>>()
        mVModel.getHomeDataUniversal(homeData, catId, token).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let {
                        val catdata = it.data
                        frameRecycler(catdata.toMutableList())
                        Glide.with(this@FrameTemplateSelectorActivity)
                            .load("https://d4f9k68hk754p.cloudfront.net/fit-in/300x400/images/${catdata[0].urlImage}")
                            .into(
                                backFrame
                            )
                    }
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {

                }
            }
        })
    }

    private fun getHomeSubSelectedUniversal(mVModel: MainViewModel, subId: String, token: String) {
        val homeData = MutableLiveData<Resource<ApiHomeDataResponse>>()
        mVModel.getHomeSubDataUniversal(homeData, subId, token).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let {
                        val catdata = it.data
                        frameRecycler(catdata.toMutableList())
                        Glide.with(this@FrameTemplateSelectorActivity)
                            .load("https://d4f9k68hk754p.cloudfront.net/fit-in/300x400/images/${catdata[0].urlImage}")
                            .into(
                                backFrame
                            )
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
                    if(it.data?.data?.size == 0){

                    }else {
                        it.data?.let {
                            val catdata = it.data
                            //topFrame(catdata)
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

    private fun getHdImageData(mVModel: MainViewModel, backImg: String, frameImg: String, logoImg:String, token: String){
        mVModel.getHdImage(backImg, frameImg, logoImg, token).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    Glide.with(this)
                            .asBitmap()
                            .load("https://d4f9k68hk754p.cloudfront.net/fit-in/712x712/" +it.data?.url)
                            .into(object : CustomTarget<Bitmap>(){
                                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                                    if (isDownloadable == true){
                                        AppUtils.storeDownloadedImage(this@FrameTemplateSelectorActivity, resource)
                                        isDownloadable = false
                                    }else {
                                        Toast.makeText(this@FrameTemplateSelectorActivity, "Downloading ...", Toast.LENGTH_SHORT).show()
                                        AppUtils.launchShareIntentWithUri(this@FrameTemplateSelectorActivity, resource)

                                    }

                                    //imageView.setImageBitmap(resource)
                                }

                                override fun onLoadCleared(placeholder: Drawable?) {
                                    // this is called when imageView is cleared on lifecycle call or for
                                    // some other reason.
                                    // if you are referencing the bitmap somewhere else too other than this imageView
                                    // clear it here as you can no longer have the bitmap
                                }
                            })
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {

                }
            }
        })
    }

}