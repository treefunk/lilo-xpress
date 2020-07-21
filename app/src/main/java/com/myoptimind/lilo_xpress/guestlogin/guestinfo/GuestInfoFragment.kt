package com.myoptimind.lilo_xpress.guestlogin.guestinfo

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.myoptimind.lilo_xpress.R

class GuestInfoFragment : Fragment(R.layout.fragment_guest_info) {

    companion object {
        fun newInstance(): GuestInfoFragment {
            val args = Bundle()

            val fragment = GuestInfoFragment()
            fragment.arguments = args
            return fragment
        }
    }
}