package com.vipcodeerror.brandup.data.model

import com.google.gson.annotations.SerializedName

data class PlanDataModel (
        @SerializedName("id")
        var id: String,
        @SerializedName("download_limit")
        var downloadLimit : String,
        @SerializedName("share_limit")
        var shareLimit : String,
        @SerializedName("day_limit")
        var dayLimit: String,
        @SerializedName("business_card_limit")
        var bCardLimit : String,
        @SerializedName("price")
        var planPrice: String
)