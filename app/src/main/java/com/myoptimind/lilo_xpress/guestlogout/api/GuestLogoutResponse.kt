package com.myoptimind.lilo_xpress.guestlogout.api

import com.google.gson.annotations.SerializedName
import com.myoptimind.lilo_xpress.data.Meta

class GuestLogoutResponse(
    val data: Data,
    val meta: Meta
) {
    class Data(
        @SerializedName("login_time_format")
        val loginTimeFormat: String,

        @SerializedName("logout_time_format")
        val logoutTimeFormat: String,

        val fullname: String,

        val agency: String,

        @SerializedName("attached_agency")
        val attachedAgency: String,

        @SerializedName("email_address")
        val emailAddress: String,

        val division: String,

        @SerializedName("person_visited")
        val personVisited: String,

        val purpose: String,

        val temperature: String,

        @SerializedName("place_of_origin")
        val placeOfOrigin: String,

        @SerializedName("login_time")
        val loginTime: String,

        @SerializedName("logout_time")
        val logoutTime: String,

        val duration: String
    )
}