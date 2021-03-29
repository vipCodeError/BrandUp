package com.vipcodeerror.brandup.ui.main.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView
import com.vipcodeerror.brandup.R
import com.vipcodeerror.brandup.util.RxSearchObservable.fromView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.search_activity_search)

        searchView = findViewById(R.id.searchView)
        searchRecyclerView = findViewById(R.id.search_recycler)

        fromView(searchView)
            .debounce(300, TimeUnit.MILLISECONDS)
            .filter { s ->
                if (s.isEmpty()) {
                    false
                } else {
                    true
                }
            }.distinctUntilChanged()
            .switchMap { s ->
                Observable.just(s);
            }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Consumer<String>{
                override fun accept(t: String?) {

                }

            })

    }
}