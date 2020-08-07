package com.myoptimind.lilo_xpress.guestlogout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.myoptimind.lilo_xpress.R
import com.myoptimind.lilo_xpress.guestlogout.tabs.GuestLogoutEnterPinFragment
import com.myoptimind.lilo_xpress.guestlogout.tabs.GuestLogoutExperienceFragment
import com.myoptimind.lilo_xpress.guestlogout.tabs.GuestLogoutPrintFragment
import com.myoptimind.lilo_xpress.shared.TabHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GuestLogoutFragment : Fragment(), TabHost<GuestLogoutTab> {

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
        changeTab(GuestLogoutTab.ENTER_PIN)
        return inflater.inflate(R.layout.fragment_guest_logout,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun changeTab(tab: GuestLogoutTab, initial: Boolean) {
        val fragment = when(tab){
            GuestLogoutTab.ENTER_PIN -> GuestLogoutEnterPinFragment.newInstance()
            GuestLogoutTab.EXPERIENCE -> GuestLogoutExperienceFragment.newInstance()
            GuestLogoutTab.PRINT -> GuestLogoutPrintFragment.newInstance()
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