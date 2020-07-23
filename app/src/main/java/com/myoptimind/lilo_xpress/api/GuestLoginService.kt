package com.myoptimind.lilo_xpress.api

import retrofit2.http.GET
import retrofit2.http.POST


interface GuestLoginService {

    @GET("character")
    suspend fun getFirstStepFields(): GuestFirstReponse

}