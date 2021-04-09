package com.vipcodeerror.brandup.data.model

import com.google.gson.annotations.SerializedName

data class LogginApiResponse(
        @SerializedName("id")
        val id: Int,
        @SerializedName("status")
        val status: Boolean,
        @SerializedName("token")
        val token : String,
        @SerializedName("pref_business")
        val prefBusiness : String,
        @SerializedName("is_already_exits")
        val isAlreadyExist : String,
        @SerializedName("plan_id")
        val planId: String,
        @SerializedName("plan_name")
        val planName : String
)
