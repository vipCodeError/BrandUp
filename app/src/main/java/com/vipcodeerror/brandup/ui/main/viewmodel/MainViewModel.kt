package com.vipcodeerror.brandup.ui.main.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.vipcodeerror.brandup.data.model.*
import com.vipcodeerror.brandup.data.model.home_modal.ApiHomeDataResponse
import com.vipcodeerror.brandup.data.model.home_modal.HomeSelectedApiResponse
import com.vipcodeerror.brandup.data.repository.MainRepository
import com.vipcodeerror.brandup.util.Resource
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.File

class MainViewModel(private val mainRepository: MainRepository) : ViewModel(){

    private val loginUser = MutableLiveData<Resource<LogginApiResponse>>()
    private val getCatData = MutableLiveData<Resource<ApiCatDataResponse>>()
    private val postCatPref = MutableLiveData<Resource<ApiResponse>>()
    private val postBussDetails = MutableLiveData<Resource<ApiResponse>>()
    private val uploadLogoImage = MutableLiveData<Resource<ImageApiResponse>>()
    private val homeSelectedData = MutableLiveData<Resource<HomeSelectedApiResponse>>()
    private val getBData = MutableLiveData<Resource<BussinessDataResponse>>()
    private val getBDataForHome = MutableLiveData<Resource<BussinessDataResponse>>()
    private val compositeDisposable = CompositeDisposable()


    fun postLoginUser(phone: String, device_name: String) : LiveData<Resource<LogginApiResponse>>{
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
        return loginUser;
    }

    fun getCatData(token: String) : LiveData<Resource<ApiCatDataResponse>>{
        getCatData.postValue(Resource.loading(null))
        compositeDisposable.add(
                mainRepository.getCatData(token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ loginData ->
                            getCatData.postValue(Resource.success(loginData))
                        }, {
                            getCatData.postValue(Resource.error("Something went wrong", null))
                        })
        )
        return getCatData
    }

    fun postUserBussPref(userId: String, pref: String, token: String) : LiveData<Resource<ApiResponse>>{
        postCatPref.postValue(Resource.loading(null))
        compositeDisposable.add(
                mainRepository.postCatPref(userId, pref, token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ loginData ->
                            postCatPref.postValue(Resource.success(loginData))
                        }, {
                            postCatPref.postValue(Resource.error("Something went wrong", null))
                        })
        )

        return postCatPref
    }

    fun postBussDetailsData(bussName : String, phone: String, address : String,
                            logoUrl: String, location: String,
                            belongToWhichUser : String, catIdBelongTo: String, token: String): LiveData<Resource<ApiResponse>>{
        postBussDetails.postValue(Resource.loading(null))
        compositeDisposable.add(
                mainRepository.postBussDetails(bussName, phone, address, logoUrl, location,
                        belongToWhichUser, catIdBelongTo, token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({ loginData ->
                            postBussDetails.postValue(Resource.success(loginData))
                        }, {
                            postBussDetails.postValue(Resource.error("Something went wrong", null))
                        })
        )

        return postBussDetails
    }

    fun uploadImageData(logoUrl : File, token: String) : LiveData<Resource<ImageApiResponse>>{
        uploadLogoImage.postValue(Resource.loading(null))
        compositeDisposable.add(
                mainRepository.uploadLogoImage(logoUrl, token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({data ->
                            uploadLogoImage.postValue(Resource.success(data))
                        }, {
                            uploadLogoImage.postValue(Resource.error("Something went wrong", null))
                        })
        )
        return uploadLogoImage
    }

    fun getSelectedHomeData(token: String) : LiveData<Resource<HomeSelectedApiResponse>>{
        homeSelectedData.postValue(Resource.loading(null))
        compositeDisposable.add(
                mainRepository.getHomeSelectedData(token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({data ->
                            homeSelectedData.postValue(Resource.success(data))
                        }, {
                            homeSelectedData.postValue(Resource.error("Something went wrong", null))
                        })
        )
        return homeSelectedData
    }

    fun getHomeDataUniversal(homeDataU : MutableLiveData<Resource<ApiHomeDataResponse>>, catId: String, token: String) : LiveData<Resource<ApiHomeDataResponse>>{

        homeDataU.postValue(Resource.loading(null))
        compositeDisposable.add(
                mainRepository.getHomeData(catId, token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({data ->
                            homeDataU.postValue(Resource.success(data))
                        }, {
                            homeDataU.postValue(Resource.error("Something went wrong", null))
                        })
        )
        return homeDataU
    }

    fun getHomeSubDataUniversal(homeDataU : MutableLiveData<Resource<ApiHomeDataResponse>>, catId: String, token: String) : LiveData<Resource<ApiHomeDataResponse>>{

        homeDataU.postValue(Resource.loading(null))
        compositeDisposable.add(
            mainRepository.getHomSubData(catId, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({data ->
                    homeDataU.postValue(Resource.success(data))
                }, {
                    homeDataU.postValue(Resource.error("Something went wrong", null))
                })
        )
        return homeDataU
    }

    fun getBussinessDetails(catId : String, token: String) : LiveData<Resource<BussinessDataResponse>> {
        getBData.postValue(Resource.loading(null))
        compositeDisposable.add(
            mainRepository.getBusinessDetails(catId, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({data ->
                    getBData.postValue(Resource.success(data))
                }, {
                    getBData.postValue(Resource.error("Something went wrong", null))
                })
        )
        return getBData
    }

    fun getBussinessDetailsForHome(catId : String, id: String, token: String) : LiveData<Resource<BussinessDataResponse>> {
        getBDataForHome.postValue(Resource.loading(null))
        compositeDisposable.add(
                mainRepository.getBusinessDetailsForHome(catId, id, token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({data ->
                            getBDataForHome.postValue(Resource.success(data))
                        }, {
                            getBDataForHome.postValue(Resource.error("Something went wrong", null))
                        })
        )
        return getBDataForHome
    }

}