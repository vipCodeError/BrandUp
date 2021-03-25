package com.vipcodeerror.brandup.ui.main.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.data.api.ApiHelper
import com.vipcodeerror.brandup.data.api.ApiServiceImpl
import com.vipcodeerror.brandup.data.model.SliderItem
import com.vipcodeerror.brandup.data.model.home_modal.ApiHomeDataResponse
import com.vipcodeerror.brandup.data.model.home_modal.HomeModel
import com.vipcodeerror.brandup.data.model.home_modal.HomeSelectedModel
import com.vipcodeerror.brandup.ui.base.ViewModelFactory
import com.vipcodeerror.brandup.ui.main.adapter.TopTrendingSliderAdapter
import com.vipcodeerror.brandup.ui.main.adapter.TrendingTitleAdapter
import com.vipcodeerror.brandup.ui.main.adapter.home_related_adapter.FirstAdapter
import com.vipcodeerror.brandup.ui.main.viewmodel.MainViewModel
import com.vipcodeerror.brandup.util.Resource
import com.vipcodeerror.brandup.util.SharedPreferenceUtil
import com.vipcodeerror.brandup.util.Status


class HomePageFragment : Fragment() {

    lateinit var staticAdsImageView : ImageView
    lateinit var staticAdsLayout : CardView
    lateinit var trendingRecyclerView : RecyclerView
    private lateinit var mainViewModel: MainViewModel
    private lateinit var sharedPreferenceUtil : SharedPreferenceUtil
    private lateinit var shadowViewObject : View

    private lateinit var firstOneTitle: TextView
    private lateinit var secondOneTitle: TextView
    private lateinit var thirdOneTitle: TextView
    private lateinit var fourthOneTitle: TextView
    private lateinit var fifthOneTitle: TextView
    private lateinit var sixthOneTitle: TextView
    private lateinit var seventhOneTitle: TextView
    private lateinit var eighthOneTitle: TextView

    private lateinit var firstOnLayout : LinearLayout
    private lateinit var secondOnLayout : LinearLayout
    private lateinit var thirdOnLayout : LinearLayout
    private lateinit var fourthOnLayout : LinearLayout
    private lateinit var fifthOnLayout : LinearLayout
    private lateinit var sixthOnLayout : LinearLayout
    private lateinit var seventhOnLayout : LinearLayout
    private lateinit var eighthOnLayout : LinearLayout

    var subCount = 0
    var rootCount = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        var view = inflater.inflate(R.layout.home_page_fragment, container, false)

        mainViewModel = setupViewModel()

        firstOneTitle = view.findViewById(R.id.first_one_txt)
        secondOneTitle = view.findViewById(R.id.second_one_txt)
        thirdOneTitle = view.findViewById(R.id.third_on_txt)
        fourthOneTitle = view.findViewById(R.id.fourth_one_txt)
        fifthOneTitle = view.findViewById(R.id.fifth_one_txt)
        sixthOneTitle = view.findViewById(R.id.sixth_one_txt)
        seventhOneTitle = view.findViewById(R.id.seventh_one_txt)
        eighthOneTitle = view.findViewById(R.id.eighth_txt)

        firstOnLayout = view.findViewById(R.id.first_one_layout)
        secondOnLayout = view.findViewById(R.id.second_one_layout)
        thirdOnLayout = view.findViewById(R.id.third_one_layout)
        fourthOnLayout = view.findViewById(R.id.fourth_one_layout)
        fifthOnLayout = view.findViewById(R.id.fifth_one_layout)
        sixthOnLayout = view.findViewById(R.id.sixth_one_layout)
        seventhOnLayout = view.findViewById(R.id.seventh_one_layout)
        eighthOnLayout = view.findViewById(R.id.eighth_one_layout)

        sharedPreferenceUtil = SharedPreferenceUtil(container!!.context)

        shadowViewObject = view

        getHomeObserver(sharedPreferenceUtil.getValueString("token").toString())

        staticAdsImageView = view.findViewById(R.id.static_ads)
        staticAdsLayout = view.findViewById(R.id.static_ads_layout)
        trendingRecyclerView = view.findViewById(R.id.trending_title_recycler)

        staticAdsLayout.startAnimation(bubbleAnimation())

        sliderAds(view)
        staticAds()
        trendingTitle()

