package com.myoptimind.lilo_xpress.guestlogout.api

import com.myoptimind.lilo_xpress.guestlogout.api.GuestLogoutCheckPinResponse
import com.myoptimind.lilo_xpress.guestlogout.api.GuestLogoutResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface GuestLogoutService {

    @POST("visitors/logout/step-1")
    @FormUrlEncoded
    suspend fun checkPinValidity(
        @Field("pin_code")
        pinCode: String
    ): GuestLogoutCheckPinResponse

    @POST("visitors/logout/step-2")
    @FormUrlEncoded
    suspend fun logoutGuest(
        @Field("pin_code")
        pinCode: String?,
        @Field("overall_experience")
        experience: String?,
        @Field("feedback")
        feedback: String?
    ): GuestLogoutResponse
}