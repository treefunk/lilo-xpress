package com.myoptimind.lilo_xpress.guestlogin.purpose

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.myoptimind.lilo_xpress.R

class GuestLoginPurposeFragment : Fragment(R.layout.fragment_guest_login_purpose) {

    companion object {
        fun newInstance(): GuestLoginPurposeFragment {
            val args = Bundle()

            val fragment = GuestLoginPurposeFragment()
            fragment.arguments = args
            return fragment
        }
    }
}