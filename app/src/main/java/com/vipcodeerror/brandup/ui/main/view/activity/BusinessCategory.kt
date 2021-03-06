package com.vipcodeerror.brandup.ui.main.view.activity

import android.content.Context
import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.ui.main.adapter.PopularCategoryAdapter

class BusinessCategory : AppCompatActivity() {

    private lateinit var businessRecyclerView : RecyclerView
    private lateinit var popularCategoryAdapter : PopularCategoryAdapter
    private lateinit var toolbar: Toolbar
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bussiness_activity)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar);

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.arrow_back))
        supportActionBar?.title = "Select your business category"
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            super.onBackPressed()
        })
        
        var catListStr = mutableListOf<String>("Marketing and Advertising Agency",
            "Clothes", "Agriculture", "Education", "Jewelery", "Art and Design", "Mobile Store",
            "Advocate", "Auto Mobile", "FMCG", "Real Estate", "Ceramic", "Electrical",
            "Building Traders", "Furniture", "Textile Industry", "Insurance", "Finance",
            "Photographer", "Tour and Travels", "Information Technology", "Graphic Designing",
            "Dairy & Sweets", "Consultant", "Computer Hardware", "Restaurant, Catering", "Solar and Power Panel",
            "Social Activist", "Steel and Aluminium", "Events", "Clinic and Hospital", "Aryuvedic",
            "Agarbatti", "Pharmaceutical", "Hotel", "Security Surveillance", "Home Appliances",
            "Interior", "Beauty parlor and salon");
        businessRecyclerView = findViewById(R.id.popular_cat_recycler)
        popularCategoryAdapter = PopularCategoryAdapter(this, catListStr)
        businessRecyclerView.layoutManager =  GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
        businessRecyclerView.adapter = popularCategoryAdapter
    }
}