package com.vipcodeerror.brandup.data.api

import com.vipcodeerror.brandup.data.model.ApiCatDataResponse
import com.vipcodeerror.brandup.data.model.ApiResponse
import com.vipcodeerror.brandup.data.model.LogginApiResponse
import io.reactivex.Single

interface ApiService {

    fun userLogin(phone : String, device_name: String) : Single<LogginApiResponse>

    fun registerPhoneNumber(phone: String) : Single<LogginApiResponse>

    fun getCatData(token : String) : Single<ApiCatDataResponse>
}