package com.vipcodeerror.brandup.data.model.home_modal

import com.google.gson.annotations.SerializedName
import com.vipcodeerror.brandup.data.model.CatModel

data class ApiHomeDataResponse(
        @SerializedName("success")
        var success : Boolean,
        @SerializedName("data")
        var data : List<HomeModel>,
        @SerializedName("status")
        var message : String
)
