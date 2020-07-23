package com.myoptimind.lilo_xpress.api

import com.google.gson.annotations.SerializedName
import com.myoptimind.lilo_xpress.data.Option

class GuestFirstReponse(
    val info: Info,
    @SerializedName("results") // TODO: change to "data" when testing in prod
    val data: List<Option>,
    val meta: Meta

    ){
    class Data(
        val agency: List<Option>,

        @SerializedName("attached_agency")
        val attachedAgency: List<Option>
    )


    class Info()
}