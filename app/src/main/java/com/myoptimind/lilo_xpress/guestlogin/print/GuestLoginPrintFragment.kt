package com.myoptimind.lilo_xpress.guestlogin.print

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.myoptimind.lilo_xpress.R

class GuestLoginPrintFragment : Fragment(R.layout.fragment_guest_login_print) {
    companion object {
        fun newInstance(): GuestLoginPrintFragment{
            val args = Bundle()
            
            val fragment = GuestLoginPrintFragment()
            fragment.arguments = args
            return fragment
        }
    }
}