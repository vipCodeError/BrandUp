package com.vipcodeerror.brandup.data.api

import android.util.Log
import com.androidnetworking.common.Priority
import com.androidnetworking.interceptors.HttpLoggingInterceptor
import com.google.android.gms.common.api.Api
import com.rx2androidnetworking.Rx2AndroidNetworking
import com.vipcodeerror.brandup.data.model.*
import com.vipcodeerror.brandup.data.model.home_modal.ApiHomeDataResponse
import com.vipcodeerror.brandup.data.model.home_modal.HomeSelectedApiResponse
import io.reactivex.Single
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit

class ApiServiceImpl : ApiService {

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
        return Rx2AndroidNetworking.post("http://brandup.shopyculture.com/api/login").addBodyParameter(bodyParam)
                .setOkHttpClient(okHttpClient)
                .build()
                .getObjectSingle(LogginApiResponse::class.java)
    }

    override fun getCatData(token: String): Single<ApiCatDataResponse> {
        return Rx2AndroidNetworking.get("http://brandup.shopyculture.com/api/get_cat")
                .addHeaders("Authorization", "Bearer " + token)
                .build().getObjectSingle(ApiCatDataResponse::class.java)
    }

    override fun postCatPref(userId: String, pref: String, token: String): Single<ApiResponse> {
        return Rx2AndroidNetworking.post("http://brandup.shopyculture.com/api/buss_pref")
                .addHeaders("Authorization", "Bearer " + token)
                .addBodyParameter("user_id", userId)
                .addBodyParameter("buss_pref", pref)
                .build().getObjectSingle(ApiResponse::class.java)
    }

    override fun postBussDetails(bussName: String, phone: String, address: String, email: String, webN: String, logoUrl: String,
                                 location: String, belongToWhichUser: String, catIdBelongTo: String, token: String): Single<ApiResponse> {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY // it should be none other wise large file cannot be upload'

        val okHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()

        var params = mutableMapOf<String, String>()
        params["b_details"] = bussName
        params["b_phone"] = phone
        params["b_addr"] = address
        params["b_email"] = email
        params["b_image_url"] = logoUrl
        params["b_location"] = location
        params["b_user_id"] = belongToWhichUser
        params["b_cat_id"] = catIdBelongTo
        params["b_web"] = webN

        return Rx2AndroidNetworking.post("http://brandup.shopyculture.com/api/buss_det")
                .addHeaders("Authorization", "Bearer " + token)
                .addBodyParameter(params)
                .setOkHttpClient(okHttpClient)
                .build().getObjectSingle(ApiResponse::class.java)
    }

    override fun uploadImage(logoUrl: File, tokens: String): Single<ImageApiResponse> {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.HEADERS // it should be none other wise large file cannot be upload'

        val okHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()

        return Rx2AndroidNetworking.upload("http://brandup.shopyculture.com/api/upload_img")
                .addMultipartFile("image", logoUrl)
                .addHeaders("Authorization", "Bearer " + tokens)
                .setContentType("multipart/form-data")
                .addHeaders("content-type", "multipart/form-data")
                .setOkHttpClient(okHttpClient)
                .setPriority(Priority.HIGH)
                .build()
                .setUploadProgressListener { bytesUploaded, totalBytes ->
                    Log.d("upload progress", ((bytesUploaded * 100) / totalBytes).toString())
                }
                .getObjectSingle(ImageApiResponse::class.java)
    }

    override fun homeSelectedData(token: String): Single<HomeSelectedApiResponse> {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY // it should be none other wise large file cannot be upload'

        val okHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()

        return Rx2AndroidNetworking.get("http://brandup.shopyculture.com/api/get_selected_hdata")
                .addHeaders("Authorization", "Bearer " + token)
                .setOkHttpClient(okHttpClient)
                .build().getObjectSingle(HomeSelectedApiResponse::class.java)
    }

    override fun homeData(catId: String, token: String): Single<ApiHomeDataResponse> {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY // it should be none other wise large file cannot be upload'

        val okHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()

        return Rx2AndroidNetworking.post("http://brandup.shopyculture.com/api/get_home_data")
                .addHeaders("Authorization", "Bearer " + token)
                .addBodyParameter("cat_id", catId)
                .setOkHttpClient(okHttpClient)
                .build().getObjectSingle(ApiHomeDataResponse::class.java)
    }

    override fun homeSubData(catId: String, token: String): Single<ApiHomeDataResponse> {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY // it should be none other wise large file cannot be upload'

        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        return Rx2AndroidNetworking.post("http://brandup.shopyculture.com/api/get_home_subdata")
            .addHeaders("Authorization", "Bearer " + token)
            .addBodyParameter("cat_id", catId)
            .setOkHttpClient(okHttpClient)
            .build().getObjectSingle(ApiHomeDataResponse::class.java)
    }

    override fun getBussinessDet(userId: String, token: String): Single<BussinessDataResponse> {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY // it should be none other wise large file cannot be upload'

        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        return Rx2AndroidNetworking.post("http://brandup.shopyculture.com/api/get_buss_data")
            .addHeaders("Authorization", "Bearer " + token)
            .addBodyParameter("user_id", userId)
            .setOkHttpClient(okHttpClient)
            .build().getObjectSingle(BussinessDataResponse::class.java)
    }

    override fun setBusinessPref(prefId: String, userId: String, token: String): Single<ApiResponse> {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY // it should be none other wise large file cannot be upload'

        val okHttpClient = OkHttpClient().newBuilder()
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()

        return Rx2AndroidNetworking.post("http://brandup.shopyculture.com/api/set_buss_data")
            .addHeaders("Authorization", "Bearer " + token)
            .addBodyParameter("user_id", userId)
            .addBodyParameter("pref_id",prefId)
            .setOkHttpClient(okHttpClient)
            .build().getObjectSingle(ApiResponse::class.java)
    }

    override fun getBottomBanner(prefId: String, token: String): Single<BottomBannerResponse> {
        return Rx2AndroidNetworking.post("http://brandup.shopyculture.com/api/get_bottom_banner")
                .addHeaders("Authorization", "Bearer " + token)
                .addBodyParameter("pref_id",prefId)
                .build().getObjectSingle(BottomBannerResponse::class.java)
    }

    override fun requestImageGenerator(user_id: String, pref_id: String, token: String): Single<ApiResponse> {
        return Rx2AndroidNetworking.post("http://brandup.shopyculture.com/api/get_bottom_banner")
                .addHeaders("Authorization", "Bearer " + token)
                .addBodyParameter("user_id",user_id)
                .addBodyParameter("pref_id",pref_id)
                .build().getObjectSingle(ApiResponse::class.java)
    }

    override fun getBussinessDetForHome(userId: String, id:String, token: String): Single<BussinessDataResponse> {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY // it should be none other wise large file cannot be upload'

        val okHttpClient = OkHttpClient().newBuilder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(interceptor)
                .writeTimeout(30, TimeUnit.SECONDS)
                .build()

        return Rx2AndroidNetworking.post("http://brandup.shopyculture.com/api/get_buss_data_for_home")
                .addHeaders("Authorization", "Bearer " + token)
                .addBodyParameter("user_id", userId)
                .addBodyParameter("id", id)
                .setOkHttpClient(okHttpClient)
                .build().getObjectSingle(BussinessDataResponse::class.java)
    }
}