package com.myoptimind.lilo_xpress.guestlogin


import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epson.epos2.Epos2Exception
import com.myoptimind.lilo_xpress.data.Option
import com.myoptimind.lilo_xpress.guestlogin.api.GuestLoginResponse
import com.myoptimind.lilo_xpress.shared.toRequestBody
import com.myoptimind.lilo_xpress.data.Result
import com.myoptimind.lilo_xpress.shared.LiloPrinter
import com.myoptimind.lilo_xpress.shared.MutableOptionList
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
    val emailAddress = MutableLiveData<String>()
    val confirmReceipt = MutableLiveData<Boolean>()
    val uploadedPhoto = MutableLiveData<File>()

    // pre-populated fields
    val agencies = guestLoginRepository.agencies
//    val attachedAgencies = guestLoginRepository.attachedAgencies
    val attachedAgencies = MediatorLiveData<Result<List<Option>>>()
    var attachedAgencies_ : List<Option>? = null





    /**
     * STEP 2
     */
    val divisionToVisit = MutableLiveData<String>()
    val divisionToVisitIndex = MutableLiveData<String>()

    val purpose = MutableLiveData<String>()
    val purposeIndex = MutableLiveData<String>()

    val personToVisit = MutableLiveData<String>()
    val personToVisitIndex = MutableLiveData<String>()

    // pre-populated fields
    val divisions = guestLoginRepository.divisions
    val purposes = guestLoginRepository.purposes
    val persons = guestLoginRepository.persons

    /**
     * STEP 3
     */

    val temperature = MutableLiveData<String>()
    val placeOfOrigin = MutableLiveData<String>()
    val placeOfOriginIndex = MutableLiveData<String>()
    val mobileNumber = MutableLiveData<String>()
    val healthCondition = MutableLiveData<String>()

    // pre-populated fields
    val placeOfOrigins = guestLoginRepository.placeOfOrigins

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
        emailAddress: String,
        confirmReceipt: Boolean
    ): Boolean {
        if (
            fullname.isBlank() ||
            this.agencyIndex.value.isNullOrBlank() ||
            this.attachedAgencyIndex.value.isNullOrBlank() ||
            emailAddress.isBlank() ||
            this.uploadedPhoto.value == null
        ) {
            return false
        }





        this.fullName.value = fullname
        this.agency.value = guestLoginRepository.getSelectedAgency(this.agencyIndex.value).name
        this.attachedAgency.value = this.attachedAgencies_?.get(this.attachedAgencyIndex.value!!.toInt())?.name
        this.emailAddress.value = emailAddress
        this.confirmReceipt.value = confirmReceipt
        return true
    }

    fun saveStep2(): Boolean {

        this.divisionToVisit.value = guestLoginRepository.getSelectedDivision(this.divisionToVisitIndex.value).name
        this.purpose.value         = guestLoginRepository.getSelectedPurpose(this.purposeIndex.value).name
        this.personToVisit.value   = guestLoginRepository.getSelectedPerson(this.personToVisitIndex.value).fullname


        if (
            this.divisionToVisitIndex.value.isNullOrBlank() ||
            this.purposeIndex.value.isNullOrBlank() ||
            this.personToVisitIndex.value.isNullOrBlank()
        ) {
            return false
        }


        return true
    }


    fun saveStep3(
        temperature: String,
        mobileNumber: String,
        placeOfOrigin: String,
        healthCondition: String
    ): Boolean {

        this.temperature.value = temperature
        this.placeOfOrigin.value = placeOfOrigin
        this.mobileNumber.value = mobileNumber
        this.healthCondition.value = healthCondition

        if (
            temperature.isBlank() ||
            placeOfOrigin.isBlank() ||
                    mobileNumber.isBlank() ||
                    healthCondition.isBlank()
        ) {
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
                    guestLoginRepository.getSelectedPurpose(purposeIndex.value).id?.toRequestBody()!!,
                    guestLoginRepository.getSelectedPerson(personToVisitIndex.value).id?.toRequestBody()!!,
                    temperature.value?.toRequestBody()!!,
                    placeOfOrigin.value?.toRequestBody()!!,
                    mobileNumber.value?.toRequestBody()!!,
                    healthCondition.value?.toRequestBody()!!
                )
                loginResult.postValue(Result.Success(res))
            }catch (exception: Exception){
                Timber.e(exception)
                loginResult.postValue(Result.Error(exception))
            }

        }

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
                    data.placeOfOrigin,
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
        emailAddress.value = null
        confirmReceipt.value = false
        uploadedPhoto.value = null
        divisionToVisit.value = null
        divisionToVisitIndex.value = null
        purpose.value = null
        purposeIndex.value = null
        personToVisit.value = null
        personToVisitIndex.value = null
        temperature.value = null
        placeOfOrigin.value = null
        placeOfOriginIndex.value = null
        mobileNumber.value = null
        healthCondition.value = null
        loginResult.value = null
        printResult.value = null

    }
}