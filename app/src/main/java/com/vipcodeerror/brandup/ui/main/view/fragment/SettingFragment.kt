package com.vipcodeerror.brandup.ui.main.view.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.ui.main.view.activity.BrandLogoEdit
import com.vipcodeerror.brandup.ui.main.view.activity.LoginActivity
import com.vipcodeerror.brandup.util.SharedPreferenceUtil

class SettingFragment : Fragment() {

    private lateinit var myBusinessBtn : TextView
    private lateinit var bannerImage : ImageView

    private lateinit var logoutTxt: TextView
    private lateinit var sharedPreferenceUtil : SharedPreferenceUtil

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_settings, container, false)
        sharedPreferenceUtil = SharedPreferenceUtil(container!!.context)
        myBusinessBtn = view.findViewById(R.id.my_business_set)
        bannerImage = view.findViewById(R.id.banner_ads_sett)

        logoutTxt = view.findViewById(R.id.logout_txt)

        myBusinessBtn.setOnClickListener {
            startActivity(Intent(requireActivity(), BrandLogoEdit::class.java))
        }

        Glide.with(requireActivity()).load("https://www.shamsherkhan.com/wp-content/uploads/2020/04/og-bannersnack_v2.png").into(bannerImage)

        logoutTxt.setOnClickListener {
                sharedPreferenceUtil.save("is_logged", false)
                var intent = Intent(requireActivity(), LoginActivity::class.java)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                requireActivity().finish()
        }
        return view
    }
}