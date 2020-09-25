package com.myoptimind.lilo_xpress.guestlogin.tabs

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.myoptimind.lilo_xpress.R
import com.myoptimind.lilo_xpress.data.Result
import com.myoptimind.lilo_xpress.guestlogin.GuestLoginTab
import com.myoptimind.lilo_xpress.guestlogin.GuestLoginViewModel
import com.myoptimind.lilo_xpress.shared.*
import kotlinx.android.synthetic.main.fragment_guest_login_cit.*
import kotlinx.android.synthetic.main.fragment_guest_login_cit.loading
import kotlinx.android.synthetic.main.fragment_guest_login_info.*
import kotlinx.android.synthetic.main.fragment_guest_login_print.*
import timber.log.Timber

class GuestLoginCitFragment : TabChildFragment<GuestLoginTab>() {

    val viewModel: GuestLoginViewModel by activityViewModels()

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
        return inflater.inflate(R.layout.fragment_guest_login_cit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loading.initLoading(requireContext())




        et_temperature.filters = arrayOf<InputFilter>(DecimalDigitsInputFilter(3,2) )

        viewModel.temperature.observe(viewLifecycleOwner, Observer { temperature ->
            et_temperature.setText(temperature)
        })

        viewModel.regions.observe(viewLifecycleOwner, Observer { regions ->
            regions.handleData(requireContext(),
                et_region,
                onSelectItem = { index -> viewModel.regionIndex.value = index.toString() }
            )
        })

        viewModel.region.observe(viewLifecycleOwner, Observer { placeOfOrigin ->
            et_region.setText(placeOfOrigin, false)
        })

        et_city.isEnabled = false
        et_province.isEnabled = false
        val provinceHint = et_province.hint
        viewModel.provincesAndCities.observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Result.Success -> {
                    if(result != null){
                        et_city.isEnabled = true
                        iv_cit_save.isEnabled = true
                        val data = result.data.data
                        val citiesResult = Result.Success(data.cities)

                        citiesResult.handleData(requireContext(),
                            et_city,
                            onSelectItem = { _ -> viewModel.city.value = et_city.text.toString() }
                        )

                        val provincesResult = Result.Success(data.provinces)
                        if(provincesResult.data.isNotEmpty()){
                            et_province.isEnabled = true
                            et_province.hint = "Select Province"
                        }else{
                            et_province.hint = "None"
                        }

                        provincesResult.handleData(requireContext(),
                            et_province,
                            onSelectItem = { _ -> viewModel.province.value = et_province.text.toString() }
                        )
                    }
                }
                is Result.Error -> {
                    iv_cit_save.isEnabled = true
                    Toast.makeText(requireContext(), result.error.message, Toast.LENGTH_SHORT)
                        .show()
                }
                Result.Loading -> {
                    et_city.isEnabled = false
                    et_province.isEnabled = false
                    iv_cit_save.isEnabled = false
                }
            }
        })

        viewModel.city.observe(viewLifecycleOwner, Observer { city ->
            et_city.setText(city,false)
        })

        viewModel.province.observe(viewLifecycleOwner, Observer { province ->
            et_province.setText(province,false)
        })

        viewModel.homeAddress.observe(viewLifecycleOwner, Observer { homeAddress ->
            et_home_address.setText(homeAddress)
        })

