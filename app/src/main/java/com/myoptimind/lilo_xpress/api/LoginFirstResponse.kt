package com.myoptimind.lilo_xpress.api

import com.google.gson.annotations.SerializedName
import com.myoptimind.lilo_xpress.data.Option

class LoginFirstResponse(
    @SerializedName("data")
    val data: Data,
    val meta: Meta

    ){
    class Data(
        val agency: List<Option>,
        @SerializedName("attached_agency")
        val attachedAgency: List<Option>
    )
}