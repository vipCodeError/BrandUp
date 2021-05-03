package com.vipcodeerror.brandup.data.model

import com.google.gson.annotations.SerializedName

data class SupportDataResponse(
        @SerializedName("success")
        var success : Boolean,
        @SerializedName("data")
        var data : List<SupportModel>,
        @SerializedName("message")
        var message : String
)
