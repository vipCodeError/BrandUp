package com.vipcodeerror.brandup.data.model

import com.google.gson.annotations.SerializedName

data class PlanDataModel (
        @SerializedName("id")
        var id: String,
        @SerializedName("plan_name")
        var planName : String,
        @SerializedName("download_limit")
        var downloadLimit : String,
        @SerializedName("share_limit")
        var shareLimit : String,
        @SerializedName("days_limit")
        var dayLimit: String,
        @SerializedName("business_card_limit")
        var bCardLimit : String,
        @SerializedName("price")
        var planPrice: String
)