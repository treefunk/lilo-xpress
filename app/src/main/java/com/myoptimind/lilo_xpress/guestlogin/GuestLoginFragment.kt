package com.myoptimind.lilo_xpress.guestlogin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.myoptimind.lilo_xpress.R
import com.myoptimind.lilo_xpress.guestlogin.tabs.GuestLoginCitFragment
import com.myoptimind.lilo_xpress.guestlogin.tabs.GuestLoginInfoFragment
import com.myoptimind.lilo_xpress.guestlogin.tabs.GuestLoginPrintFragment
import com.myoptimind.lilo_xpress.guestlogin.tabs.GuestLoginPurposeFragment
import com.myoptimind.lilo_xpress.shared.TabHost

private const val TAG = "GuestLoginFragment"

class GuestLoginFragment : Fragment(R.layout.fragment_guest_login),
    TabHost<GuestLoginTab> {

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
        changeTab(GuestLoginTab.GUEST_INFO)
    }

    override fun changeTab(tab: GuestLoginTab) {

        val fragment = when(tab){
            GuestLoginTab.GUEST_INFO -> GuestLoginInfoFragment.newInstance()
            GuestLoginTab.PURPOSE    -> GuestLoginPurposeFragment.newInstance()
            GuestLoginTab.CIT        -> GuestLoginCitFragment.newInstance()
            GuestLoginTab.PRINT      -> GuestLoginPrintFragment.newInstance()
        }

        fragment.guestTabChanger = this

        activity?.run{
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_container_post, fragment)
                .commit()
        }
    }

}