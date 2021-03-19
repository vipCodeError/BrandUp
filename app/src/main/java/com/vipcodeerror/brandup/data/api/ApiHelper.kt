package com.vipcodeerror.brandup.data.api

class ApiHelper(private val apiService: ApiService)  {
    fun loginUser(phone: String, device_name: String) = apiService.userLogin(phone, device_name)

    fun registerPhoneNumber(phone: String) = apiService.registerPhoneNumber(phone)

}