        return view
    }

    private fun bubbleAnimation() : ScaleAnimation{
        val scale = ScaleAnimation(
            0.95f,
            1f,
            0.95f,
            1f,
            ScaleAnimation.RELATIVE_TO_SELF,
            .5f,
            ScaleAnimation.RELATIVE_TO_SELF,
            .5f
        )
        scale.duration = 2000
        scale.interpolator = BounceInterpolator()
        scale.repeatMode = ScaleAnimation.RESTART
        scale.repeatCount = ScaleAnimation.INFINITE
        return scale
    }

    private fun sliderAds(view: View){
        val sliderAdapter = TopTrendingSliderAdapter(requireActivity())
        val sliderView: SliderView = view.findViewById(R.id.imageSlider)
        sliderView.setSliderAdapter(sliderAdapter)
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.startAutoCycle();

        var listUrls = mutableListOf<SliderItem>()
        listUrls.add(SliderItem("https://www.visme.co/wp-content/uploads/2020/08/Visme4-Free-Banner-Maker.jpg"))
        listUrls.add(SliderItem("https://www.shamsherkhan.com/wp-content/uploads/2020/04/og-bannersnack_v2.png"))
        listUrls.add(SliderItem("https://bannerboo.com/upload/iblock/9e9/export_banners_to_mp4_bannerboo.jpg"))
        sliderAdapter.addItem(listUrls)

    }

    private fun staticAds(){
        Glide.with(this).load("https://www.shamsherkhan.com/wp-content/uploads/2020/04/og-bannersnack_v2.png").into(
            staticAdsImageView
        )
    }

    private fun trendingTitle(){
        var catListStr = mutableListOf<String>("Marketing and Advertising Agency",
            "Clothes", "Agriculture", "Education", "Jewelery", "Art and Design", "Mobile Store",
            "Advocate", "Auto Mobile", "FMCG", "Real Estate", "Ceramic", "Electrical",
            "Building Traders", "Furniture", "Textile Industry", "Insurance", "Finance",
            "Photographer", "Tour and Travels", "Information Technology", "Graphic Designing",
            "Dairy & Sweets", "Consultant", "Computer Hardware", "Restaurant, Catering", "Solar and Power Panel",
            "Social Activist", "Steel and Aluminium", "Events", "Clinic and Hospital", "Aryuvedic",
            "Agarbatti", "Pharmaceutical", "Hotel", "Security Surveillance", "Home Appliances",
            "Interior", "Beauty parlor and salon");
        val trendingTitleAdapter  = TrendingTitleAdapter(catListStr)
        trendingRecyclerView.adapter = trendingTitleAdapter
        trendingRecyclerView.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun firstRecycler(view : View, resId : Int, lLayout: View, dataHome : MutableList<HomeModel>){
        // test
        var firstAdapterList = mutableListOf<HomeModel>()
        firstAdapterList.addAll(dataHome)

        lLayout.visibility = View.VISIBLE
        val recyclerFirst = view.findViewById<RecyclerView>(resId)
        var firstAdapter = FirstAdapter(requireActivity(), firstAdapterList)
        recyclerFirst.adapter = firstAdapter
        recyclerFirst.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setupViewModel() :MainViewModel {
        return  ViewModelProviders.of(
                this,
                ViewModelFactory(ApiHelper(ApiServiceImpl()))
        ).get(MainViewModel::class.java)
    }


    private fun getHomeObserver(token: String) {
        mainViewModel.getSelectedHomeData(token).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let {
                        var catdata = it.data
                        for(cdata : HomeSelectedModel in catdata){
                            val mVModel = setupViewModel()
                            if (cdata.isSubShown == "0"){
                                getHomeSelectedUniversal(mVModel, cdata.catId, sharedPreferenceUtil.getValueString("token").toString())
                            }else if(cdata.isSubShown == "1") {
                                getHomeSubSelectedUniversal(mVModel, cdata.subId, sharedPreferenceUtil.getValueString("token").toString())
                            }
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

    private fun getHomeSelectedUniversal(mVModel : MainViewModel, catId: String, token: String) {
        val homeData = MutableLiveData<Resource<ApiHomeDataResponse>>()
        mVModel.getHomeDataUniversal(homeData, catId, token).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let {

                        if(rootCount == 0){
                            var catdata = it.data
                            val cName = catdata[0].catName
                            firstOneTitle.text = cName
                            firstRecycler(shadowViewObject, R.id.first_one_recyclerview, firstOnLayout ,catdata.toMutableList())
                        } else  if(rootCount == 1) {
                            var catdata = it.data
                            val cName = catdata[0].catName
                            secondOneTitle.text = cName
                            firstRecycler(shadowViewObject, R.id.second_one_recyclerview, secondOnLayout,catdata.toMutableList())
                        } else if(rootCount == 2) {
                            var catdata = it.data
                            val cName = catdata[0].catName
                            thirdOneTitle.text = cName
                            firstRecycler(shadowViewObject, R.id.third_one_recyclerview, thirdOnLayout,catdata.toMutableList())
                        } else if(rootCount == 3) {
                            var catdata = it.data
                            val cName = catdata[0].catName
                            thirdOneTitle.text = cName
                            firstRecycler(shadowViewObject, R.id.fourth_one_recyclerview, fourthOnLayout,catdata.toMutableList())
                        } else if(rootCount == 4) {
                            var catdata = it.data
                            val cName = catdata[0].catName
                            thirdOneTitle.text = cName
                            firstRecycler(shadowViewObject, R.id.fifth_one_recyclerview, fifthOnLayout,catdata.toMutableList())
                        } else if(rootCount == 5) {
                            var catdata = it.data
                            val cName = catdata[0].catName
                            thirdOneTitle.text = cName
                            firstRecycler(shadowViewObject, R.id.sixth_one_recyclerview, sixthOnLayout,catdata.toMutableList())
                        } else if(rootCount == 6) {
                            var catdata = it.data
                            val cName = catdata[0].catName
                            thirdOneTitle.text = cName
                            firstRecycler(shadowViewObject, R.id.seventh_one_recyclerview, seventhOnLayout,catdata.toMutableList())
                        } else if(rootCount == 7) {
                            var catdata = it.data
                            val cName = catdata[0].catName
                            thirdOneTitle.text = cName
                            firstRecycler(shadowViewObject, R.id.eighth_one_recyclerview, eighthOnLayout,catdata.toMutableList())
                        }
                        rootCount++
                    }
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {

                }
            }
        })
    }


    private fun getHomeSubSelectedUniversal(mVModel : MainViewModel, subId: String, token: String) {
        val homeData = MutableLiveData<Resource<ApiHomeDataResponse>>()
        mVModel.getHomeSubDataUniversal(homeData, subId, token).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let {

                        if(subCount == 0){
                            var catdata = it.data
                            val cName = catdata[0].subCatName
                            firstOneTitle.text = cName
                            firstRecycler(shadowViewObject, R.id.first_one_recyclerview, firstOnLayout ,catdata.toMutableList())
                        } else  if(subCount == 1) {
                            var catdata = it.data
                            val cName = catdata[0].subCatName
                            secondOneTitle.text = cName
                            firstRecycler(shadowViewObject, R.id.second_one_recyclerview, secondOnLayout,catdata.toMutableList())
                        } else if(subCount == 2) {
                            var catdata = it.data
                            val cName = catdata[0].subCatName
                            thirdOneTitle.text = cName
                            firstRecycler(shadowViewObject, R.id.third_one_recyclerview, thirdOnLayout,catdata.toMutableList())
                        } else if(subCount == 3) {
                            var catdata = it.data
                            val cName = catdata[0].subCatName
                            fourthOneTitle.text = cName
                            firstRecycler(shadowViewObject, R.id.fourth_one_recyclerview, fourthOnLayout,catdata.toMutableList())
                        } else if(subCount == 4) {
                            var catdata = it.data
                            val cName = catdata[0].subCatName
                            fourthOneTitle.text = cName
                            firstRecycler(shadowViewObject, R.id.fifth_one_recyclerview, fifthOnLayout,catdata.toMutableList())
                        } else if(subCount == 5) {
                            var catdata = it.data
                            val cName = catdata[0].subCatName
                            fourthOneTitle.text = cName
                            firstRecycler(shadowViewObject, R.id.sixth_one_recyclerview, sixthOnLayout,catdata.toMutableList())
                        } else if(subCount == 6) {
                            var catdata = it.data
                            val cName = catdata[0].subCatName
                            fourthOneTitle.text = cName
                            firstRecycler(shadowViewObject, R.id.seventh_one_recyclerview, seventhOnLayout,catdata.toMutableList())
                        } else if(subCount == 7) {
                            var catdata = it.data
                            val cName = catdata[0].subCatName
                            fourthOneTitle.text = cName
                            firstRecycler(shadowViewObject, R.id.eighth_one_recyclerview, eighthOnLayout,catdata.toMutableList())
                        }

                        subCount++
                    }
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {

                }
            }
        })
    }

}