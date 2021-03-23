package com.vipcodeerror.brandup.data.model

import com.google.gson.annotations.SerializedName

data class ImageApiResponse (
        @SerializedName("image_url")
        var imageUrl : String,
        @SerializedName("status")
        var status : String,
        @SerializedName("message")
        var message : String,
)