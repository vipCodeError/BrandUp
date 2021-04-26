package com.vipcodeerror.brandup.data.model

import com.google.gson.annotations.SerializedName


data class HdImageModel(
        @SerializedName("id")
        val id: Int,
        @SerializedName("status")
        val status: Boolean,
        @SerializedName("url")
        val url : String
)
