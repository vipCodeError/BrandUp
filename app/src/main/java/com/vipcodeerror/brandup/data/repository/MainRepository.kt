package com.vipcodeerror.brandup.data.repository

import com.vipcodeerror.brandup.data.api.ApiHelper
import com.vipcodeerror.brandup.data.model.ApiCatDataResponse
import com.vipcodeerror.brandup.data.model.LogginApiResponse
import io.reactivex.Single

class MainRepository (private val apiHelper: ApiHelper) {

    fun postLoginUser(phone : String, device_name:String) : Single<LogginApiResponse>{
        return apiHelper.loginUser(phone, device_name)
    }


    fun postRegisterNumber(phone : String) : Single<LogginApiResponse>{
        return apiHelper.registerPhoneNumber(phone)
    }

    fun getCatData(token : String): Single<ApiCatDataResponse>{
        return apiHelper.catData(token)
    }

}