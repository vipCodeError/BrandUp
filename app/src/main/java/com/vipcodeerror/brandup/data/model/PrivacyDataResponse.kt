package com.vipcodeerror.brandup.data.model

import com.google.gson.annotations.SerializedName

data class PrivacyDataResponse(
        @SerializedName("success")
        var success : Boolean,
        @SerializedName("data")
        var data : List<PrivacyModel>,
        @SerializedName("message")
        var message : String
)
