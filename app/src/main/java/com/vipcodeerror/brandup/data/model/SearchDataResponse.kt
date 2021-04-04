package com.vipcodeerror.brandup.data.model

import com.google.gson.annotations.SerializedName

data class SearchDataResponse (
        @SerializedName("success")
        var success : Boolean,
        @SerializedName("data")
        var data : List<SearchDataModel>,
        @SerializedName("message")
        var message : String
        )