package com.vipcodeerror.brandup.ui.main.view.activity

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import com.smarteist.autoimageslider.SliderView
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.data.model.SliderItem
import com.vipcodeerror.brandup.ui.main.adapter.*

class FrameTemplateSelectorActivity : AppCompatActivity(){

    private lateinit var topFrameRecycler : RecyclerView
    private lateinit var subCatTitleRecycler : RecyclerView
    private lateinit var frameSelectorRecycler : RecyclerView
    private lateinit var backFrame : ImageView

    private lateinit var frameSelectorAdapter: FrameSelectorAdapter
   // private lateinit var topFrameAdapter : TopFrameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.frame_selector_activity)
        
       // topFrameRecycler = findViewById(R.id.top_frame_recycler)
        backFrame = findViewById(R.id.back_frame)
        subCatTitleRecycler = findViewById(R.id.sub_cat_recycler)
        frameSelectorRecycler = findViewById(R.id.frame_selector_recycler)

        frameRecycler()
        trendingTitle()
        topFrame()
    }

    private fun topFrame(){
        var topStrList = mutableListOf<SliderItem>(SliderItem("https://practicebuckett123.s3.ap-south-1.amazonaws.com/fifth_frame.png"),
                SliderItem("https://practicebuckett123.s3.ap-south-1.amazonaws.com/first_frame.png"),
                SliderItem("https://practicebuckett123.s3.ap-south-1.amazonaws.com/secondframe.png"),
                SliderItem("https://practicebuckett123.s3.ap-south-1.amazonaws.com/shopyy_culture.png"),
                SliderItem("https://practicebuckett123.s3.ap-south-1.amazonaws.com/third_frame.png"))

        val sliderAdapter = FrameSliderAdapter(this)
        val sliderView: SliderView = findViewById(R.id.top_frame_recycler)
        sliderView.setSliderAdapter(sliderAdapter)
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        sliderView.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION);
        sliderView.startAutoCycle();

        sliderAdapter.addItem(topStrList)


//        topFrameAdapter = TopFrameAdapter(this, topStrList)
//        subCatTitleRecycler.adapter = topFrameAdapter
//        subCatTitleRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun frameRecycler(){
        var firstAdapterList = mutableListOf<String>()
        firstAdapterList.add("https://files.prokerala.com/images/800w/hanuman-jayanti-wishes-1.jpg")
        firstAdapterList.add("https://i2.wp.com/www.ahataxis.com/blog/wp-content/uploads/2018/08/janmas-1.jpg?ssl=1")
        firstAdapterList.add("https://cdn.dnaindia.com/sites/default/files/styles/full/public/2020/12/28/946113-january-calendar-2021-12.jpg")
        firstAdapterList.add("https://www.fabhotels.com/blog/wp-content/uploads/2019/08/Ganesh-Chaturthi.jpg")
        firstAdapterList.add("http://www.chefatlarge.in/wp-content/uploads/2017/09/lord-ganesha.jpg")
        firstAdapterList.add("https://i2.wp.com/www.ahataxis.com/blog/wp-content/uploads/2018/08/janmas-1.jpg?ssl=1")
        firstAdapterList.add("https://cdn.dnaindia.com/sites/default/files/styles/full/public/2020/12/28/946113-january-calendar-2021-12.jpg")
        firstAdapterList.add("https://www.fabhotels.com/blog/wp-content/uploads/2019/08/Ganesh-Chaturthi.jpg")
        firstAdapterList.add("https://i2.wp.com/www.ahataxis.com/blog/wp-content/uploads/2018/08/janmas-1.jpg?ssl=1")
        firstAdapterList.add("https://cdn.dnaindia.com/sites/default/files/styles/full/public/2020/12/28/946113-january-calendar-2021-12.jpg")
        firstAdapterList.add("https://www.fabhotels.com/blog/wp-content/uploads/2019/08/Ganesh-Chaturthi.jpg")
        firstAdapterList.add("https://files.prokerala.com/images/800w/hanuman-jayanti-wishes-1.jpg")
        firstAdapterList.add("https://i2.wp.com/www.ahataxis.com/blog/wp-content/uploads/2018/08/janmas-1.jpg?ssl=1")
        firstAdapterList.add("https://cdn.dnaindia.com/sites/default/files/styles/full/public/2020/12/28/946113-january-calendar-2021-12.jpg")
        firstAdapterList.add("https://www.fabhotels.com/blog/wp-content/uploads/2019/08/Ganesh-Chaturthi.jpg")
        firstAdapterList.add("http://www.chefatlarge.in/wp-content/uploads/2017/09/lord-ganesha.jpg")

        frameSelectorAdapter = FrameSelectorAdapter(this, firstAdapterList)

        frameSelectorRecycler.adapter = frameSelectorAdapter
        frameSelectorRecycler.layoutManager = GridLayoutManager(this,3, LinearLayoutManager.VERTICAL, false)
        frameSelectorAdapter.clickOnFrameUrl = object : ClickOnFrameUrl {
            override fun setUrlImage(url: String) {
                Glide.with(this@FrameTemplateSelectorActivity).load(url).into(backFrame)
            }
        }
    }

    private fun trendingTitle(){
        var catListStr = mutableListOf<String>("All", "Marketing and Advertising Agency",
                "Clothes", "Agriculture", "Education", "Jewelery", "Art and Design", "Mobile Store");
        val trendingTitleAdapter  = TrendingTitleAdapter(catListStr)
        subCatTitleRecycler.adapter = trendingTitleAdapter
        subCatTitleRecycler.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

}