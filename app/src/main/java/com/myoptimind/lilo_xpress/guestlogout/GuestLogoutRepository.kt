package com.myoptimind.lilo_xpress.guestlogout

import com.myoptimind.lilo_xpress.api.GuestLogoutCheckPinResponse
import com.myoptimind.lilo_xpress.api.GuestLogoutService
import com.myoptimind.lilo_xpress.data.Result
import com.myoptimind.lilo_xpress.shared.DropdownDataSource
import javax.inject.Inject

class GuestLogoutRepository
@Inject
constructor(
    val guestLogoutService: GuestLogoutService
){
    init {

    }
    suspend fun checkPinValidity(
        pinCode: String
    ): GuestLogoutCheckPinResponse {
        return guestLogoutService.checkPinValidity(pinCode)
    }

}