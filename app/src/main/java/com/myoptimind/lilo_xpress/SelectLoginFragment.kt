package com.myoptimind.lilo_xpress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.myoptimind.lilo_xpress.cesbie.CesbieFragment
import kotlinx.android.synthetic.main.fragment_select_login.*

private const val TAG = "SelectLoginFragment"



class SelectLoginFragment : Fragment() {

    companion object {
        const val GUEST_LOGIN  = "guest_login"
        const val CESBIE_LOGIN = "cesbie_login"
    }

    val args: SelectLoginFragmentArgs by navArgs()

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
            if(args.userType == GUEST_LOGIN){
                findNavController().navigate(R.id.action_selectLoginFragment_to_guestLoginFragment)
            }else if(args.userType == CESBIE_LOGIN){
                findNavController().navigate(R.id.action_selectLoginFragment_to_cesbieFragment,
                    Bundle().also { it.putString(CesbieFragment.TYPE,CesbieFragment.LOGIN) }
                )
            }
        }
        ib_logout.setOnClickListener {
            if(args.userType == GUEST_LOGIN){
                findNavController().navigate(R.id.action_selectLoginFragment_to_guestLogoutFragment)
            }else if(args.userType == CESBIE_LOGIN){
                findNavController().navigate(R.id.action_selectLoginFragment_to_cesbieFragment,
                    Bundle().also { it.putString(CesbieFragment.TYPE,CesbieFragment.LOGOUT) }
                )
            }
        }
    }
}