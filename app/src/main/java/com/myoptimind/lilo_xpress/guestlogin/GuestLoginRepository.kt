package com.myoptimind.lilo_xpress.guestlogin

import com.myoptimind.lilo_xpress.api.GuestFirstReponse
import com.myoptimind.lilo_xpress.api.GuestLoginService
import javax.inject.Inject


class GuestLoginRepository
@Inject
constructor(val guestLoginService: GuestLoginService) {

    suspend fun getFirstStepData(): GuestFirstReponse {
        return guestLoginService.getFirstStepFields()
    }

}