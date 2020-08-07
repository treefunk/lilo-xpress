package com.myoptimind.lilo_xpress.cesbie

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myoptimind.lilo_xpress.cesbie.api.CesbieLoginResponse
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import com.myoptimind.lilo_xpress.data.Result

class CesbieViewModel
@ViewModelInject
constructor(
    val cesbieRepository: CesbieRepository
): ViewModel() {

    val placeOfOrigins = cesbieRepository.placeOfOrigins
    val persons        = cesbieRepository.persons

    val personIndex    = MutableLiveData<String>()

    val loginResult =  MutableLiveData<Result<CesbieLoginResponse>>()
    val logoutResult = MutableLiveData<Result<CesbieLoginResponse>>()

    fun resetData(){
        loginResult.value = null
        logoutResult.value = null
        cesbieRepository.fetchStaff()
    }


    fun loginCesbie(
        temperature: String,
        placeOfOrigin: String,
        healthCondition: String
    ): Boolean {

        if(personIndex.value.isNullOrBlank() ||
                temperature.isBlank() ||
                placeOfOrigin.isBlank()){
            return false
        }

        viewModelScope.launch(IO) {
            loginResult.postValue(Result.Loading)
            try{

                val result = cesbieRepository.cesbieLogin(
                    cesbieRepository.getSelectedCesbie(personIndex.value!!).id!!,
                    temperature,
                    placeOfOrigin,
                    healthCondition
                )
                loginResult.postValue(Result.Success(result))
            }catch (exception: Exception){
                loginResult.postValue(Result.Error(exception))
            }

        }
        return true
    }

    fun logoutCesbie() : Boolean {
        if(personIndex.value.isNullOrBlank()){
            return false
        }
        viewModelScope.launch(IO){
            logoutResult.postValue(Result.Loading)
            try{
                val result = cesbieRepository.cesbieLogout(
                    cesbieRepository.getSelectedCesbie(personIndex.value!!).id!!
                )
                logoutResult.postValue(Result.Success(result))
            }catch (exception: Exception){
                logoutResult.postValue(Result.Error(exception))
            }
        }
        return true
    }

}