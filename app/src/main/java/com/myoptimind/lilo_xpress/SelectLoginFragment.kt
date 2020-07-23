package com.myoptimind.lilo_xpress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_select_login.*

private const val TAG = "SelectLoginFragment"

class SelectLoginFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_select_login,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ib_login.setOnClickListener {
            findNavController().navigate(R.id.action_selectLoginFragment_to_guestLoginFragment)
        }
        ib_logout.setOnClickListener {
            findNavController().navigate(R.id.action_selectLoginFragment_to_guestLogoutFragment)
        }
    }
}