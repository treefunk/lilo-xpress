package com.myoptimind.lilo_xpress.guestlogin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.myoptimind.lilo_xpress.R
import kotlinx.android.synthetic.main.fragment_guest_login_purpose.*

class GuestLoginPurposeFragment : GuestInfoChildFragment() {

    companion object {
        fun newInstance(): GuestLoginPurposeFragment {
            val args = Bundle()

            val fragment =
                GuestLoginPurposeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_guest_login_purpose,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iv_guest_purpose_next.setOnClickListener {
            guestTabChanger.changeTab(GuestLoginTab.CIT)
        }

        iv_guest_purpose_back.setOnClickListener {
            guestTabChanger.changeTab(GuestLoginTab.GUEST_INFO)
        }
    }
}