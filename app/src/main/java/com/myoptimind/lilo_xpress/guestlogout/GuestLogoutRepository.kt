package com.myoptimind.lilo_xpress.guestlogout

import com.myoptimind.lilo_xpress.data.FeedbackExperience
import com.myoptimind.lilo_xpress.guestlogout.api.GuestLogoutCheckPinResponse
import com.myoptimind.lilo_xpress.guestlogout.api.GuestLogoutResponse
import com.myoptimind.lilo_xpress.guestlogout.api.GuestLogoutService
import javax.inject.Inject

class GuestLogoutRepository
@Inject
constructor(
    private val guestLogoutService: GuestLogoutService
){
    init {

    }
    suspend fun checkPinValidity(
        pinCode: String
    ): GuestLogoutCheckPinResponse {
        return guestLogoutService.checkPinValidity(pinCode)
    }

    suspend fun guestLogout(
        pinCode: String?,
        experience: FeedbackExperience?,
        feedback: String?
    ): GuestLogoutResponse {
        return guestLogoutService.logoutGuest(
            pinCode,
            experience?.id,
            feedback
        )
    }

}