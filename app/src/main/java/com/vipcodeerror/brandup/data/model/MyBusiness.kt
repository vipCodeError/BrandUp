package com.vipcodeerror.brandup.data.model

import com.google.gson.annotations.SerializedName

data class MyBusiness(
        @SerializedName("business_name")
        var businessName: String,
        @SerializedName("logo_name")
        var logoName: String,
        @SerializedName("busines_location")
        var businessLocation: String,
        @SerializedName("logo_url")
        var logoImgUrl: String

)
