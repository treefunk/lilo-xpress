package com.myoptimind.lilo_xpress.guestlogin

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.myoptimind.lilo_xpress.R
import com.myoptimind.lilo_xpress.api.GuestLoginService
import com.myoptimind.lilo_xpress.data.Result
import com.myoptimind.lilo_xpress.setUpDropDown
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_guest_login_info.*
import javax.inject.Inject

private const val TAG = "GuestLoginFragment"

@AndroidEntryPoint
class GuestLoginInfoFragment : GuestInfoChildFragment() {

    @Inject
    lateinit var guestLoginService: GuestLoginService

    val viewModel: GuestLoginViewModel by activityViewModels()

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

//        val agenciesPopupMenu = PopupMenu(requireContext(),label_attached_agency,Gravity.START,android.R.attr.popupMenuStyle,R.style.Widget_AppCompat_Light_PopupMenu_Overflow)


        viewModel.agencies.observe(viewLifecycleOwner, Observer { result ->

            when(result){
                is Result.Success -> {
                    et_agency.setUpDropDown(
                        requireContext(),
                        result.data.map { option -> option.name }
                    ) {
                        Log.v(TAG,"index is $it")
                    }
                }
                is Result.Error -> Toast.makeText(requireContext(),"yeoo",Toast.LENGTH_SHORT).show()
                Result.Loading -> Toast.makeText(requireContext(), "retrieving...",Toast.LENGTH_SHORT).show()
            }
        })
    }

}