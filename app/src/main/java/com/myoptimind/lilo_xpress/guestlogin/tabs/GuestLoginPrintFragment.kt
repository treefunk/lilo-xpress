package com.myoptimind.lilo_xpress.guestlogin.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.myoptimind.lilo_xpress.R
import com.myoptimind.lilo_xpress.guestlogin.GuestLoginTab
import com.myoptimind.lilo_xpress.guestlogin.GuestLoginViewModel
import com.myoptimind.lilo_xpress.shared.TabChildFragment

class GuestLoginPrintFragment : TabChildFragment<GuestLoginTab>() {

    val viewModel: GuestLoginViewModel by activityViewModels()

    companion object {
        fun newInstance(): GuestLoginPrintFragment {
            val args = Bundle()
            
            val fragment =
                GuestLoginPrintFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_guest_login_print,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}