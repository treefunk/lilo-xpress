package com.myoptimind.lilo_xpress.shared

import androidx.fragment.app.Fragment
import com.myoptimind.lilo_xpress.guestlogin.GuestLoginTab

abstract class TabChildFragment<T>: Fragment() {
    lateinit var guestTabChanger: TabHost<T>
}

interface TabHost<in T> {
    fun changeTab(tab: T)
}