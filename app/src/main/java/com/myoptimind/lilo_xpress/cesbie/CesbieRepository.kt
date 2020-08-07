package com.myoptimind.lilo_xpress.cesbie

import com.myoptimind.lilo_xpress.cesbie.api.CesbieLoginResponse
import com.myoptimind.lilo_xpress.cesbie.api.CesbieService
import com.myoptimind.lilo_xpress.data.DropDownType
import com.myoptimind.lilo_xpress.data.Option
import com.myoptimind.lilo_xpress.shared.DropdownDataSource
import javax.inject.Inject

class CesbieRepository
@Inject
constructor(
    val dropdownDataSource: DropdownDataSource,
    val cesbieService: CesbieService
)
{

    val placeOfOrigins   = dropdownDataSource.placeOfOrigin
    val persons          = dropdownDataSource.persons


    init {
            dropdownDataSource.fetchGuestInfoStep3()
    }

    fun getSelectedCesbie(index: String): Option {
        return dropdownDataSource.getSelected(index,DropDownType.PERSONS)
    }

    fun fetchStaff() {
        dropdownDataSource.fetchGuestInfoStep2()
    }

    suspend fun cesbieLogin(
        staffId: String,
        temperature: String,
        placeOfOrigin: String,
        healthCondition: String
    ): CesbieLoginResponse {
        return cesbieService.loginCesbie(
            staffId,
            temperature,
            placeOfOrigin,
            healthCondition
        )
    }

    suspend fun cesbieLogout(
        staffId: String
    ): CesbieLoginResponse {
        return cesbieService.logoutCesbie(staffId)
    }


}