package com.vipcodeerror.brandup.data.repository

import com.vipcodeerror.brandup.data.api.ApiHelper
import com.vipcodeerror.brandup.data.model.ApiCatDataResponse
import com.vipcodeerror.brandup.data.model.ApiResponse
import com.vipcodeerror.brandup.data.model.ImageApiResponse
import com.vipcodeerror.brandup.data.model.LogginApiResponse
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

}