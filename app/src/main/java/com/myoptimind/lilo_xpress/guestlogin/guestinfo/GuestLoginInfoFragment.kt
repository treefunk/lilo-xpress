package com.myoptimind.lilo_xpress.guestlogin.guestinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.myoptimind.lilo_xpress.R

class GuestLoginInfoFragment : Fragment(R.layout.fragment_guest_login_info) {

    companion object {
        fun newInstance(): GuestLoginInfoFragment {
            val args = Bundle()

            val fragment = GuestLoginInfoFragment()
            fragment.arguments = args
            return fragment
        }
    }
}