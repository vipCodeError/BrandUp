package com.vipcodeerror.brandup.data.api

import com.rx2androidnetworking.Rx2AndroidNetworking
import com.vipcodeerror.brandup.data.model.ApiResponse
import com.vipcodeerror.brandup.data.model.LogginApiResponse
import io.reactivex.Single

class ApiServiceImpl : ApiService{

    override fun userLogin(phone: String, device_name: String): Single<LogginApiResponse> {
        var bodyParam = HashMap<String, String>()
        bodyParam["phone"] = phone
        bodyParam["device_name"] = device_name
        return Rx2AndroidNetworking.post("http://127.0.0.1:8000/api/login").
        addBodyParameter(bodyParam).
        build().
        getObjectSingle(LogginApiResponse::class.java)
    }

    override fun registerPhoneNumber(phone: String): Single<LogginApiResponse> {
        return Rx2AndroidNetworking.post("http://127.0.0.1:8000/api/signup").
        addBodyParameter("phone", phone).build().
        getObjectSingle(LogginApiResponse::class.java)
    }

}