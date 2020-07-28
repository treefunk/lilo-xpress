package com.myoptimind.lilo_xpress.api

import com.google.gson.annotations.SerializedName

class GuestLoginResponse (
    val data: Data,

    val meta: Meta
){

    class Data(
        val fullname: String,

        val agency: String,

        @SerializedName("attached_agency")
        val attachedAgency: String,

        @SerializedName("email_address")
        val emailAddress: String,

        @SerializedName("is_have_ecopy")
        val confirmReceipt: String,

        @SerializedName("division_to_visit")
        val divisionToVisit: String,

        val purpose: String,

        @SerializedName("person_to_visit")
        val personToVisit: String,

        val temperature: String,

        @SerializedName("place_of_origin")
        val placeOfOrigin: String,

        @SerializedName("mobile_number")
        val mobileNumber: String,

        @SerializedName("health_condition")
        val healthCondition: String,

        @SerializedName("pin_code")
        val pinCode: String
    )
}