package com.vipcodeerror.brandup.data.model.home_modal

import com.google.gson.annotations.SerializedName

data class HomeSelectedApiResponse(
        @SerializedName("success")
        var success : Boolean,
        @SerializedName("data")
        var data : List<HomeSelectedModel>,
        @SerializedName("message")
        var message : String
)
