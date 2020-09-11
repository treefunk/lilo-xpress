package com.myoptimind.lilo_xpress.data

import androidx.lifecycle.MutableLiveData


data class Option (
    val id: String? = null,
    var name: String? = null,
    var fullname: String? = null,
    var selected: Boolean = false
)