package com.myoptimind.lilo_xpress.guestlogin


import com.myoptimind.lilo_xpress.guestlogin.api.GuestLoginService
import com.myoptimind.lilo_xpress.data.DropDownType
import com.myoptimind.lilo_xpress.data.Option
import com.myoptimind.lilo_xpress.guestlogin.api.AttachedAgenciesResponse
import com.myoptimind.lilo_xpress.guestlogin.api.GuestLoginResponse
import com.myoptimind.lilo_xpress.shared.DropdownDataSource
import com.myoptimind.lilo_xpress.shared.api.CitiesResponse
import com.myoptimind.lilo_xpress.shared.api.ProvincesCitiesResponse
import com.myoptimind.lilo_xpress.shared.toRequestBody
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject


class GuestLoginRepository
@Inject
constructor(
    val dropdownDataSource: DropdownDataSource,
    val guestLoginService: GuestLoginService
) {

    init {


    }


    val agencies         = dropdownDataSource.agencies
    val divisions        = dropdownDataSource.divisions
    val purposes         = dropdownDataSource.purposes
    val persons          = dropdownDataSource.persons
    val regions          = dropdownDataSource.regions

    fun getSelectedAgency(index: String?): Option = dropdownDataSource.getSelected(index,DropDownType.AGENCIES)
    fun getSelectedAttachedAgency(index: String?): Option = dropdownDataSource.getSelected(index,DropDownType.ATTACHED_AGENCIES)
    fun getSelectedDivision(index: String?): Option = dropdownDataSource.getSelected(index,DropDownType.DIVISIONS)
    fun getSelectedPurpose(index: String?): Option = dropdownDataSource.getSelected(index,DropDownType.PURPOSES)
    fun getSelectedPerson(index: String?): Option = dropdownDataSource.getSelected(index,DropDownType.PERSONS)
    fun getSelectedRegion(index: String?): Option = dropdownDataSource.getSelected(index,DropDownType.REGION)


    suspend fun guestLogin(
        fullname: RequestBody,
        agency: RequestBody,
        attachedAgency: RequestBody,
        emailAddress: RequestBody,
        confirmReceipt: RequestBody,
        uploadedPhoto: MultipartBody.Part,
        divisionToVisit: RequestBody,
        purpose: List<RequestBody>,
        personToVisit: List<RequestBody>,
        temperature: RequestBody,
        region: RequestBody,
        province: RequestBody,
        city: RequestBody,
        mobileNumber: RequestBody,
        anyContact: RequestBody,
        anyContactDetails: RequestBody,
        haveTravel: RequestBody,
        haveTravelDetails: RequestBody,
        healthCondition: RequestBody
    ): GuestLoginResponse {
        return guestLoginService.loginGuest(
            fullname,
            agency,
            attachedAgency,
            emailAddress,
            confirmReceipt,
            uploadedPhoto,
            divisionToVisit,
            purpose,
            personToVisit,
            temperature,
            region,
            province,
            city,
            mobileNumber,
            anyContact,
            anyContactDetails,
            haveTravel,
            haveTravelDetails,
            healthCondition
        )
    }

    fun fetchDropdownData() {
        dropdownDataSource.fetchGuestInfoStep1()
        dropdownDataSource.fetchGuestInfoStep2()

        if(dropdownDataSource.regions.value == null){
            dropdownDataSource.fetchGuestInfoStep3()
        }
    }

    suspend fun fetchAttachedAgencies(agencyId: String): AttachedAgenciesResponse {
        return guestLoginService.getAttachedAgencies(agencyId)
    }

    suspend fun fetchCities(region: String): CitiesResponse {
        return dropdownDataSource.getCities(region)
    }

    suspend fun fetchProvincesCities(region: String): ProvincesCitiesResponse {
        return dropdownDataSource.getProvincesCities(region)
    }

}