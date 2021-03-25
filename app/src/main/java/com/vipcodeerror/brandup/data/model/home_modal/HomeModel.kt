package com.vipcodeerror.brandup.data.model.home_modal

import com.google.gson.annotations.SerializedName

data class HomeModel(
        @SerializedName("url")
        var urlImage: String,
        @SerializedName("cat_name")
        var catName: String,
        @SerializedName("subcat_name")
        var subCatName: String,
        @SerializedName("cat_id")
        var catId : String,
        @SerializedName("sub_id")
        var subId : String
)
