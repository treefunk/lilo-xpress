package com.myoptimind.lilo_xpress.guestlogin.cit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.myoptimind.lilo_xpress.R

class GuestLoginCitFragment : Fragment(R.layout.fragment_guest_login_cit) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }
    
    companion object {
        fun newInstance(): GuestLoginCitFragment {
            val args = Bundle()
            val fragment = GuestLoginCitFragment()
            fragment.arguments = args
            return fragment
        }
    }
}