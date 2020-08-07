package com.myoptimind.lilo_xpress.guestlogout

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myoptimind.lilo_xpress.guestlogout.api.GuestLogoutCheckPinResponse
import com.myoptimind.lilo_xpress.data.FeedbackExperience
import com.myoptimind.lilo_xpress.data.Result
import com.myoptimind.lilo_xpress.guestlogin.api.GuestLoginResponse
import com.myoptimind.lilo_xpress.guestlogout.api.GuestLogoutResponse
import com.myoptimind.lilo_xpress.shared.LiloPrinter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.Exception

class GuestLogoutViewModel
@ViewModelInject
constructor(
    val guestLogoutRepository: GuestLogoutRepository,
    val liloPrinter: LiloPrinter
)
    : ViewModel(){

    val pin = MutableLiveData<String>()

    val _enterPin = MutableLiveData<Result<GuestLogoutCheckPinResponse>>()
    val enterPin: LiveData<Result<GuestLogoutCheckPinResponse>> = _enterPin

    val experience = MutableLiveData<FeedbackExperience>()
    val feedback   = MutableLiveData<String>()

    val guestLogout = MutableLiveData<Result<GuestLogoutResponse>>()
    val printResult = MutableLiveData<Result<String>>()

    fun validatePin(pinCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _enterPin.postValue(Result.Loading)
            try{
                val response = guestLogoutRepository.checkPinValidity(pinCode)
                if(response.meta.status.equals("200")){
                    _enterPin.postValue(Result.Success(response))
                    pin.postValue(pinCode)
                }else{
                    _enterPin.postValue(Result.Error(Exception(response.meta.message)))
                }
            }catch (exception: Exception){
                _enterPin.postValue(Result.Error(Exception("Invalid Pin Code.")))
                pin.postValue(null)
            }
        }
    }

    fun resetData(){
        _enterPin.value = null
        experience.value = null
        feedback.value = null
        guestLogout.value = null
    }

    fun save(
        feedback: String
    ){
        viewModelScope.launch(Dispatchers.IO) {
            guestLogout.postValue(Result.Loading)
            try{
                val result = guestLogoutRepository.guestLogout(
                    pin.value,
                    experience.value,
                    feedback
                )
                guestLogout.postValue(Result.Success(result))
            }catch (exception: Exception){
                guestLogout.postValue(Result.Error(Exception(exception.message)))
            }
        }
    }

    fun printData(
        guestLogoutResponse: GuestLogoutResponse
    ){
        val data = guestLogoutResponse.data
        viewModelScope.launch(Dispatchers.IO) {
            printResult.postValue(Result.Loading)
            try{
                liloPrinter.printReceipt(
                    "CESB LETTERHEAD",
                    data.loginTime,
                    data.fullname,
                    data.agency,
                    data.attachedAgency,
                    data.emailAddress,
                    data.division,
                    data.personVisited,
                    data.purpose,
                    data.temperature,
                    data.placeOfOrigin,
                    null,
                    data.loginTimeFormat,
                    data.logoutTimeFormat,
                    data.duration
                )

                printResult.postValue(Result.Success("Successfully printed receipt"))
            }catch (exception: Exception){
                printResult.postValue(Result.Error(exception))
            }
        }
    }
}