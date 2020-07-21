package com.myoptimind.lilo_xpress.guestlogin.cit

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.myoptimind.lilo_xpress.R

class GuestLoginCitFragment : Fragment(R.layout.fragment_guest_login_cit) {
    
    companion object {
        fun newInstance(): GuestLoginCitFragment {
            val args = Bundle()
            val fragment = GuestLoginCitFragment()
            fragment.arguments = args
            return fragment
        }
    }
}