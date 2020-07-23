package com.myoptimind.lilo_xpress.guestlogout.enterpincode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.myoptimind.lilo_xpress.R

class GuestLogoutEnterPinFragment : Fragment(){

    companion object {
        fun newInstance(): GuestLogoutEnterPinFragment {
            val args = Bundle()
            
            val fragment = GuestLogoutEnterPinFragment()
            fragment.arguments = args
            return fragment
        }
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_guest_logout_enter_pin,container,false)
    }
    
}