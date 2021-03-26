package com.vipcodeerror.brandup.data.model

import com.google.gson.annotations.SerializedName
import com.vipcodeerror.brandup.data.model.home_modal.HomeSelectedModel

data  class BussinessDataResponse (
    @SerializedName("success")
    var success : Boolean,
    @SerializedName("data")
    var data : List<BusinessDetailsModel>,
    @SerializedName("message")
    var message : String
    )