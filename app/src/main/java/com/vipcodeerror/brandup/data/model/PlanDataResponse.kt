package com.vipcodeerror.brandup.data.model

import com.google.gson.annotations.SerializedName

data class PlanDataResponse(
        @SerializedName("success")
        var success : Boolean,
        @SerializedName("data")
        var data : List<PlanDataModel>,
        @SerializedName("message")
        var message : String
        )