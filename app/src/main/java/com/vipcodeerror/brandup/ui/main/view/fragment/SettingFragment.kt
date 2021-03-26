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
import com.vipcodeerror.brandup.ui.main.view.activity.MyBusinessList
import com.vipcodeerror.brandup.util.SharedPreferenceUtil

class SettingFragment : Fragment() {

    private lateinit var myBusinessEditTxt : TextView
    private lateinit var bannerImage : ImageView

    private lateinit var logoutTxt: TextView
    private lateinit var sharedPreferenceUtil : SharedPreferenceUtil

    private lateinit var myBusinessTxt : TextView
    private lateinit var preferredTxt : TextView
    private lateinit var helpAndSupportTxt : TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_settings, container, false)
        sharedPreferenceUtil = SharedPreferenceUtil(container!!.context)

        bannerImage = view.findViewById(R.id.banner_ads_sett)

        myBusinessTxt = view.findViewById(R.id.my_business_set)
        preferredTxt = view.findViewById(R.id.preferred_language_txt)
        helpAndSupportTxt = view.findViewById(R.id.help_and_support)
        myBusinessEditTxt = view.findViewById(R.id.edit_your_business)
        logoutTxt = view.findViewById(R.id.logout_txt)

        myBusinessEditTxt.setOnClickListener {
            startActivity(Intent(requireActivity(), BrandLogoEdit::class.java))
        }

        Glide.with(requireActivity()).load("https://www.shamsherkhan.com/wp-content/uploads/2020/04/og-bannersnack_v2.png").into(bannerImage)

        myBusinessTxt.setOnClickListener {
            startActivity(Intent(requireActivity(), MyBusinessList::class.java))
        }

        preferredTxt.setOnClickListener {

        }

        helpAndSupportTxt.setOnClickListener {

        }


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
}