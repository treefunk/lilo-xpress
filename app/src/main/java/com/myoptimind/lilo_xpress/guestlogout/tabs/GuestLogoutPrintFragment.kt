package com.myoptimind.lilo_xpress.guestlogout.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.myoptimind.lilo_xpress.R
import com.myoptimind.lilo_xpress.guestlogout.GuestLogoutTab
import com.myoptimind.lilo_xpress.shared.TabChildFragment

class GuestLogoutPrintFragment : TabChildFragment<GuestLogoutTab>() {
    
    companion object {
        fun newInstance(): GuestLogoutPrintFragment {
            val args = Bundle()
            
            val fragment =
                GuestLogoutPrintFragment()
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