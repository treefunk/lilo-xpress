package com.myoptimind.lilo_xpress.guestlogout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.myoptimind.lilo_xpress.R
import com.myoptimind.lilo_xpress.guestlogout.enterpincode.GuestLogoutEnterPinFragment
import com.myoptimind.lilo_xpress.guestlogout.experience.GuestLogoutExperienceFragment
import com.myoptimind.lilo_xpress.guestlogout.print.GuestLogoutPrintFragment
import kotlinx.android.synthetic.main.fragment_guest_logout.*

class GuestLogoutFragment : Fragment() {

    companion object {
        fun newInstance(): GuestLogoutFragment {
            val args = Bundle()

            val fragment = GuestLogoutFragment ()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container_post,GuestLogoutPrintFragment.newInstance())?.commit()
        return inflater.inflate(R.layout.fragment_guest_logout,container,false)
    }
}