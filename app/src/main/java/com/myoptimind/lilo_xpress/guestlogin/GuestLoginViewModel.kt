package com.myoptimind.lilo_xpress.guestlogin

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myoptimind.lilo_xpress.data.Option
import com.myoptimind.lilo_xpress.data.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class GuestLoginViewModel
    @ViewModelInject
    constructor(val guestLoginRepository: GuestLoginRepository): ViewModel() {

    val fullName        = MutableLiveData<String>()
    val agency          = MutableLiveData<String>()
    val attachedAgency  = MutableLiveData<String>()
    val emailAddress    = MutableLiveData<String>()
    val confirmReceipt  = MutableLiveData<String>()
    val divisionToVisit = MutableLiveData<String>()
    val purpose         = MutableLiveData<String>()
    val personToVisit   = MutableLiveData<String>()


    // pre-populated fields
    val agencies         = MutableLiveData<Result<List<Option>>>()
    val attachedAgencies = MutableLiveData<Result<List<Option>>>()

    init {

    }

    fun populateFields() {
        CoroutineScope(Dispatchers.IO).launch {
            val res = guestLoginRepository.getFirstStepData()
            Log.v("rest",res.toString())
            withContext(Main){
                agencies.value = Result.Success(res.data)
                for(option in res.data){
                    Log.v("rest","name - ${option.name}")
                }
            }
        }
    }




}