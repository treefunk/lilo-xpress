package com.myoptimind.lilo_xpress

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.myoptimind.lilo_xpress.data.UserType
import kotlinx.android.synthetic.main.fragment_select_user.*

class SelectUserFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    companion object {
        fun newInstance(): SelectUserFragment {
            return SelectUserFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_select_user,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        ib_guest.setOnClickListener {
            findNavController().navigate(SelectUserFragmentDirections.actionSelectUserFragmentToSelectLoginFragment(SelectLoginFragment.GUEST_LOGIN))
        }

        ib_cesbie.setOnClickListener {
            findNavController().navigate(SelectUserFragmentDirections.actionSelectUserFragmentToSelectLoginFragment(SelectLoginFragment.CESBIE_LOGIN))

        }

    }

}