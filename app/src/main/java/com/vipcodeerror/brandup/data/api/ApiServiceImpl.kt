package com.vipcodeerror.brandup.data.api

import com.androidnetworking.interceptors.HttpLoggingInterceptor
import com.rx2androidnetworking.Rx2AndroidNetworking
import com.vipcodeerror.brandup.data.model.ApiCatDataResponse
import com.vipcodeerror.brandup.data.model.ApiResponse
import com.vipcodeerror.brandup.data.model.LogginApiResponse
import io.reactivex.Single
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class ApiServiceImpl : ApiService{

    override fun userLogin(phone: String, device_name: String): Single<LogginApiResponse> {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY // it should be none other wise large file cannot be upload'

        val okHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()


        var bodyParam = HashMap<String, String>()
        bodyParam["phone"] = phone
        bodyParam["device_name"] = device_name
        return Rx2AndroidNetworking.post("http://brandup.shopyculture.com/api/login").
        addBodyParameter(bodyParam)
                .setOkHttpClient(okHttpClient)
                .build()
                .getObjectSingle(LogginApiResponse::class.java)
    }

    override fun registerPhoneNumber(phone: String): Single<LogginApiResponse> {
        return Rx2AndroidNetworking.post("http://brandup.shopyculture.com/api/signup").
        addBodyParameter("phone", phone).build().
        getObjectSingle(LogginApiResponse::class.java)
    }

    override fun getCatData(token : String): Single<ApiCatDataResponse> {
        return Rx2AndroidNetworking.get("http://brandup.shopyculture.com/api/get_cat")
                .addHeaders("Authorization", "Bearer "+token)
                .build().
                getObjectSingle(ApiCatDataResponse::class.java)
    }

}