/*
        viewModel.mobileNumber.observe(viewLifecycleOwner, Observer { mobileNumber ->
            et_mobile_number.setText(mobileNumber)
        })

        viewModel.healthCondition.observe(viewLifecycleOwner, Observer { healthCondition ->
            et_health_condition.setText(healthCondition)
        })
*/

        val symptoms = ArrayAdapter<String>(requireContext(),android.R.layout.simple_dropdown_item_1line,
            arrayOf(
                "None",
                "Fever",
                "Cough",
                "Shortness of breath",
                "Fatigue",
                "Muscle or body aches",
                "Headache",
                "Loss of taste or smell",
                "Sore throat",
                "Congestion or runny nose",
                "Nausea or vomiting",
                "Diarrhea"
            )
        )

        viewModel.experiencing.observe(viewLifecycleOwner, Observer { experiencing ->
            et_are_you_experiencing.setText(experiencing,false)
        })

        et_are_you_experiencing.apply {
            setAdapter(symptoms)
            setOnClickListener { showDropDown() }
            setOnItemClickListener{ _,_,i,_ -> viewModel.experiencing.value = symptoms.getItem(i) }
        }

        toggle_contact.addOnButtonCheckedListener { _, checkedId, isChecked ->
            when(checkedId){
                R.id.contact_yes -> {
                    if (isChecked) {
                        viewModel.anyContact.value = true
                    }
                }
                R.id.contact_no -> {
                    if (isChecked){
                       viewModel.anyContact.value = false
                    }
                }
            }
        }

        viewModel.anyContact.observe(viewLifecycleOwner, Observer { isChecked ->
            if(isChecked != null){
                if(isChecked){
                    toggle_contact.check(R.id.contact_yes)
                    til_any_contact_details.visibility = View.VISIBLE
                }else{
                    toggle_contact.check(R.id.contact_no)
                    til_any_contact_details.visibility = View.GONE
                }
            }
        })

        viewModel.anyContactDetails.observe(viewLifecycleOwner, Observer { anyContactDetails ->
            et_any_contact_details.setText(anyContactDetails)
        })

        // travel local/international

        toggle_travel.addOnButtonCheckedListener { _, checkedId, isChecked ->
            when(checkedId){
                R.id.travel_yes -> {
                    if (isChecked) {
                        viewModel.haveTravel.value = true
                    }
                }
                R.id.travel_no -> {
                    if (isChecked){
                        viewModel.haveTravel.value = false
                    }
                }
            }
        }

        viewModel.haveTravel.observe(viewLifecycleOwner, Observer { isChecked ->
            if(isChecked != null){
                if(isChecked){
                    toggle_travel.check(R.id.travel_yes)
                    til_travel_details.visibility = View.VISIBLE
                }else{
                    toggle_travel.check(R.id.travel_no)
                    til_travel_details.visibility = View.GONE
                }
            }
        })

        viewModel.haveTravelDetails.observe(viewLifecycleOwner, Observer { haveTravelDetails ->
            et_travel_details.setText(haveTravelDetails)
        })



//        toggle_contact.addOnButtonCheckedListener { group, checkedId, isChecked ->
//            when(checkedId){
//                R.id.contact_yes -> {
//                    til_any_contact_details.visibility = View.VISIBLE
//                }
//                else -> {
//                    til_any_contact_details.visibility = View.GONE
//                }
//            }
//        }


        iv_cit_back.setOnClickListener {
            iv_cit_back.requestFocusFromTouch()
            viewModel.saveStep3(
                et_temperature.text.toString(),
                et_home_address.text.toString(),
                et_region.text.toString(),
                et_city.text.toString(),
                et_any_contact_details.text.toString(),
                et_travel_details.text.toString(),
                cb_declaration.isChecked
            )
            guestTabChanger.changeTab(GuestLoginTab.PURPOSE)
        }

        iv_cit_save.setOnClickListener {
            iv_cit_back.isFocusable = true
            if (viewModel.saveStep3(
                    et_temperature.text.toString(),
                    et_home_address.text.toString(),
                    et_region.text.toString(),
                    et_city.text.toString(),
                    et_any_contact_details.text.toString(),
                    et_travel_details.text.toString(),
                    cb_declaration.isChecked
                )
            ) {
                viewModel.loginGuest()
            } else {
                requireContext().displayGenericFormError()
            }
        }

        viewModel.loginResult.observe(viewLifecycleOwner, Observer { result ->
            when (result) {
                is Result.Success -> {
                    loading_components_cit.visibility = View.GONE
                    enableInputs(true)
                    requireContext().displayAlert("",result.data.meta.message)
                    guestTabChanger.changeTab(GuestLoginTab.PRINT)
                }
                is Result.Error -> {
                    loading_components_cit.visibility = View.GONE
                    enableInputs(true)
                    requireContext().displayAlert(
                        "",
                        result.error.message!!
                    )
                }
                Result.Loading -> {
                    loading_components_cit.visibility = View.VISIBLE
                    enableInputs(false)
                }
            }
        })
    }

    private fun enableInputs(enabled: Boolean) {
        et_temperature.isEnabled = enabled
        et_region.isEnabled = enabled
        et_city.isEnabled = enabled
        et_province.isEnabled = enabled
        et_home_address.isEnabled = enabled
        et_are_you_experiencing.isEnabled = enabled
        et_any_contact_details.isEnabled = enabled
        et_travel_details.isEnabled = enabled
        toggle_contact.isEnabled = enabled
        toggle_travel.isEnabled = enabled
        iv_cit_back.isEnabled = enabled
        iv_cit_save.isEnabled = enabled
    }


}