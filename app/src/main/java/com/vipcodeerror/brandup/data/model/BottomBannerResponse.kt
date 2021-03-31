package com.vipcodeerror.brandup.data.model

import com.google.gson.annotations.SerializedName

data class BottomBannerResponse(
        @SerializedName("success")
        var success : Boolean,
        @SerializedName("data")
        var data : List<BottomBannerModel>,
        @SerializedName("message")
        var message : String
)
