package com.vipcodeerror.brandup.data.api

import java.io.File

class ApiHelper(private val apiService: ApiService)  {
    fun loginUser(phone: String, device_name: String) = apiService.userLogin(phone, device_name)

    fun catData(token : String) = apiService.getCatData(token)

    fun businessCatPref(catId: String, token: String) = apiService.postCatPref(catId, token)

    fun postBusinessDetails(bussName : String, phone: String, address : String,
                            logoUrl: String, location: String,
                            belongToWhichUser : String, catIdBelongTo: String, token: String) = apiService
            .postBussDetails(bussName, phone, address, logoUrl, location,
            belongToWhichUser, catIdBelongTo, token)

    fun uploadLogoImage(logoUrl : File, token: String) = apiService.uploadImage(logoUrl, token)
}