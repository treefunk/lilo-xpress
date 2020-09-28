package com.myoptimind.lilo_xpress.guestlogin.api

import com.myoptimind.lilo_xpress.shared.api.CitiesResponse
import com.myoptimind.lilo_xpress.shared.api.ProvincesCitiesResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


interface GuestLoginService {

    @GET("visitors/guest-login/step-1")
    suspend fun getLoginFirstStep(): LoginFirstResponse

    @GET("visitors/guest-login/step-2")
    suspend fun getLoginSecondStep(): LoginSecondResponse

    @GET("visitors/guest-login/step-3")
    suspend fun getLoginThirdStep(): LoginThirdResponse

    @GET("visitors/attached-agency/{agencyId}")
    suspend fun getAttachedAgencies(@Path("agencyId") agencyId : String): AttachedAgenciesResponse

    @POST("get-cities")
    @FormUrlEncoded
    suspend fun getCities(
        @Field("region")
        region: String
    ): CitiesResponse

    @POST("get-provinces-cities")
    @FormUrlEncoded
    suspend fun getProvincesCities(
        @Field("region")
        region: String
    ): ProvincesCitiesResponse


    /**
     * Upload image for single item
     */
    @JvmSuppressWildcards
    @Multipart
    @POST("visitors/guest-login")
    suspend fun loginGuest(
        @Part("fullname") fullname: RequestBody,
        @Part("agency") agency: RequestBody,
        @Part("attached_agency") attachedAgency: RequestBody,
        @Part("email_address") emailAddress: RequestBody,
        @Part("is_have_ecopy") confirmReceipt: RequestBody,
        @Part uploadedPhoto: MultipartBody.Part,
        @Part("division_to_visit") divisionToVisit: RequestBody,
        @Part("purpose[]") purpose: List<RequestBody>,
        @Part("person_to_visit[]") personToVisit: List<RequestBody>,
        @Part("temperature") temperature: RequestBody,
        @Part("home_address") homeAddress: RequestBody,
        @Part("region") region: RequestBody,
        @Part("province") province: RequestBody,
        @Part("city") city: RequestBody,
        @Part("mobile_number") mobileNumber: RequestBody,
        @Part("is_recent_contact") anyContact: RequestBody,
        @Part("recent_contact_details") anyContactDetails: RequestBody,
        @Part("is_travelled_locally") haveTravelLocally: RequestBody,
        @Part("travelled_locally_details") haveTravelDetails: RequestBody,
        @Part("health_condition") healthCondition: RequestBody
    ): GuestLoginResponse

}