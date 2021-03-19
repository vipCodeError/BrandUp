package com.vipcodeerror.brandup.data.model

import com.google.gson.annotations.SerializedName

data class LogginApiResponse(
        @SerializedName("id")
        val id: Int,
        @SerializedName("status")
        val status: Boolean,
        @SerializedName("token")
        val token : String
)
