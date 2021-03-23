package com.vipcodeerror.brandup.data.api

import com.vipcodeerror.brandup.data.model.ApiCatDataResponse
import com.vipcodeerror.brandup.data.model.ApiResponse
import com.vipcodeerror.brandup.data.model.ImageApiResponse
import com.vipcodeerror.brandup.data.model.LogginApiResponse
import io.reactivex.Single
import java.io.File

interface ApiService {

    fun userLogin(phone : String, device_name: String) : Single<LogginApiResponse>

    fun getCatData(token : String) : Single<ApiCatDataResponse>

    fun postCatPref(catId: String, token: String) : Single<ApiResponse>

    fun postBussDetails(bussName : String, phone: String, address : String,
                        logoUrl: String, location: String,
                        belongToWhichUser : String, catIdBelongTo: String, token: String)  : Single<ApiResponse>

    fun uploadImage(logoUrl: File, tokens: String) : Single<ImageApiResponse>
}