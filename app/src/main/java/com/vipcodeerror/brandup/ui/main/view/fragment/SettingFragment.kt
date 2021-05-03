package com.vipcodeerror.brandup.ui.main.view.fragment

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.data.api.ApiHelper
import com.vipcodeerror.brandup.data.api.ApiServiceImpl
import com.vipcodeerror.brandup.data.model.BannerDataResponse
import com.vipcodeerror.brandup.ui.base.ViewModelFactory
import com.vipcodeerror.brandup.ui.main.view.activity.*
import com.vipcodeerror.brandup.ui.main.view.activity.MainActivity
import com.vipcodeerror.brandup.ui.main.viewmodel.MainViewModel
import com.vipcodeerror.brandup.util.Resource
import com.vipcodeerror.brandup.util.SharedPreferenceUtil
import com.vipcodeerror.brandup.util.Status


class SettingFragment : Fragment() {


    //private lateinit var bannerImage : ImageView

    private lateinit var logoutTxt: TextView
    private lateinit var sharedPreferenceUtil : SharedPreferenceUtil

    private lateinit var myBusinessTxt : LinearLayout
    private lateinit var preferredTxt : LinearLayout
    private lateinit var helpAndSupportTxt : LinearLayout
    private lateinit var upgradePlan : LinearLayout
    private lateinit var shareLayout : LinearLayout
    private lateinit var privacyPolicy : LinearLayout

    private lateinit var bName : TextView
    private lateinit var bCatName : TextView
    private lateinit var bPhoneNo : TextView
    private lateinit var bPlanType : TextView
    private lateinit var bDetLay : LinearLayout

    private lateinit var mainViewModel: MainViewModel
    private val getBrandDataStatic = MutableLiveData<Resource<BannerDataResponse>>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_settings, container, false)
        sharedPreferenceUtil = SharedPreferenceUtil(container!!.context)
        mainViewModel = setupViewModel()

        bName = view.findViewById(R.id.buss_name)
        bCatName = view.findViewById(R.id.buss_cat)
        bPhoneNo = view.findViewById(R.id.phone_no)
        bPlanType = view.findViewById(R.id.plan_type)

      //  bannerImage = view.findViewById(R.id.banner_ads_sett)
      //  bannerImage = view.findViewById(R.id.banner_ads_sett)

        myBusinessTxt = view.findViewById(R.id.my_b_layout)
        preferredTxt = view.findViewById(R.id.pref_lan_layout)
        helpAndSupportTxt = view.findViewById(R.id.help_and_support_layout)
        upgradePlan = view.findViewById(R.id.edit_b_layout)
        logoutTxt = view.findViewById(R.id.logout_txt)
        shareLayout = view.findViewById(R.id.share)
        privacyPolicy = view.findViewById(R.id.privacy_and_policy)
        bDetLay = view.findViewById(R.id.b_lay_det)

        upgradePlan.setOnClickListener {
            startActivity(Intent(requireActivity(), PlanSelectorActivity::class.java))
        }

        // Glide.with(requireActivity()).load("https://www.shamsherkhan.com/wp-content/uploads/2020/04/og-bannersnack_v2.png").into(bannerImage)

        myBusinessTxt.setOnClickListener {
            startActivity(Intent(requireActivity(), MyBusinessList::class.java))
        }

        preferredTxt.setOnClickListener {
            startActivity(Intent(requireActivity(), PreferredLanguageActivity::class.java))
            //requireActivity().finish()
        }

        helpAndSupportTxt.setOnClickListener {
            startActivity(Intent(requireActivity(), SupportActivity::class.java))
        }

        privacyPolicy.setOnClickListener {
            startActivity(Intent(requireActivity(), PrivacyPolicyActivity::class.java))
        }

        shareLayout.setOnClickListener {
            var imageUri: Uri? = null
            try {
                imageUri = Uri.parse(
                    MediaStore.Images.Media.insertImage(
                        requireActivity().contentResolver,
                        BitmapFactory.decodeResource(resources, R.drawable.brandup),
                        null,
                        null
                    )
                )
            } catch (e: NullPointerException) {
            }
            val share = Intent(Intent.ACTION_SEND)
            share.type = "image/*"
            share.putExtra(Intent.EXTRA_STREAM, imageUri)
            share.putExtra(
                Intent.EXTRA_TEXT,
                """ Share Our app .
                    https://play.google.com/store/apps/details?id=${requireActivity().packageName}
                    
                                """.trimIndent()
            )
            startActivity(Intent.createChooser(share, " EnsLive "))
        }

        getBusinnessForHomeData(
            mainViewModel,
            sharedPreferenceUtil.getValueString("user_id").toString(),
            sharedPreferenceUtil.getValueString("pref_buss").toString(),
            sharedPreferenceUtil.getValueString("token").toString()
        )

//        getBannerStaticData(
//            getBrandDataStatic,
//            "1",
//            sharedPreferenceUtil.getValueString("token").toString()
//        )

        logoutTxt.setOnClickListener {
                sharedPreferenceUtil.save("is_logged", false)
                sharedPreferenceUtil.save("token", "")
                sharedPreferenceUtil.save("user_id", "")
                sharedPreferenceUtil.save("pref_buss", "")

                var intent = Intent(requireActivity(), LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                requireActivity().finish()
        }
        return view
    }

    private fun getBusinnessForHomeData(
        mVModel: MainViewModel,
        catId: String,
        id: String,
        token: String
    ) {
        mVModel.getBussinessDetailsForHome(catId, id, token).observe(
            this,
            androidx.lifecycle.Observer {
                when (it.status) {
                    Status.SUCCESS -> {
                        it.data?.let {
                            if (it.data.isNotEmpty()) {
                                bName.text = it.data[0].bName
                                bPhoneNo.text = it.data[0].bPhone
                                bCatName.text = it.data[0].catName
                                bPlanType.text =
                                    "Plan : " + sharedPreferenceUtil.getValueString("plan_name")
                                        .toString()
                            }else {
                                bDetLay.visibility = View.INVISIBLE
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

    private fun setupViewModel() :MainViewModel {
        return  ViewModelProviders.of(
            this,
            ViewModelFactory(ApiHelper(ApiServiceImpl()))
        ).get(MainViewModel::class.java)
    }

    private fun getBannerStaticData(
        bannerLiveData: MutableLiveData<Resource<BannerDataResponse>>,
        slideOrStatic: String,
        token: String
    ) {
        mainViewModel.fetchBannerData(bannerLiveData, slideOrStatic, token).observe(
            this,
            androidx.lifecycle.Observer {
                when (it.status) {
                    Status.SUCCESS -> {
                        it.data?.let {
                            var tData = it.data
                            if (tData.size > 1) {
//                                Glide.with(requireActivity())
//                                    .load("https://d4f9k68hk754p.cloudfront.net/fit-in/512x400/images/" + tData[1].url)
//                                    .into(
//                                        bannerImage
//                                    )
//                                bannerImage.setOnClickListener {
//                                    Toast.makeText(
//                                        requireActivity(),
//                                        tData[0].redirectUrl,
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//                                }
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

}