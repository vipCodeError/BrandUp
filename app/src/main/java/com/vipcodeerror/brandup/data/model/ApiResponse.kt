package com.vipcodeerror.brandup.data.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
        @SerializedName("id")
        val id: Int,
        @SerializedName("status")
        val status: Boolean,
        @SerializedName("message")
        val token : String
)
