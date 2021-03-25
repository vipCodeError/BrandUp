package com.vipcodeerror.brandup.data.model.home_modal

import com.google.gson.annotations.SerializedName

data class HomeSelectedModel (
    @SerializedName("id")
    var id: String,
    @SerializedName("cat_id")
    var catId : String,
    @SerializedName("sub_id")
    var subId : String,
    @SerializedName("is_sub_shown")
    var isSubShown : String
)