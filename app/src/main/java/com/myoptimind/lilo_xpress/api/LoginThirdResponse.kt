package com.myoptimind.lilo_xpress.api

import com.google.gson.annotations.SerializedName
import com.myoptimind.lilo_xpress.data.Option

class LoginThirdResponse(
    @SerializedName("data")
    val data: Data,
    val meta: Meta

    ){
    class Data(
        @SerializedName("place_of_origin")
        val placeOfOrigins: List<Option>
    )
}