package com.vipcodeerror.brandup.data.model

import com.google.gson.annotations.SerializedName

data class TrendingDataModel (
        @SerializedName("id")
        var id: String,
        @SerializedName("cat_name")
        var catName: String,
        @SerializedName("sub_name")
        var subName: String,
        @SerializedName("cat_id")
        var catId: String,
        @SerializedName("sub_id")
        var subId: String,
        @SerializedName("is_sub_shown")
        var isSubcat: String,
)