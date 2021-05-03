package com.vipcodeerror.brandup.data.model

import com.google.gson.annotations.SerializedName

data class SupportModel(

        @SerializedName("id")
        var id: String,
        @SerializedName("email")
        var email: String,
        @SerializedName("phone")
        var phone :String
)
