package com.myoptimind.lilo_xpress.guestlogin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.myoptimind.lilo_xpress.R
import kotlinx.android.synthetic.main.fragment_guest_login_cit.*

class GuestLoginCitFragment : GuestInfoChildFragment() {

    companion object {
        fun newInstance(): GuestLoginCitFragment {
            val args = Bundle()
            val fragment =
                GuestLoginCitFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_guest_login_cit,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: GuestLoginViewModel by viewModels()

        iv_cit_back.setOnClickListener {
            guestTabChanger.changeTab(GuestLoginTab.PURPOSE)
        }

        iv_cit_save.setOnClickListener {
            guestTabChanger.changeTab(GuestLoginTab.PRINT)
        }
    }
    

}