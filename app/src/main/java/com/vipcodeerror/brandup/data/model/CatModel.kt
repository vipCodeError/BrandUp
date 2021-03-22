package com.vipcodeerror.brandup.data.model

import com.google.gson.annotations.SerializedName

data class CatModel(
        @SerializedName("id")
        var id: Int,
        @SerializedName("cat_name")
        var catName: String,
        @SerializedName("image_url")
        var imgUrl: String,
)
