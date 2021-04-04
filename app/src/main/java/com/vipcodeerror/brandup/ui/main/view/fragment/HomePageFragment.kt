package com.vipcodeerror.brandup.ui.main.view.fragment

import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.cardview.widget.CardView
import androidx.core.widget.NestedScrollView
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
import com.vipcodeerror.brandup.ui.main.adapter.*
import com.vipcodeerror.brandup.ui.main.view.activity.NotificationActivity
import com.vipcodeerror.brandup.ui.main.view.activity.SearchActivity
import com.vipcodeerror.brandup.ui.main.viewmodel.MainViewModel
import com.vipcodeerror.brandup.util.Resource
import com.vipcodeerror.brandup.util.SharedPreferenceUtil
import com.vipcodeerror.brandup.util.Status
import it.sephiroth.android.library.xtooltip.ClosePolicy
import it.sephiroth.android.library.xtooltip.Tooltip
import xyz.peridy.shimmerlayout.ShimmerLayout


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

    private lateinit var businessTitleTxt : TextView

    private lateinit var selectedBussinesLayout : LinearLayout

    private lateinit var shimmerLayout : ShimmerLayout
    private lateinit var nestedLayout : NestedScrollView

    private lateinit var searchIcon : ImageView
    private lateinit var notificationIcon : ImageView

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

        businessTitleTxt = view.findViewById(R.id.bussiness_name)
        selectedBussinesLayout = view.findViewById(R.id.selected_buss_layout)

        shimmerLayout = view.findViewById(R.id.shimmer_layout)
        nestedLayout = view.findViewById(R.id.root_nested_scroll_view)

        searchIcon = view.findViewById(R.id.search_icons)
        notificationIcon = view.findViewById(R.id.notification_icon)

        sharedPreferenceUtil = SharedPreferenceUtil(container!!.context)

        shadowViewObject = view

        getHomeObserver(sharedPreferenceUtil.getValueString("token").toString())

        staticAdsImageView = view.findViewById(R.id.static_ads)
        staticAdsLayout = view.findViewById(R.id.static_ads_layout)
        trendingRecyclerView = view.findViewById(R.id.trending_title_recycler)

        staticAdsLayout.startAnimation(bubbleAnimation())

        sliderAds(view)
        staticAds()
        geTrendingData(sharedPreferenceUtil.getValueString("token").toString())

        getBusinnessForHomeData(
            mainViewModel,
            sharedPreferenceUtil.getValueString("user_id").toString(),
            sharedPreferenceUtil.getValueString("pref_buss").toString(),
            sharedPreferenceUtil.getValueString("token").toString(),
        )

        selectedBussinesLayout.setOnClickListener {
            getBusinnessData(
                mainViewModel, sharedPreferenceUtil.getValueString("user_id").toString(),
                sharedPreferenceUtil.getValueString(
                    "token"
                ).toString(),
            )
        }

        searchIcon.setOnClickListener {
            requireActivity().startActivity(Intent(requireActivity(), SearchActivity::class.java))
        }

        notificationIcon.setOnClickListener {
            requireActivity().startActivity(
                Intent(
                    requireActivity(),
                    NotificationActivity::class.java
                )
            )
        }

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
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM)
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
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

    private fun universalDaynamicRecycler(
        view: View,
        resId: Int,
        lLayout: View,
        isSubShown: String,
        catId: String,
        subId: String,
        dataHome: MutableList<HomeModel>
    ){
        // test
        var firstAdapterList = mutableListOf<HomeModel>()
        firstAdapterList.addAll(dataHome)

        lLayout.visibility = View.VISIBLE
        val recyclerFirst = view.findViewById<RecyclerView>(resId)
        var firstAdapter = HomeCardAdapter(
            requireActivity(),
            isSubShown,
            catId,
            subId,
            firstAdapterList
        )
        recyclerFirst.adapter = firstAdapter
        recyclerFirst.layoutManager = LinearLayoutManager(
            requireActivity(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
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
                        for (cdata: HomeSelectedModel in catdata) {
                            val mVModel = setupViewModel()
                            if (cdata.isSubShown == "0") {
                                getHomeSelectedUniversal(
                                    mVModel, cdata.catId, sharedPreferenceUtil.getValueString(
                                        "token"
                                    ).toString()
                                )
                            } else if (cdata.isSubShown == "1") {
                                getHomeSubSelectedUniversal(
                                    mVModel, cdata.subId, sharedPreferenceUtil.getValueString(
                                        "token"
                                    ).toString()
                                )
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

    private fun getHomeSelectedUniversal(mVModel: MainViewModel, catId: String, token: String) {
        val homeData = MutableLiveData<Resource<ApiHomeDataResponse>>()
        mVModel.getHomeDataUniversal(homeData, catId, token).observe(this, Observer {
            when (it.status) {

                Status.SUCCESS -> {
                    shimmerLayout.visibility = View.GONE
                    nestedLayout.visibility = View.VISIBLE
                    it.data?.let {

                        if (rootCount == 0) {
                            var catdata = it.data
                            val cName = catdata[0].catName
                            firstOneTitle.text = cName
                            universalDaynamicRecycler(
                                shadowViewObject,
                                R.id.first_one_recyclerview,
                                firstOnLayout,
                                "0",
                                catId,
                                catdata[0].subId,
                                catdata.toMutableList()
                            )
                        } else if (rootCount == 1) {
                            var catdata = it.data
                            val cName = catdata[0].catName
                            secondOneTitle.text = cName
                            universalDaynamicRecycler(
                                shadowViewObject,
                                R.id.second_one_recyclerview,
                                secondOnLayout,
                                "0",
                                catId,
                                catdata[0].subId,
                                catdata.toMutableList()
                            )
                        } else if (rootCount == 2) {
                            var catdata = it.data
                            val cName = catdata[0].catName
                            thirdOneTitle.text = cName
                            universalDaynamicRecycler(
                                shadowViewObject,
                                R.id.third_one_recyclerview,
                                thirdOnLayout,
                                "0",
                                catId,
                                catdata[0].subId,
                                catdata.toMutableList()
                            )
                        } else if (rootCount == 3) {
                            var catdata = it.data
                            val cName = catdata[0].catName
                            fourthOneTitle.text = cName
                            universalDaynamicRecycler(
                                shadowViewObject,
                                R.id.fourth_one_recyclerview,
                                fourthOnLayout,
                                "0",
                                catId,
                                catdata[0].subId,
                                catdata.toMutableList()
                            )
                        } else if (rootCount == 4) {
                            var catdata = it.data
                            val cName = catdata[0].catName
                            fifthOneTitle.text = cName
                            universalDaynamicRecycler(
                                shadowViewObject,
                                R.id.fifth_one_recyclerview,
                                fifthOnLayout,
                                "0",
                                catId,
                                catdata[0].subId,
                                catdata.toMutableList()
                            )
                        } else if (rootCount == 5) {
                            var catdata = it.data
                            val cName = catdata[0].catName
                            sixthOneTitle.text = cName
                            universalDaynamicRecycler(
                                shadowViewObject,
                                R.id.sixth_one_recyclerview,
                                sixthOnLayout,
                                "0",
                                catId,
                                catdata[0].subId,
                                catdata.toMutableList()
                            )
                        } else if (rootCount == 6) {
                            var catdata = it.data
                            val cName = catdata[0].catName
                            seventhOneTitle.text = cName
                            universalDaynamicRecycler(
                                shadowViewObject,
                                R.id.seventh_one_recyclerview,
                                seventhOnLayout,
                                "0",
                                catId,
                                catdata[0].subId,
                                catdata.toMutableList()
                            )
                        } else if (rootCount == 7) {
                            var catdata = it.data
                            val cName = catdata[0].catName
                            eighthOneTitle.text = cName
                            universalDaynamicRecycler(
                                shadowViewObject,
                                R.id.eighth_one_recyclerview,
                                eighthOnLayout,
                                "0",
                                catId,
                                catdata[0].subId,
                                catdata.toMutableList()
                            )
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


    private fun getHomeSubSelectedUniversal(mVModel: MainViewModel, subId: String, token: String) {
        val homeData = MutableLiveData<Resource<ApiHomeDataResponse>>()
        mVModel.getHomeSubDataUniversal(homeData, subId, token).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    //selectedBussinesLayout.post { showToolTip(selectedBussinesLayout) }
                    shimmerLayout.visibility = View.GONE
                    nestedLayout.visibility = View.VISIBLE
                    it.data?.let {

                        if (subCount == 0) {
                            var catdata = it.data
                            val cName = catdata[0].subCatName
                            firstOneTitle.text = cName
                            universalDaynamicRecycler(
                                shadowViewObject,
                                R.id.first_one_recyclerview,
                                firstOnLayout,
                                "1",
                                subId,
                                catdata[0].subId,
                                catdata.toMutableList()
                            )
                        } else if (subCount == 1) {
                            var catdata = it.data
                            val cName = catdata[0].subCatName
                            secondOneTitle.text = cName
                            universalDaynamicRecycler(
                                shadowViewObject,
                                R.id.second_one_recyclerview,
                                secondOnLayout,
                                "1",
                                subId,
                                catdata[0].subId,
                                catdata.toMutableList()
                            )
                        } else if (subCount == 2) {
                            var catdata = it.data
                            val cName = catdata[0].subCatName
                            thirdOneTitle.text = cName
                            universalDaynamicRecycler(
                                shadowViewObject,
                                R.id.third_one_recyclerview,
                                thirdOnLayout,
                                "1",
                                subId,
                                catdata[0].subId,
                                catdata.toMutableList()
                            )
                        } else if (subCount == 3) {
                            var catdata = it.data
                            val cName = catdata[0].subCatName
                            fourthOneTitle.text = cName
                            universalDaynamicRecycler(
                                shadowViewObject,
                                R.id.fourth_one_recyclerview,
                                fourthOnLayout,
                                "1",
                                subId,
                                catdata[0].subId,
                                catdata.toMutableList()
                            )
                        } else if (subCount == 4) {
                            var catdata = it.data
                            val cName = catdata[0].subCatName
                            fifthOneTitle.text = cName
                            universalDaynamicRecycler(
                                shadowViewObject,
                                R.id.fifth_one_recyclerview,
                                fifthOnLayout,
                                "1",
                                subId,
                                catdata[0].subId,
                                catdata.toMutableList()
                            )
                        } else if (subCount == 5) {
                            var catdata = it.data
                            val cName = catdata[0].subCatName
                            sixthOneTitle.text = cName
                            universalDaynamicRecycler(
                                shadowViewObject,
                                R.id.sixth_one_recyclerview,
                                sixthOnLayout,
                                "1",
                                subId,
                                catdata[0].subId,
                                catdata.toMutableList()
                            )
                        } else if (subCount == 6) {
                            var catdata = it.data
                            val cName = catdata[0].subCatName
                            seventhOneTitle.text = cName
                            universalDaynamicRecycler(
                                shadowViewObject,
                                R.id.seventh_one_recyclerview,
                                seventhOnLayout,
                                "1",
                                subId,
                                catdata[0].subId,
                                catdata.toMutableList()
                            )
                        } else if (subCount == 7) {
                            var catdata = it.data
                            val cName = catdata[0].subCatName
                            eighthOneTitle.text = cName
                            universalDaynamicRecycler(
                                shadowViewObject,
                                R.id.eighth_one_recyclerview,
                                eighthOnLayout,
                                "1",
                                subId,
                                catdata[0].subId,
                                catdata.toMutableList()
                            )
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

    private fun getBusinnessData(mVModel: MainViewModel, catId: String, token: String) {
        mVModel.getBussinessDetails(catId, token).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let {

                        val bussListDialog = AlertDialog.Builder(requireActivity())
                        val layoutBDialog = LayoutInflater.from(requireActivity()).inflate(
                            R.layout.buss_dialog_list,
                            null,
                            false
                        )
                        val bussDialogAdapter = BussDialogAdapter(it.data.toMutableList())
                        val bussRecyclerView =
                            layoutBDialog.findViewById<RecyclerView>(R.id.buss_list_recycler)
                        bussRecyclerView.adapter = bussDialogAdapter
                        bussRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
                        bussListDialog.setView(layoutBDialog)
                        bussDialogAdapter.onClickListener =
                            object : BussDialogAdapter.OnClickListItem {
                                override fun setBId(bId: String) {
                                    setUserBussPref(
                                        sharedPreferenceUtil.getValueString("user_id").toString(),
                                        bId,
                                        sharedPreferenceUtil.getValueString(
                                            "token"
                                        ).toString()
                                    )
                                }

                            }
                        bussListDialog.show()
                    }
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {

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
                        selectedBussinesLayout.post { showToolTip(selectedBussinesLayout) }
                        if (it.data.isNotEmpty()) {
                            sharedPreferenceUtil.save("logoUrl", it.data[0].logoUrl)
                            businessTitleTxt.text = it.data[0].bName
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

    private fun setUserBussPref(phone: String, pref_id: String, token: String){
        mainViewModel.postUserBussPref(phone, pref_id, token).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let {
                        sharedPreferenceUtil.save("pref_buss", pref_id)
                        val intent = requireActivity().intent
                        requireActivity().finish()
                        startActivity(intent)
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

    private fun geTrendingData(token: String) {
        mainViewModel.getTrendingData(token).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let {
                        var tData = it.data
                        val trendingTitleAdapter  = TrendingTitleAdapter(requireActivity(), tData.toMutableList())
                        trendingRecyclerView.adapter = trendingTitleAdapter
                        trendingRecyclerView.layoutManager = LinearLayoutManager(
                                requireActivity(),
                                LinearLayoutManager.HORIZONTAL,
                                false
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

    private fun showToolTip(view : View, ){
        val tooltip = Tooltip.Builder(requireActivity())
            .anchor(view, 0, 0, false)
            .text("Select Business here")
            .arrow(true)
            .floatingAnimation(Tooltip.Animation.DEFAULT)
            .closePolicy(ClosePolicy.TOUCH_ANYWHERE_NO_CONSUME)
            .showDuration(6000)
            .fadeDuration(2000)
            .overlay(true)
            .create()

        tooltip
            .doOnHidden { }
            .doOnFailure { }
            .doOnShown { }.show(view, Tooltip.Gravity.BOTTOM, true)
    }
}