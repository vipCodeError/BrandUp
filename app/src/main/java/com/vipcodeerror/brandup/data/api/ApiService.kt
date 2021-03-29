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

    fun postBussDetails(bussName : String, phone: String, address : String,
                        logoUrl: String, location: String,
                        belongToWhichUser : String, catIdBelongTo: String, token: String)  : Single<ApiResponse>

    fun uploadImage(logoUrl: File, tokens: String) : Single<ImageApiResponse>

    fun homeSelectedData(token: String) : Single<HomeSelectedApiResponse>

    fun homeData(catId: String, token: String) : Single<ApiHomeDataResponse>

    fun homeSubData(catId: String, token: String) : Single<ApiHomeDataResponse>

    fun getBussinessDet(userId : String, token: String) : Single<BussinessDataResponse>

    fun getBussinessDetForHome(userId : String, id:String, token: String) : Single<BussinessDataResponse>

    fun setBusinessPref(prefId : String , userId : String, token: String) : Single<ApiResponse>
}