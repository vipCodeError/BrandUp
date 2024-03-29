package com.vipcodeerror.brandup.ui.main.view.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.data.api.ApiHelper
import com.vipcodeerror.brandup.data.api.ApiServiceImpl
import com.vipcodeerror.brandup.data.model.CatModel
import com.vipcodeerror.brandup.ui.base.ViewModelFactory
import com.vipcodeerror.brandup.ui.main.adapter.PopularCategoryAdapter
import com.vipcodeerror.brandup.ui.main.viewmodel.MainViewModel
import com.vipcodeerror.brandup.util.RxSearchObservable
import com.vipcodeerror.brandup.util.SharedPreferenceUtil
import com.vipcodeerror.brandup.util.Status
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class BusinessCategory : AppCompatActivity() {

    private lateinit var businessRecyclerView : RecyclerView
    private lateinit var popularCategoryAdapter : PopularCategoryAdapter
    private lateinit var toolbar: Toolbar
    private lateinit var mainViewModel: MainViewModel
    private lateinit var sharedPreferenceUtil : SharedPreferenceUtil
    private lateinit var searchView: SearchView
    private lateinit var listModel : MutableList<CatModel>
    private lateinit var tempList : MutableList<CatModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bussiness_activity)
        setupViewModel()

        sharedPreferenceUtil = SharedPreferenceUtil(this)
        searchView = findViewById(R.id.searchView)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar);

        listModel = mutableListOf<CatModel>()
        tempList = mutableListOf<CatModel>()

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(ContextCompat.getDrawable(this, R.drawable.arrow_back))
        supportActionBar?.title = "Select your business category"
        toolbar.setNavigationOnClickListener(View.OnClickListener {
            super.onBackPressed()
        })

        RxSearchObservable.fromView(searchView)
            .debounce(300, TimeUnit.MILLISECONDS)
            .filter { s ->
                if (s.isEmpty()) {

                    true
                } else {
                    true
                }
            }.distinctUntilChanged()
            .switchMap { s ->
                Observable.just(s)
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Consumer<String> {
                override fun accept(t: String?) {
                    var tempAccepList = mutableListOf<CatModel>()
                    if (t!!.length > 0){

                        tempList.filterNotNull().forEach {
                            if (it.catName.contains(t!!, ignoreCase = true)){
                                tempAccepList.add(it)
                            }
                        }
                        popularCategoryAdapter.setDataItem(tempAccepList)

                    }else {
                        popularCategoryAdapter.setDataItem(listModel)
                    }
                }
            })

        searchView.setOnClickListener{
            searchView.isIconified = false
        }

        catDataObserver(sharedPreferenceUtil.getValueString("token").toString())

    }

    private fun catDataObserver(token: String) {
        mainViewModel.getCatData(token).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let {
                        var catdata = it.data
                        listModel.addAll(catdata.toMutableList())
                        tempList.addAll(catdata.toMutableList())
                        businessRecyclerView = findViewById(R.id.popular_cat_recycler)
                        popularCategoryAdapter = PopularCategoryAdapter(this, catdata)
                        businessRecyclerView.layoutManager =  GridLayoutManager(this, 3, GridLayoutManager.VERTICAL, false)
                        businessRecyclerView.adapter = popularCategoryAdapter
                       // sharedPreferenceUtil.save("token", it.token)
                       // Toast.makeText(this@BusinessCategory, "Token ID is :: " + it.data, Toast.LENGTH_SHORT).show()
//                        startActivity(Intent(this@OtpVerficationActivity, PreferredLanguageActivity::class.java))
//                        finish()
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

    private fun setupViewModel() {
        mainViewModel = ViewModelProviders.of(
                this,
                ViewModelFactory(ApiHelper(ApiServiceImpl()))
        ).get(MainViewModel::class.java)
    }
}