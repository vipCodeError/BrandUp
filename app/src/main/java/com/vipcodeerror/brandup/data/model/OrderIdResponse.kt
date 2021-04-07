package com.vipcodeerror.brandup.data.model

import com.google.gson.annotations.SerializedName

data class OrderIdResponse(
    @SerializedName("success")
    var success : String,
    @SerializedName("order_id")
    var orderId : String
)
