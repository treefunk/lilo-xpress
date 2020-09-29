package com.myoptimind.lilo_xpress.guestlogin.tabs

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.myoptimind.lilo_xpress.R
import com.myoptimind.lilo_xpress.data.PurposeType
import com.myoptimind.lilo_xpress.data.Result
import com.myoptimind.lilo_xpress.guestlogin.GuestLoginTab
import com.myoptimind.lilo_xpress.guestlogin.GuestLoginViewModel
import com.myoptimind.lilo_xpress.guestlogin.api.GuestLoginResponse
import com.myoptimind.lilo_xpress.shared.LiloPrinter
import com.myoptimind.lilo_xpress.shared.TabChildFragment
import com.myoptimind.lilo_xpress.shared.initLoading
import dagger.hilt.android.AndroidEntryPoint

import kotlinx.android.synthetic.main.fragment_guest_login_print.*
import kotlinx.android.synthetic.main.fragment_guest_login_print.label_divison_person_visited
import kotlinx.android.synthetic.main.fragment_guest_login_print.label_email_address
import kotlinx.android.synthetic.main.fragment_guest_login_print.label_purpose_of_visit
import kotlinx.android.synthetic.main.fragment_guest_login_print.loading
import kotlinx.android.synthetic.main.fragment_guest_login_print.tv_agency_name
import kotlinx.android.synthetic.main.fragment_guest_login_print.tv_date_and_time
import kotlinx.android.synthetic.main.fragment_guest_login_print.tv_division_person_visited
import kotlinx.android.synthetic.main.fragment_guest_login_print.tv_email_address
import kotlinx.android.synthetic.main.fragment_guest_login_print.tv_fullname
import kotlinx.android.synthetic.main.fragment_guest_login_print.tv_place_of_origin
import kotlinx.android.synthetic.main.fragment_guest_login_print.tv_purpose_of_visit
import kotlinx.android.synthetic.main.fragment_guest_login_print.tv_temperature
import kotlinx.android.synthetic.main.fragment_guest_logout_print.*
import javax.inject.Inject

@AndroidEntryPoint
class GuestLoginPrintFragment : TabChildFragment<GuestLoginTab>() {

    val viewModel: GuestLoginViewModel by activityViewModels()

    @Inject
    lateinit var liloPrinter: LiloPrinter;

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
        loading.initLoading(requireContext())



        viewModel.loginResult.observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Result.Success -> {



                    val data = result.data.data
                    val purposeType = if(data.divisionToVisit.isNullOrBlank()) PurposeType.PERSON else PurposeType.SERVICES

                    if(data.emailAddress.isEmpty()){
                        tv_email_address.visibility = View.GONE
                        label_email_address.visibility = View.GONE
                    }


                    tv_date_and_time.setText(data.loginTimeFormat)
                    tv_fullname.setText(data.fullname)
                    tv_agency_name.setText("${data.agency}\n${data.attachedAgency}")
                    tv_email_address?.setText(data.emailAddress)

                    if(purposeType == PurposeType.SERVICES){
                        label_divison_person_visited.setText("Division")
                        tv_division_person_visited.setText(data.divisionToVisit)
                        tv_purpose_of_visit.setText(data.purpose)
                    }else{
                        label_divison_person_visited.setText("Person Visited")
                        tv_division_person_visited.setText(data.personToVisit)
                        tv_purpose_of_visit.visibility          = View.GONE
                        label_purpose_of_visit.visibility       = View.GONE
                    }
//                    tv_division_person_visited.setText("${data.divisionToVisit} / ${data.personToVisit}")
                    tv_temperature.setText(data.temperature)
                    val sb = StringBuilder()
                    sb.append(data.region)
                    if(data.province.isNotEmpty()){
                        sb.append(", ${data.province}")
                    }
                    sb.append("\n${data.city}")
                    tv_place_of_origin.setText(sb.toString())
                    tv_pin_code.setText(data.pinCode)
                    btn_print_login.setOnClickListener {
                       viewModel.printData(result.data)
                    }
                }
                else -> {
                    // DO NOTHING
                }
            }
        })

        viewModel.printResult.observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Result.Success -> {
                    findNavController().navigate(R.id.action_guestLoginFragment_to_selectUserFragment)
                    btn_print_login.isEnabled = true
                    loading_components_print.visibility = View.GONE
                }
                is Result.Error -> {
                    AlertDialog.Builder(requireContext())
                        .setTitle("Print")
                        .setMessage(result.error.message)
                        .setPositiveButton("Cancel", DialogInterface.OnClickListener{ dialog, _ ->
                            dialog.dismiss()
                        })
                        .setNegativeButton("Continue Without Receipt", DialogInterface.OnClickListener{ dialog, _ ->
                            dialog.dismiss()
                            findNavController().navigate(R.id.action_guestLoginFragment_to_selectUserFragment)
                        }).create().show()
                    btn_print_login.isEnabled = true
                    loading_components_print.visibility = View.GONE
                }
                Result.Loading -> {
                    btn_print_login.isEnabled = false
                    loading_components_print.visibility = View.VISIBLE
                }
            }
        })

    }

}