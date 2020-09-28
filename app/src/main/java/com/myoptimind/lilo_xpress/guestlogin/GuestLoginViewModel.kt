package com.myoptimind.lilo_xpress.guestlogin


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.myoptimind.lilo_xpress.data.Option
import com.myoptimind.lilo_xpress.data.PurposeType
import com.myoptimind.lilo_xpress.guestlogin.api.GuestLoginResponse
import com.myoptimind.lilo_xpress.shared.toRequestBody
import com.myoptimind.lilo_xpress.data.Result
import com.myoptimind.lilo_xpress.shared.LiloPrinter
import com.myoptimind.lilo_xpress.shared.api.ProvincesCitiesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import timber.log.Timber

import java.io.File
import java.net.UnknownHostException


class GuestLoginViewModel
    @ViewModelInject
    constructor(
        val guestLoginRepository: GuestLoginRepository,
        val liloPrinter: LiloPrinter
    ): ViewModel() {




    /**
     * STEP 1
     */
    val fullName = MutableLiveData<String>()
    val agency = MutableLiveData<String>()
    val agencyIndex = MutableLiveData<String>()
    val attachedAgency = MutableLiveData<String>()
    val attachedAgencyIndex = MutableLiveData<String>()
    val mobileNo = MutableLiveData<String>()
    val emailAddress = MutableLiveData<String>()
    val confirmReceipt = MutableLiveData<Boolean>()
    val uploadedPhoto = MutableLiveData<File>()

    // pre-populated fields
    val agencies = guestLoginRepository.agencies
    val attachedAgencies = MediatorLiveData<Result<List<Option>>>()
    var attachedAgencies_ : List<Option>? = null

    /**
     * STEP 2
     */
    val purposeType = MutableLiveData<PurposeType>()


    val divisionToVisit = MutableLiveData<String>()
    val divisionToVisitIndex = MutableLiveData<String>()

    val purpose = MutableLiveData<String>()
    val purposeIndex = MutableLiveData<String>()

    val selectedPurposes = MutableLiveData<List<Option>>()

    val personToVisit = MutableLiveData<String>()
    val personToVisitIndex = MutableLiveData<String>()

    val selectedPersons = MutableLiveData<List<Option>>()


    // pre-populated fields
    val divisions = guestLoginRepository.divisions
    val purposes = guestLoginRepository.purposes
    val persons = guestLoginRepository.persons

    /**
     * STEP 3
     */

    val temperature = MutableLiveData<String>()
    val homeAddress = MutableLiveData<String>()
    val region = MutableLiveData<String>()
    val regionIndex = MutableLiveData<String>()
    val province = MutableLiveData<String>()
    val city   = MutableLiveData<String>()
    val experiencing = MutableLiveData<String>()
    val anyContact = MutableLiveData<Boolean>()
    val anyContactDetails = MutableLiveData<String>()
    val haveTravel = MutableLiveData<Boolean>()
    val haveTravelDetails = MutableLiveData<String>()
    val declaration = MutableLiveData<Boolean>()
/*
    val mobileNumber = MutableLiveData<String>()
    val healthCondition = MutableLiveData<String>()
*/

    // pre-populated fields
    val regions   = guestLoginRepository.regions
//    val cities    = MediatorLiveData<Result<List<Option>>>()
//    val provinces = MutableLiveData<Result<List<Option>>>()

    val provincesAndCities = MediatorLiveData<Result<ProvincesCitiesResponse>>()

    /**
     *  Result as LiveData
     */

    val loginResult = MutableLiveData<Result<GuestLoginResponse>>()
    val printResult = MutableLiveData<Result<String>>()


    init {
        confirmReceipt.value = false

        /**
         *  For attached agencies dropdown
         *  fires when agency is changed
         */
        attachedAgencies.addSource(agencyIndex) { index ->
            if(index != null){
                initAttachedAgency(index)
            }
        }

        provincesAndCities.addSource(regionIndex){ index ->
            if(index != null){
                initProvincesCities(index)
            }
        }


    }

    fun listenPurposeType(){
        Transformations.map(purposeType) { _ ->
            Timber.v("purpose type is transformed..")
            selectedPurposes.value = ArrayList()
            selectedPersons.value = ArrayList()
        }
    }

    private fun initProvincesCities(index: String) {
        val option = guestLoginRepository.getSelectedRegion(index)
        Timber.v("chosen is ${option.name}")
            Timber.v("Fetching provinces & cities for ${option.name}..")
            provincesAndCities.postValue(Result.Loading)
            viewModelScope.launch(IO) {
                try{
                    val response = guestLoginRepository.fetchProvincesCities(option.name!!)
//                    cities.postValue(Result.Success(response.data.cities))
//                    provinces.postValue(Result.Success(response.data.provinces))
                    provincesAndCities.postValue(Result.Success(response))
                }catch (exception: Exception){
                    Timber.e("Failed fetching cities\nError: ${exception.message}")
                    if(exception is UnknownHostException){
//                        cities.postValue(Result.Error(Exception("No Internet Connection, Unable fetching cities..")))
//                        provinces.postValue(Result.Error(Exception("No Internet Connection, Unable fetching provinces...")))
                        provincesAndCities.postValue(Result.Error(Exception("No Internet Connection, Unable to fetch cities and provinces")))
                    }else{
//                        cities.postValue(Result.Error(exception))
//                        provinces.postValue(Result.Error(exception))
                        provincesAndCities.postValue(Result.Error(exception))
                    }
                    delay(5000)
                    initProvincesCities(option.name!!)
                }
                city.postValue(null)
                province.postValue(null)
            }
    }

    private fun initAttachedAgency(index : String){
        val option = guestLoginRepository.getSelectedAttachedAgency(index)
        Timber.v("chosen is ${option.name}")
        if (!option.id.equals("0") && option.id != null) {
            Timber.v("Fetching attached agencies for ${option.name}..")
            attachedAgencies.postValue(Result.Loading)
            viewModelScope.launch(IO) {
                try{
                    val response = guestLoginRepository.fetchAttachedAgencies(option.id)
                    attachedAgencies.postValue(Result.Success(response.data))
                    attachedAgencies_ = response.data
                }catch (exception: Exception){
                    Timber.e("Failed fetching attached agency\nError: ${exception.message}")
                    if(exception is UnknownHostException){
                        attachedAgencies.postValue(Result.Error(Exception("No Internet Connection, Unable fetching attached agencies..")))
                    }else{
                        attachedAgencies.postValue(Result.Error(exception))
                    }
                    delay(5000)
                    initAttachedAgency(index)
                }
                attachedAgency.postValue(null)
                attachedAgencyIndex.postValue(null)
            }
        }
    }

    fun saveStep1(
        fullname: String,
        mobileNo: String,
        emailAddress: String,
        confirmReceipt: Boolean
    ): Boolean {
        if (
            fullname.isBlank() ||
            this.agencyIndex.value.isNullOrBlank() ||
            this.attachedAgencyIndex.value.isNullOrBlank() ||
            emailAddress.isBlank() ||
            mobileNo.isBlank() ||
            this.uploadedPhoto.value == null
        ) {
            return false
        }

        this.fullName.value = fullname
        this.agency.value = guestLoginRepository.getSelectedAgency(this.agencyIndex.value).name
        this.attachedAgency.value = this.attachedAgencies_?.get(this.attachedAgencyIndex.value!!.toInt())?.name
        this.mobileNo.value = mobileNo
        this.emailAddress.value = emailAddress
        this.confirmReceipt.value = confirmReceipt
        return true
    }

    fun saveStep2(): Boolean {

        Timber.v("Selected Services: \n${selectedPurposes.value?.map { "${it.name}" }}")
        Timber.v("Selected Persons: \n${selectedPersons.value?.map { "${it.fullname}" }}")

        when(purposeType.value){
            PurposeType.SERVICES -> {
                this.selectedPersons.value = listOf()
                this.divisionToVisit.value = guestLoginRepository.getSelectedDivision(this.divisionToVisitIndex.value).name
                this.purpose.value         = selectedPurposes.value?.map { it.id }?.joinToString(",")
                if(this.purpose.value.isNullOrBlank() || this.divisionToVisitIndex.value.isNullOrBlank()){ return false }
            }
            PurposeType.PERSON -> {
                this.divisionToVisit.value = null
                this.divisionToVisitIndex.value = null
                this.selectedPurposes.value = listOf()
                this.personToVisit.value   = selectedPersons.value?.map { it.id }?.joinToString(",")
                if(this.personToVisit.value.isNullOrBlank()){ return false }
            }
            null -> { return false }
        }
        return true
    }


    fun saveStep3(
        temperature: String,
        homeAddress: String,
        region: String,
        city: String,
        anyContactDetails: String,
        haveTravelDetails: String,
        cbDeclration: Boolean
    ): Boolean {

        this.temperature.value = temperature
        this.homeAddress.value = homeAddress
        this.region.value = region
        this.city.value = city

        if(this.anyContact.value == true){
            this.anyContactDetails.value = anyContactDetails
        }

        if(this.haveTravel.value == true){
            this.haveTravelDetails.value = haveTravelDetails
        }

        if (
            temperature.isBlank() ||
            homeAddress.isBlank() ||
            region.isBlank() ||
            city.isBlank()
        ) {
            return false
        }

        if((this.anyContact.value == true && anyContactDetails.isBlank()) ||
            (this.haveTravel.value == true && haveTravelDetails.isBlank())){
            return false
        }

        if(!cbDeclration){
            return false
        }



        return true
    }






    fun loginGuest() {

        viewModelScope.launch(Dispatchers.IO) {
            loginResult.postValue(Result.Loading)
            val uploaded = uploadedPhoto.value
            val requestBody: RequestBody = RequestBody.create(MediaType.parse("image/*"), uploaded)
            val multipart = MultipartBody.Part.createFormData(
                "photo",
                uploaded!!.name,
                requestBody
            )
            try{
                val res = guestLoginRepository.guestLogin(
                    fullName.value?.toRequestBody()!!,
                    guestLoginRepository.getSelectedAgency(agencyIndex.value).id?.toRequestBody()!!,
                    this@GuestLoginViewModel.attachedAgencies_?.get(this@GuestLoginViewModel.attachedAgencyIndex.value!!.toInt())?.id?.toRequestBody()!!,
                    emailAddress.value?.toRequestBody()!!,
                    when(confirmReceipt.value!!){
                        true -> "1".toRequestBody()
                        false -> "0".toRequestBody()
                    },
                    multipart,
                    guestLoginRepository.getSelectedDivision(divisionToVisitIndex.value).id?.toRequestBody()!!,
                    getSelected(selectedPurposes.value),
                    getSelected(selectedPersons.value),
                    temperature.value?.toRequestBody()!!,
                    homeAddress.value?.toRequestBody()!!,
                    region.value?.toRequestBody()!!,
                    if(province.value.isNullOrBlank()) "".toRequestBody() else province.value!!.toRequestBody(),
                    city.value?.toRequestBody()!!,
                    mobileNo.value!!.toRequestBody(),
                    (if(anyContact.value == true) "1" else "0").toRequestBody(),
                    if(anyContactDetails.value.isNullOrBlank()) "".toRequestBody() else anyContactDetails.value!!.toRequestBody(),
                    (if(haveTravel.value == true) "1" else "0").toRequestBody(),
                    if(haveTravelDetails.value.isNullOrBlank()) "".toRequestBody() else haveTravelDetails.value!!.toRequestBody(),
                    experiencing.value!!.toRequestBody()
                )
                loginResult.postValue(Result.Success(res))
            }catch (exception: Exception){
                Timber.e(exception)
                loginResult.postValue(Result.Error(exception))
            }

        }
    }

    private fun getSelected(options: List<Option>?): List<RequestBody> {
        if(options != null){
            return options.map{ option ->
                "${option.id}"
            }.toRequestBody()
        }
        return listOf()
    }


    fun printData(
        guestLoginResponse: GuestLoginResponse
    ){
        val data = guestLoginResponse.data
        viewModelScope.launch(IO) {
            printResult.postValue(Result.Loading)
            try{
                liloPrinter.printReceipt(
                    "CESB LETTERHEAD",
                    data.loginTimeFormat,
                    data.fullname,
                    data.agency,
                    data.attachedAgency,
                    data.emailAddress,
                    data.divisionToVisit,
                    data.personToVisit,
                    data.purpose,
                    data.temperature,
                    data.region,
                    data.city,
                    data.pinCode,
                    data.loginTimeFormat
                )

                printResult.postValue(Result.Success("Successfully printed receipt"))
            }catch (exception: Exception){
                printResult.postValue(Result.Error(exception))
            }
        }
    }




    fun resetData(){

        guestLoginRepository.fetchDropdownData()


        fullName.value = null
        agency.value = null
        agencyIndex.value = null
        attachedAgency.value = null
        attachedAgencyIndex.value = null
        mobileNo.value = null
        emailAddress.value = null
        confirmReceipt.value = false
        uploadedPhoto.value = null
        purposeType.value = null
        divisionToVisit.value = null
        divisionToVisitIndex.value = null
        purpose.value = null
        selectedPurposes.value = ArrayList()
        selectedPersons.value = ArrayList()
        personToVisit.value = null
        temperature.value = null
        homeAddress.value = null
        anyContact.value = null
        anyContactDetails.value = null
        haveTravel.value = null
        haveTravelDetails.value = null
        declaration.value = null
        region.value = null
        regionIndex.value = null
        province.value = null
        city.value = null
        loginResult.value = null
        printResult.value = null
        experiencing.value = null

    }
}