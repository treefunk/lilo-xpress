package com.myoptimind.lilo_xpress.guestlogin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GuestLoginViewModel : ViewModel() {
    val fullName        = MutableLiveData<String>()
    val agency          = MutableLiveData<String>()
    val attachedAgency  = MutableLiveData<String>()
    val emailAddress    = MutableLiveData<String>()
    val confirmReceipt  = MutableLiveData<String>()
    val divisionToVisit = MutableLiveData<String>()
    val purpose         = MutableLiveData<String>()
    val personToVisit   = MutableLiveData<String>()







}