package com.vipcodeerror.brandup.data.repository

import com.vipcodeerror.brandup.data.api.ApiHelper
import com.vipcodeerror.brandup.data.model.*
import com.vipcodeerror.brandup.data.model.home_modal.ApiHomeDataResponse
import com.vipcodeerror.brandup.data.model.home_modal.HomeSelectedApiResponse
import io.reactivex.Single
import java.io.File

class MainRepository (private val apiHelper: ApiHelper) {

    fun postLoginUser(phone : String, device_name:String) : Single<LogginApiResponse>{
        return apiHelper.loginUser(phone, device_name)
    }

    fun getCatData(token : String): Single<ApiCatDataResponse>{
        return apiHelper.catData(token)
    }

    fun postCatPref(userId: String, pref: String, token: String) : Single<ApiResponse>{
        return apiHelper.businessCatPref(userId, pref, token)
    }

    fun postBussDetails(bussName : String, phone: String, address : String, email: String, webN: String,
                       logoUrl: String, location: String,
                       belongToWhichUser : String, catIdBelongTo: String, token: String) : Single<ApiResponse>{
        return apiHelper.postBusinessDetails(bussName, phone, address, email, webN, logoUrl, location,
                belongToWhichUser, catIdBelongTo, token)
    }

    fun postUpdateBussDetails(userId: String, bussName : String, phone: String, address : String, email: String, webN: String,
                        logoUrl: String, location: String,
                        belongToWhichUser : String, catIdBelongTo: String, token: String) : Single<ApiResponse>{
        return apiHelper.postUpdateBusinessDetails(userId, bussName, phone, address, email, webN, logoUrl, location,
            belongToWhichUser, catIdBelongTo, token)
    }

    fun uploadLogoImage(logoUrl: File, token: String) : Single<ImageApiResponse>{
        return apiHelper.uploadLogoImage(logoUrl, token)
    }

    fun getHomeSelectedData(token : String) : Single<HomeSelectedApiResponse>{
        return apiHelper.getHomeSelectedData(token)
    }

    fun getHomeData(catId : String, token : String) : Single<ApiHomeDataResponse>{
        return apiHelper.getHomeData(catId, token)
    }

    fun getHomSubData(catId : String, token : String) : Single<ApiHomeDataResponse>{
        return apiHelper.getHomeSubData(catId, token)
    }

    fun getBusinessDetails(catId: String, token: String) : Single<BussinessDataResponse>{
        return apiHelper.getBussDetails(catId, token)
    }

    fun getBusinessDetailsForHome(catId: String, id:String,  token: String) : Single<BussinessDataResponse>{
        return apiHelper.getBussinessDetailsForHome(catId, id, token)
    }

    fun getBottomBanner(prefId : String, token : String) : Single<BottomBannerResponse>{
        return apiHelper.getBottomBannerResponse(prefId, token)
    }

    fun executeImageGeneratorRequest(user_id : String, pref_id: String, token: String) : Single<ApiResponse>{
        return apiHelper.requestForImageGeneration(user_id, pref_id, token)
    }

    fun getAllPlan(token : String) : Single<PlanDataResponse>{
        return apiHelper.getAllPlanData(token)
    }

    fun searchStr(str: String, token : String) : Single<SearchDataResponse>{
        return apiHelper.searchString(str, token)
    }

    fun getTrendingData(token: String) : Single<TrendingDataResponse>{
        return apiHelper.getTrendingData(token)
    }

    fun getBannerData(slideOrStatic : String, token: String) : Single<BannerDataResponse>{
        return apiHelper.getBannerData(slideOrStatic, token)
    }

    fun createOrderId(amt : String, token : String) : Single<OrderIdResponse>{
        return apiHelper.crateOrderId(amt, token)
    }

    fun verifyTransaction(paymentSignature : String,
                          razorpayPaymentId : String,
                          razorpayOrderId : String,userId : String, planType: String ,token : String) : Single<ApiResponse>{
        return apiHelper.verifyTransaction(paymentSignature, razorpayPaymentId, razorpayOrderId, userId , planType, token)
    }

    fun getPlanById(planId : String, token: String) : Single<PlanDataModel> {
        return apiHelper.getPlanDataById(planId, token)
    }

    fun getHdBrandImage(backImg: String, frameImg: String, logoImg:String, token: String) : Single<HdImageModel>{
        return apiHelper.getHdBrandImage(backImg, frameImg, logoImg, token)
    }

    fun getSupportData() : Single<SupportDataResponse>{
        return apiHelper.getSupportData()
    }

    fun getPrivacyData() : Single<PrivacyDataResponse> {
        return apiHelper.getPrivacyPolicyData()
    }
}