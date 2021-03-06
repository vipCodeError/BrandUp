package com.vipcodeerror.brandup.ui.main.view.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.ui.main.view.fragment.DownloadFragment
import com.vipcodeerror.brandup.ui.main.view.fragment.HomePageFragment
import com.vipcodeerror.brandup.ui.main.view.fragment.SettingFragment


class MainActivity : AppCompatActivity() {
    lateinit var downloadedFragment : DownloadFragment
    lateinit var settingsFragment : SettingFragment
    lateinit var homePageFragment : HomePageFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        downloadedFragment = DownloadFragment()
        settingsFragment = SettingFragment()
        homePageFragment = HomePageFragment()

        // show home fragment when app first launch
        homePageFragment()

        val bottomNavigationView = findViewById<View>(R.id.bottom_navigation) as BottomNavigationView
        bottomNavigationView.itemIconTintList = null

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    val fragmentManager = supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.frameLayout, homePageFragment)
                    fragmentTransaction.commit()

                }
                R.id.custom -> {
                    Toast.makeText(this@MainActivity, "Work in Progress...", Toast.LENGTH_SHORT).show()
                }
                R.id.downloaded -> {
                    val fragmentManager = supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.frameLayout, downloadedFragment)
                    fragmentTransaction.commit()
                }
                R.id.account -> {
                    val fragmentManager = supportFragmentManager
                    val fragmentTransaction = fragmentManager.beginTransaction()
                    fragmentTransaction.replace(R.id.frameLayout, settingsFragment)
                    fragmentTransaction.commit()
                }
            }
            true
        }
    }

    fun homePageFragment(){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, homePageFragment)
        fragmentTransaction.commit()
    }
}