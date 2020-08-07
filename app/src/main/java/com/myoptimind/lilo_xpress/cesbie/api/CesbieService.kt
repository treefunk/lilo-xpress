package com.myoptimind.lilo_xpress.cesbie.api

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface CesbieService {

    @POST("visitors/cesbie-login")
    @FormUrlEncoded
    suspend fun loginCesbie(
        @Field("staff_id")
        staffId: String,
        @Field("temperature")
        temperature: String,
        @Field("place_of_origin")
        placeOfOrigin: String,
        @Field("health_condition")
        healthCondition: String
    ): CesbieLoginResponse

    @POST("visitors/cesbie-logout")
    @FormUrlEncoded
    suspend fun logoutCesbie(
        @Field("staff_id")
        staffId: String
    ): CesbieLoginResponse
}
