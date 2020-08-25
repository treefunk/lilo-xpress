package com.myoptimind.lilo_xpress.cesbie

import com.myoptimind.lilo_xpress.cesbie.api.CesbieLoginResponse
import com.myoptimind.lilo_xpress.cesbie.api.CesbieService
import com.myoptimind.lilo_xpress.data.DropDownType
import com.myoptimind.lilo_xpress.data.Option
import com.myoptimind.lilo_xpress.shared.DropdownDataSource
import com.myoptimind.lilo_xpress.shared.api.CitiesResponse
import javax.inject.Inject

class CesbieRepository
@Inject
constructor(
    val dropdownDataSource: DropdownDataSource,
    val cesbieService: CesbieService
)
{

    val regions   = dropdownDataSource.regions
    val persons          = dropdownDataSource.persons


    init {
            dropdownDataSource.fetchGuestInfoStep3()
    }

    fun getSelectedCesbie(index: String): Option {
        return dropdownDataSource.getSelected(index,DropDownType.PERSONS)
    }

    fun getSelectedRegion(index: String?): Option = dropdownDataSource.getSelected(index,DropDownType.REGION)


    fun fetchStaff() {
        dropdownDataSource.fetchGuestInfoStep2()
    }

    suspend fun cesbieLogin(
        staffId: String,
        temperature: String,
        region: String,
        city: String,
        healthCondition: String
    ): CesbieLoginResponse {
        return cesbieService.loginCesbie(
            staffId,
            temperature,
            region,
            city,
            healthCondition
        )
    }

    suspend fun cesbieLogout(
        staffId: String
    ): CesbieLoginResponse {
        return cesbieService.logoutCesbie(staffId)
    }

    suspend fun fetchCities(region: String): CitiesResponse {
        return dropdownDataSource.getCities(region)
    }


}