package com.vipcodeerror.brandup.data.model

import com.google.gson.annotations.SerializedName

data class BannerDataResponse (
        @SerializedName("success")
        var success : Boolean,
        @SerializedName("data")
        var data : List<BannerDataModel>,
        @SerializedName("message")
        var message : String
        )