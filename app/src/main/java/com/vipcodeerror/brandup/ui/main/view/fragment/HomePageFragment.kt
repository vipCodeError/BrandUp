package com.vipcodeerror.brandup.ui.main.view.fragment

import android.icu.number.Scale
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import android.view.animation.OvershootInterpolator
import android.view.animation.ScaleAnimation
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.data.model.SliderItem
import com.vipcodeerror.brandup.ui.main.adapter.SliderAdapterExample
import com.vipcodeerror.brandup.ui.main.adapter.TrendingTitleAdapter


class HomePageFragment : Fragment() {

    lateinit var staticAdsImageView : ImageView
    lateinit var staticAdsLayout : CardView
    lateinit var trendingRecyclerView : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.home_page_fragment, container, false)

        staticAdsImageView = view.findViewById(R.id.static_ads)
        staticAdsLayout = view.findViewById(R.id.static_ads_layout)
        trendingRecyclerView = view.findViewById(R.id.trending_title_recycler)

        staticAdsLayout.startAnimation(bubbleAnimation());

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
        val sliderAdapter = SliderAdapterExample(requireActivity())
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
}