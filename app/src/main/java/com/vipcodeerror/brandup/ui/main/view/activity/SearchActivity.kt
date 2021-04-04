package com.vipcodeerror.brandup.ui.main.view.activity

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.data.api.ApiHelper
import com.vipcodeerror.brandup.data.api.ApiServiceImpl
import com.vipcodeerror.brandup.data.model.SearchDataModel
import com.vipcodeerror.brandup.data.model.TrendingDataModel
import com.vipcodeerror.brandup.data.model.home_modal.HomeSelectedModel
import com.vipcodeerror.brandup.ui.base.ViewModelFactory
import com.vipcodeerror.brandup.ui.main.adapter.SearchStringAdapter
import com.vipcodeerror.brandup.ui.main.adapter.TrendingSearchAdapter
import com.vipcodeerror.brandup.ui.main.viewmodel.MainViewModel
import com.vipcodeerror.brandup.util.RxSearchObservable.fromView
import com.vipcodeerror.brandup.util.SharedPreferenceUtil
import com.vipcodeerror.brandup.util.Status
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit


class SearchActivity : AppCompatActivity() {

    private lateinit var searchView: SearchView
    private lateinit var searchRecyclerView: RecyclerView
    private lateinit var trendingRecyclerView: RecyclerView
    private lateinit var backBtn : ImageView
    private lateinit var mainViewModel: MainViewModel
    private lateinit var sharedPreferenceUtil : SharedPreferenceUtil
    private lateinit var searchStringAdapter: SearchStringAdapter
    private lateinit var trendingSearchAdapter: TrendingSearchAdapter

    private lateinit var tempDataList : MutableList<SearchDataModel>
    private lateinit var tempDataTrendList : MutableList<TrendingDataModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_activity_search)

        tempDataList = mutableListOf()
        tempDataTrendList = mutableListOf()

        searchView = findViewById(R.id.searchView)
        searchRecyclerView = findViewById(R.id.search_recycler)
        trendingRecyclerView = findViewById(R.id.trending_recycler)
        backBtn = findViewById(R.id.back)

        sharedPreferenceUtil = SharedPreferenceUtil(this)
        mainViewModel = setupViewModel()

        geTrendingData(sharedPreferenceUtil.getValueString("token").toString())


        fromView(searchView)
            .debounce(300, TimeUnit.MILLISECONDS)
            .filter { s ->
                if (s.isEmpty()) {
                    tempDataList.clear()
                    false
                } else {
                    true
                }
            }.distinctUntilChanged()
            .switchMap { s ->
                Observable.just(s)
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Consumer<String>{
                override fun accept(t: String?) {
                    searchString(t!!, sharedPreferenceUtil.getValueString("token").toString())
                }
            })

        backBtn.setOnClickListener{
            onBackPressed()
        }

        searchView.setOnClickListener{
            searchView.isIconified = false
        }
    }

    private fun setupViewModel() :MainViewModel {
        return  ViewModelProviders.of(
                this,
                ViewModelFactory(ApiHelper(ApiServiceImpl()))
        ).get(MainViewModel::class.java)
    }


    private fun searchString(str: String , token: String) {
        mainViewModel.searchStrData(str, token).observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let {
                        var sData = it.data
                        tempDataList = sData.toMutableList()
                        searchStringAdapter = SearchStringAdapter(this@SearchActivity, tempDataList)
                        searchRecyclerView.adapter = searchStringAdapter
                        searchRecyclerView.layoutManager = LinearLayoutManager(this)

                    }
                }
                Status.LOADING -> {

                }
                Status.ERROR -> {

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
                        tempDataTrendList = tData.toMutableList()
                        trendingSearchAdapter = TrendingSearchAdapter(this@SearchActivity,tempDataTrendList)
                        trendingRecyclerView.adapter = trendingSearchAdapter
                        trendingRecyclerView.layoutManager = LinearLayoutManager(this)
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