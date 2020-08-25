package com.myoptimind.lilo_xpress.cesbie

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myoptimind.lilo_xpress.cesbie.api.CesbieLoginResponse
import com.myoptimind.lilo_xpress.data.Option
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch
import com.myoptimind.lilo_xpress.data.Result
import kotlinx.coroutines.delay
import timber.log.Timber
import java.net.UnknownHostException

class CesbieViewModel
@ViewModelInject
constructor(
    val cesbieRepository: CesbieRepository
): ViewModel() {

    val regions        = cesbieRepository.regions
    val cities  = MediatorLiveData<Result<List<Option>>>()
    var cities_ : List<Option>? = null
    val persons        = cesbieRepository.persons

    val region = MutableLiveData<String>()
    val regionIndex = MutableLiveData<String>()
    val city   = MutableLiveData<String>()

    val personIndex    = MutableLiveData<String>()

    val loginResult    =  MutableLiveData<Result<CesbieLoginResponse>>()
    val logoutResult   = MutableLiveData<Result<CesbieLoginResponse>>()

    init {
        cities.addSource(regionIndex){ index ->
            if(index != null){
                initCities(index)
            }
        }
    }

    private fun initCities(index: String) {
        val option = cesbieRepository.getSelectedRegion(index)
        Timber.v("chosen is ${option.name}")
        Timber.v("Fetching cities for ${option.name}..")
        cities.postValue(Result.Loading)
        viewModelScope.launch(IO) {
            try{
                val response = cesbieRepository.fetchCities(option.name!!)
                cities.postValue(Result.Success(response.data.cities))
                cities_ = response.data.cities
            }catch (exception: Exception){
                Timber.e("Failed fetching cities\nError: ${exception.message}")
                if(exception is UnknownHostException){
                    cities.postValue(Result.Error(Exception("No Internet Connection, Unable fetching cities..")))
                }else{
                    cities.postValue(Result.Error(exception))
                }
                delay(5000)
                initCities(option.name!!)
            }
            city.postValue(null)
        }
    }

    fun resetData(){
        loginResult.value = null
        logoutResult.value = null
        cesbieRepository.fetchStaff()
        region.value = null
        regionIndex.value = null
        city.value = null
    }


    fun loginCesbie(
        temperature: String,
        region: String,
        city: String,
        healthCondition: String
    ): Boolean {

        if(personIndex.value.isNullOrBlank() ||
                temperature.isBlank() ||
                region.isBlank() ||
                city.isBlank() ||
                healthCondition.isBlank()){
            return false
        }

        viewModelScope.launch(IO) {
            loginResult.postValue(Result.Loading)
            try{

                val result = cesbieRepository.cesbieLogin(
                    cesbieRepository.getSelectedCesbie(personIndex.value!!).id!!,
                    temperature,
                    region,
                    city,
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