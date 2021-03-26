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

    fun postCatPref(catId : String, token: String) : Single<ApiResponse>{
        return apiHelper.businessCatPref(catId, token)
    }

    fun postBussDetails(bussName : String, phone: String, address : String,
                       logoUrl: String, location: String,
                       belongToWhichUser : String, catIdBelongTo: String, token: String) : Single<ApiResponse>{
        return apiHelper.postBusinessDetails(bussName, phone, address, logoUrl, location,
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
}