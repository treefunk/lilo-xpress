package com.myoptimind.lilo_xpress.guestlogin.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.myoptimind.lilo_xpress.R
import com.myoptimind.lilo_xpress.data.Result
import com.myoptimind.lilo_xpress.guestlogin.GuestLoginTab
import com.myoptimind.lilo_xpress.guestlogin.GuestLoginViewModel
import com.myoptimind.lilo_xpress.shared.TabChildFragment
import kotlinx.android.synthetic.main.fragment_guest_login_print.*

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



        viewModel.loginResult.observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Result.Success -> {
                    val data = result.data.data
                    tv_date_and_time.setText(data.loginTimeFormat)
                    tv_fullname.setText(data.fullname)
                    tv_agency_name.setText("${data.agency}\n${data.attachedAgency}")
                    tv_email_address.setText(data.emailAddress)
                    tv_division_person_visited.setText("${data.divisionToVisit} / ${data.personToVisit}")
                    tv_purpose_of_visit.setText(data.purpose)
                    tv_temperature.setText(data.temperature)
                    tv_place_of_origin.setText(data.placeOfOrigin)
                    tv_pin_code.setText(data.pinCode)
                }
                else -> {
                    // DO NOTHING
                }
            }
        })

        btn_print_login.setOnClickListener {
            findNavController().navigate(R.id.action_guestLoginFragment_to_selectUserFragment)
        }

    }
}