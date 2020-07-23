package com.myoptimind.lilo_xpress.guestlogout.print

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.myoptimind.lilo_xpress.R

class GuestLogoutPrintFragment : Fragment() {
    
    companion object {
        fun newInstance(): GuestLogoutPrintFragment{
            val args = Bundle()
            
            val fragment = GuestLogoutPrintFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_guest_logout_print,container,false)
    }
}