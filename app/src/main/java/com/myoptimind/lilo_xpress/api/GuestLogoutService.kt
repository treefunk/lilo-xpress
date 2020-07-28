package com.myoptimind.lilo_xpress.api

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
}