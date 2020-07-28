package com.myoptimind.lilo_xpress.guestlogout

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myoptimind.lilo_xpress.api.GuestLogoutCheckPinResponse
import com.myoptimind.lilo_xpress.data.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception

class GuestLogoutViewModel
@ViewModelInject
constructor(val guestLogoutRepository: GuestLogoutRepository)
    : ViewModel(){

    val _enterPin = MutableLiveData<Result<GuestLogoutCheckPinResponse>>()
    val enterPin: LiveData<Result<GuestLogoutCheckPinResponse>> = _enterPin

    fun validatePin(pinCode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _enterPin.postValue(Result.Loading)
            try{
                val response = guestLogoutRepository.checkPinValidity(pinCode)
                _enterPin.postValue(Result.Success(response))
            }catch (exception: Exception){
                _enterPin.postValue(Result.Error(Exception("Invalid Pin Code.")))
            }
        }
    }
}