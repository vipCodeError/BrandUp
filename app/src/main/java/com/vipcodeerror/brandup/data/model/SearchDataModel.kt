package com.vipcodeerror.brandup.data.model

import com.google.gson.annotations.SerializedName

data class SearchDataModel (
        @SerializedName("id")
        var id: String,
        @SerializedName("cat_name")
        var catName: String
        )