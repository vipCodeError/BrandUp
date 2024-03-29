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
    private val getBottomBannerData = MutableLiveData<Resource<BottomBannerResponse>>()
    private val requestForImgGen = MutableLiveData<Resource<ApiResponse>>()
    private val getAllplanData = MutableLiveData<Resource<PlanDataResponse>>()
    private val searchStr = MutableLiveData<Resource<SearchDataResponse>>()
    private val getTrendingData = MutableLiveData<Resource<TrendingDataResponse>>()
    private val createOrderId = MutableLiveData<Resource<OrderIdResponse>>()
    private val verifyTransaction = MutableLiveData<Resource<ApiResponse>>()
    private val updateBusinessDetails= MutableLiveData<Resource<ApiResponse>>()
    private val getPlanDataById = MutableLiveData<Resource<PlanDataModel>>()
    private val getHdBrandImage = MutableLiveData<Resource<HdImageModel>>()
    private val supportData = MutableLiveData<Resource<SupportDataResponse>>()
    private val privacyData = MutableLiveData<Resource<PrivacyDataResponse>>()

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

    fun postBussDetailsData(bussName : String, phone: String, address : String, email: String, webN: String,
                            logoUrl: String, location: String,
                            belongToWhichUser : String, catIdBelongTo: String, token: String): LiveData<Resource<ApiResponse>>{
        postBussDetails.postValue(Resource.loading(null))
        compositeDisposable.add(
                mainRepository.postBussDetails(bussName, phone, address, email,webN, logoUrl, location,
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

    fun postUpdateBussDetailsData(user_id: String, bussName : String, phone: String, address : String, email: String, webN: String,
                            logoUrl: String, location: String,
                            belongToWhichUser : String, catIdBelongTo: String, token: String): LiveData<Resource<ApiResponse>>{
        updateBusinessDetails.postValue(Resource.loading(null))
        compositeDisposable.add(
            mainRepository.postUpdateBussDetails(user_id, bussName, phone, address, email,webN, logoUrl, location,
                belongToWhichUser, catIdBelongTo, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ loginData ->
                    updateBusinessDetails.postValue(Resource.success(loginData))
                }, {
                    updateBusinessDetails.postValue(Resource.error("Something went wrong", null))
                })
        )

        return updateBusinessDetails
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

    fun getBottomBannerDatas(prefId : String, token : String) : LiveData<Resource<BottomBannerResponse>>{
        getBottomBannerData.postValue(Resource.loading(null))
        compositeDisposable.add(
                mainRepository.getBottomBanner(prefId, token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({data ->
                            getBottomBannerData.postValue(Resource.success(data))
                        }, {
                            getBottomBannerData.postValue(Resource.error("Something went wrong", null))
                        })
        )

        return getBottomBannerData
    }

    fun requestForImageGeneration(user_id: String, pref_id: String, token : String) : LiveData<Resource<ApiResponse>>{
        requestForImgGen.postValue(Resource.loading(null))
        compositeDisposable.add(
                mainRepository.executeImageGeneratorRequest(user_id, pref_id, token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({data ->
                            requestForImgGen.postValue(Resource.success(data))
                        }, {
                            requestForImgGen.postValue(Resource.error("Something went wrong", null))
                        })
        )

        return requestForImgGen
    }

    fun getAllPlan(token: String) : LiveData<Resource<PlanDataResponse>>{
        getAllplanData.postValue(Resource.loading(null))

        compositeDisposable.add(
                mainRepository.getAllPlan(token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({data ->
                            getAllplanData.postValue(Resource.success(data))
                        }, {
                            getAllplanData.postValue(Resource.error("Something went wrong", null))
                        })
        )

        return getAllplanData
    }

    fun searchStrData(str: String , token: String) : LiveData<Resource<SearchDataResponse>>{
        searchStr.postValue(Resource.loading(null))

        compositeDisposable.add(
                mainRepository.searchStr(str, token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({data ->
                            searchStr.postValue(Resource.success(data))
                        }, {
                            searchStr.postValue(Resource.error("Something went wrong", null))
                        })
        )

        return searchStr
    }

    fun getTrendingData(token: String) : LiveData<Resource<TrendingDataResponse>>{
        getTrendingData.postValue(Resource.loading(null))

        compositeDisposable.add(
                mainRepository.getTrendingData(token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({data ->
                            getTrendingData.postValue(Resource.success(data))
                        }, {
                            getTrendingData.postValue(Resource.error("Something went wrong", null))
                        })
        )

        return getTrendingData
    }

    fun fetchBannerData(getBrandData : MutableLiveData<Resource<BannerDataResponse>>, slideOrStatic : String, token : String) : LiveData<Resource<BannerDataResponse>>{
        getBrandData.postValue(Resource.loading(null))
        compositeDisposable.add(
                mainRepository.getBannerData(slideOrStatic, token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({data ->
                            getBrandData.postValue(Resource.success(data))
                        }, {
                            getBrandData.postValue(Resource.error("Something went wrong", null))
                        })
        )

        return getBrandData
    }

    fun createOrderIdData(amt : String, token : String) : LiveData<Resource<OrderIdResponse>>{
        createOrderId.postValue(Resource.loading(null))
        compositeDisposable.add(
            mainRepository.createOrderId(amt, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({data ->
                    createOrderId.postValue(Resource.success(data))
                }, {
                    createOrderId.postValue(Resource.error("Something went wrong", null))
                })
        )

        return createOrderId
    }

    fun verifyTransactionData(paymentSignature : String,
                              razorpayPaymentId : String,
                              razorpayOrderId : String, userId : String, planType : String, token : String) : LiveData<Resource<ApiResponse>>{
        verifyTransaction.postValue(Resource.loading(null))
        compositeDisposable.add(
            mainRepository.verifyTransaction(paymentSignature, razorpayPaymentId, razorpayOrderId, userId, planType, token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({data ->
                    verifyTransaction.postValue(Resource.success(data))
                }, {
                    verifyTransaction.postValue(Resource.error("Something went wrong", null))
                })
        )

        return verifyTransaction
    }

    fun getPlanById(planId : String, token : String) : LiveData<Resource<PlanDataModel>>{
        getPlanDataById.postValue(Resource.loading(null))
        compositeDisposable.add(
                mainRepository.getPlanById(planId, token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({data ->
                            getPlanDataById.postValue(Resource.success(data))
                        }, {
                            getPlanDataById.postValue(Resource.error("Something went wrong", null))
                        })
        )

        return getPlanDataById
    }

    fun getHdImage(backImg: String, frameImg: String, logoImg:String, token: String) : LiveData<Resource<HdImageModel>>{
        getHdBrandImage.postValue(Resource.loading(null))
        compositeDisposable.add(
                mainRepository.getHdBrandImage(backImg, frameImg, logoImg, token)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({data ->
                            getHdBrandImage.postValue(Resource.success(data))
                        }, {
                            getHdBrandImage.postValue(Resource.error("Something went wrong", null))
                        })
        )

        return getHdBrandImage;
    }

    fun getSupportData() : LiveData<Resource<SupportDataResponse>> {
        supportData.postValue(Resource.loading(null))
        compositeDisposable.add(
                mainRepository.getSupportData()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({data ->
                            supportData.postValue(Resource.success(data))
                        }, {
                            supportData.postValue(Resource.error("Something went wrong", null))
                        })
        )

        return supportData;
    }

    fun getPrivacyData() : LiveData<Resource<PrivacyDataResponse>> {
        privacyData.postValue(Resource.loading(null))
        compositeDisposable.add(
                mainRepository.getPrivacyData()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe({data ->
                            privacyData.postValue(Resource.success(data))
                        }, {
                            privacyData.postValue(Resource.error("Something went wrong", null))
                        })
        )
        return privacyData;
    }
}