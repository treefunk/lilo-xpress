package com.myoptimind.lilo_xpress.api

import com.google.gson.annotations.SerializedName
import com.myoptimind.lilo_xpress.data.Option

class LoginSecondResponse(
    @SerializedName("data")
    val data: Data,
    val meta: Meta

    ){
    class Data(
        @SerializedName("division")
        val divisions: List<Option>,

        @SerializedName("purpose")
        val purposes: List<Option>,

        @SerializedName("person_to_visit")
        val personsToVisit: List<Option>
    )
}