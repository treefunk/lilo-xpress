package com.myoptimind.lilo_xpress.guestlogout.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.myoptimind.lilo_xpress.R
import com.myoptimind.lilo_xpress.data.Result
import com.myoptimind.lilo_xpress.guestlogout.GuestLogoutTab
import com.myoptimind.lilo_xpress.guestlogout.GuestLogoutViewModel
import com.myoptimind.lilo_xpress.shared.TabChildFragment
import kotlinx.android.synthetic.main.fragment_guest_logout_print.*

class GuestLogoutPrintFragment : TabChildFragment<GuestLogoutTab>() {

    val viewModel: GuestLogoutViewModel by activityViewModels()
    
    companion object {
        fun newInstance(): GuestLogoutPrintFragment {
            val args = Bundle()
            
            val fragment =
                GuestLogoutPrintFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_guest_logout_print,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.guestLogout.observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Result.Success -> {
                    val data = result.data.data
                    tv_date_and_time.setText(data.logoutTimeFormat)
                    tv_fullname.setText(data.fullname)
                    tv_agency_name.setText("${data.agency}\n${data.attachedAgency}")
                    tv_email_address.setText(data.emailAddress)
                    tv_division_person_visited.setText("${data.division} / ${data.personVisited}")
                    tv_purpose_of_visit.setText(data.purpose)
                    tv_temperature.setText(data.temperature)
                    tv_place_of_origin.setText(data.placeOfOrigin)
                    tv_duration_of_visit.setText(data.duration)
                }
                else -> {
                    // do nothing
                }
            }
        })

        btn_print_logout.setOnClickListener {
            findNavController().navigate(R.id.action_guestLogoutFragment_to_selectUserFragment)
        }
    }
}