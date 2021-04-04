package com.vipcodeerror.brandup.data.model

import com.google.gson.annotations.SerializedName

data class TrendingDataResponse (
        @SerializedName("success")
        var success : Boolean,
        @SerializedName("data")
        var data : List<TrendingDataModel>,
        @SerializedName("message")
        var message : String
        )