package com.myoptimind.lilo_xpress.shared

import androidx.fragment.app.Fragment
import com.myoptimind.lilo_xpress.guestlogin.GuestLoginTab

abstract class TabChildFragment<T>: Fragment() {
    lateinit var guestTabChanger: TabHost<T>
    lateinit var parentFrag: Fragment
}

interface TabHost<in T> {
    fun changeTab(tab: T, initial: Boolean = false)
}