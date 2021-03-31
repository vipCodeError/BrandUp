package com.vipcodeerror.brandup.data.api

import java.io.File

class ApiHelper(private val apiService: ApiService)  {
    fun loginUser(phone: String, device_name: String) = apiService.userLogin(phone, device_name)

    fun catData(token : String) = apiService.getCatData(token)

    fun businessCatPref(userId: String, pref: String,token: String) = apiService.postCatPref(userId, pref, token)

    fun postBusinessDetails(bussName : String, phone: String, address : String, email: String, webN : String,
                            logoUrl: String, location: String,
                            belongToWhichUser : String, catIdBelongTo: String, token: String) = apiService
            .postBussDetails(bussName, phone, address, email, webN, logoUrl, location,
            belongToWhichUser, catIdBelongTo, token)

    fun uploadLogoImage(logoUrl : File, token: String) = apiService.uploadImage(logoUrl, token)

    fun getHomeSelectedData(token: String) = apiService.homeSelectedData(token)

    fun getHomeData(catId: String, token: String) = apiService.homeData(catId, token)

    fun getHomeSubData(catId: String, token: String) = apiService.homeSubData(catId, token)

    fun getBussDetails(userId : String, token: String) = apiService.getBussinessDet(userId, token)

    fun getBussinessDetailsForHome(userId : String, id :String, token: String) = apiService.getBussinessDetForHome(userId, id, token)

    fun setBussPref(prefId : String , userId : String, token: String) = apiService.setBusinessPref(prefId, userId, token)

    fun getBottomBannerResponse(prefId : String, token: String) = apiService.getBottomBanner(prefId, token)

    fun requestForImageGeneration(user_id : String, pref_id: String, token: String) = apiService.requestImageGenerator(user_id, pref_id, token)

}