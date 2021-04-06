package com.vipcodeerror.brandup.data.model

import com.google.gson.annotations.SerializedName

data class BannerDataModel (
        @SerializedName("url")
        var url: String,
        @SerializedName("type")
        var banerType: String,
        @SerializedName("slide_or_static")
        var isSlideOrStatic: String,
        @SerializedName("redirect_url")
        var redirectUrl: String,
        @SerializedName("cat_id")
        var catId: String,
        @SerializedName("sub_id")
        var subId: String,
        @SerializedName("is_sub_shown")
        var isSubShown: String,
        )