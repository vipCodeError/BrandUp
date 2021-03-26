package com.vipcodeerror.brandup.data.model

import com.google.gson.annotations.SerializedName

data class BusinessDetailsModel (
    @SerializedName("id")
    var id: String,
    @SerializedName("business_name")
    var bName :String,
    @SerializedName("phone_no")
    var bPhone :String,
    @SerializedName("addr")
    var bAddress :String,
    @SerializedName("logo_url")
    var logoUrl :String,
    @SerializedName("location")
    var bLocation :String,
    @SerializedName("user_belong_to")
    var userId : String,
    @SerializedName("cat_belong_to")
    var catId : String,
    @SerializedName("cat_name")
    var catName : String
)