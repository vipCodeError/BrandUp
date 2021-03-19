package com.vipcodeerror.brandup.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vipcodeerror.brandup.data.model.ApiResponse
import com.vipcodeerror.brandup.data.model.LogginApiResponse
import com.vipcodeerror.brandup.data.repository.MainRepository
import com.vipcodeerror.brandup.util.Resource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel(private val mainRepository: MainRepository) : ViewModel(){

    private val loginUser = MutableLiveData<Resource<LogginApiResponse>>()
    private val signUpUser = MutableLiveData<Resource<LogginApiResponse>>()
    private val compositeDisposable = CompositeDisposable()


    fun postLoginUser(phone: String, device_name: String){
        loginUser.postValue(Resource.loading(null))
        compositeDisposable.add(
                mainRepository.postLoginUser(phone, device_name)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ loginData ->
                                   loginUser.postValue(Resource.success(loginData))
                        }, {
                            loginUser.postValue(Resource.error("Something went wrong", null))
                        })
        )
    }


    fun postLoginUser(phone: String) : LiveData<Resource<LogginApiResponse>> {
        signUpUser.postValue(Resource.loading(null))
        compositeDisposable.add(
                mainRepository.postRegisterNumber(phone)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ loginData ->
                            signUpUser.postValue(Resource.success(loginData))
                        }, {
                            signUpUser.postValue(Resource.error("Something went wrong", null))
                        })
        )
        return signUpUser
    }

}