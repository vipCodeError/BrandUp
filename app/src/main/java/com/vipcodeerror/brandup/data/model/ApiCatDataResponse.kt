package com.vipcodeerror.brandup.data.model

import com.google.gson.annotations.SerializedName

data class ApiCatDataResponse(
        @SerializedName("success")
        var success : Boolean,
        @SerializedName("data")
        var data : List<CatModel>,
        @SerializedName("message")
        var message : String
)

