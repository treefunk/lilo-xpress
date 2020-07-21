package com.myoptimind.lilo_xpress.guestlogin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.myoptimind.lilo_xpress.R
import com.myoptimind.lilo_xpress.guestlogin.guestinfo.GuestInfoFragment
import kotlinx.android.synthetic.main.fragment_cesbie.*

class GuestLoginFragment : Fragment(R.layout.fragment_guest_login) {

    companion object {
        fun newInstance(): GuestLoginFragment {
            val args = Bundle()

            val fragment = GuestLoginFragment ()
            fragment.arguments = args
            return fragment
        }
    }
    
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.supportFragmentManager?.beginTransaction()
            ?.replace(R.id.fragment_container_post,GuestInfoFragment.newInstance())?.commit()
    }
}