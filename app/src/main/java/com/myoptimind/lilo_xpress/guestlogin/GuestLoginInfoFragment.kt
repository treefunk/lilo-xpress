package com.myoptimind.lilo_xpress.guestlogin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.myoptimind.lilo_xpress.R
import kotlinx.android.synthetic.main.fragment_guest_login_info.*

class GuestLoginInfoFragment : GuestInfoChildFragment() {

    companion object {
        fun newInstance(): GuestLoginInfoFragment {
            val args = Bundle()

            val fragment =
                GuestLoginInfoFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_guest_login_info,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iv_guest_info_next.setOnClickListener {
            guestTabChanger.changeTab(GuestLoginTab.PURPOSE)
        }
    }

}