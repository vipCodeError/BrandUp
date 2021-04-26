package com.vipcodeerror.brandup.data.api

import com.vipcodeerror.brandup.data.model.*
import com.vipcodeerror.brandup.data.model.home_modal.ApiHomeDataResponse
import com.vipcodeerror.brandup.data.model.home_modal.HomeSelectedApiResponse
import io.reactivex.Single
import java.io.File

interface ApiService {

    fun userLogin(phone : String, device_name: String) : Single<LogginApiResponse>

    fun getCatData(token : String) : Single<ApiCatDataResponse>

    fun postCatPref(userId: String, pref: String, token: String) : Single<ApiResponse>

    fun postBussDetails(bussName : String, phone: String, address : String, email: String, webN : String,
                        logoUrl: String, location: String,
                        belongToWhichUser : String, catIdBelongTo: String, token: String)  : Single<ApiResponse>

    fun uploadImage(logoUrl: File, tokens: String) : Single<ImageApiResponse>

    fun homeSelectedData(token: String) : Single<HomeSelectedApiResponse>

    fun homeData(catId: String, token: String) : Single<ApiHomeDataResponse>

    fun homeSubData(catId: String, token: String) : Single<ApiHomeDataResponse>

    fun getBussinessDet(userId : String, token: String) : Single<BussinessDataResponse>

    fun getBussinessDetForHome(userId : String, id:String, token: String) : Single<BussinessDataResponse>

    fun setBusinessPref(prefId : String , userId : String, token: String) : Single<ApiResponse>

    fun getBottomBanner(prefId: String, token : String) : Single<BottomBannerResponse>

    fun requestImageGenerator(user_id : String, pref_id: String, token: String) : Single<ApiResponse>

    fun getAllPlanData(token: String) : Single<PlanDataResponse>

    fun searchString(str: String ,token: String) : Single<SearchDataResponse>

    fun getTrendingData(token: String) : Single<TrendingDataResponse>

    fun getBannerData(slideOrStatic : String, token:String) : Single<BannerDataResponse>

    fun createOrderId(amt :String, token : String) : Single<OrderIdResponse>

    fun verifyTransaction(paymentSignature : String,
                          razorpayPaymentId : String,
                          razorpayOrderId : String, userId : String, planType: String, token: String) : Single<ApiResponse>


    fun postUpdateBussDetails(userId : String, bussName : String, phone: String, address : String, email: String, webN : String,
                        logoUrl: String, location: String,
                        belongToWhichUser : String, catIdBelongTo: String, token: String)  : Single<ApiResponse>

    fun getPlanById(planId : String, token: String) : Single<PlanDataModel>

    fun getHdBrandImage(backImg: String, frameImg: String, logoImg:String, token: String) : Single<HdImageModel>
}