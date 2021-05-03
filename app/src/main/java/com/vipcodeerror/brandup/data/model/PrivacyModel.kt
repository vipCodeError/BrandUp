package com.vipcodeerror.brandup.data.model

import com.google.gson.annotations.SerializedName

data class PrivacyModel(
        @SerializedName("id")
        var id : String,
        @SerializedName("policy_data")
        var policyData: String,

)