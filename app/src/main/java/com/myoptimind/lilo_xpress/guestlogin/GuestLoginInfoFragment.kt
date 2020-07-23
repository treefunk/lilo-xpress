package com.myoptimind.lilo_xpress.guestlogin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.myoptimind.lilo_xpress.R
import com.myoptimind.lilo_xpress.api.GuestLoginService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_guest_login_info.*
import javax.inject.Inject

private const val TAG = "GuestLoginFragment"

@AndroidEntryPoint
class GuestLoginInfoFragment : GuestInfoChildFragment() {

    @Inject
    lateinit var guestLoginService: GuestLoginService

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

        val viewModel: GuestLoginViewModel by viewModels()
        viewModel.populateFields()
    }

}