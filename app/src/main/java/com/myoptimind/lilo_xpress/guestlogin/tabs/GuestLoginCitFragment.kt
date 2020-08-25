package com.myoptimind.lilo_xpress.guestlogin.tabs

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.myoptimind.lilo_xpress.R
import com.myoptimind.lilo_xpress.data.Result
import com.myoptimind.lilo_xpress.guestlogin.GuestLoginTab
import com.myoptimind.lilo_xpress.guestlogin.GuestLoginViewModel
import com.myoptimind.lilo_xpress.shared.*
import kotlinx.android.synthetic.main.fragment_guest_login_cit.*
import kotlinx.android.synthetic.main.fragment_guest_login_info.*

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
        viewModel.cities.observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Result.Success -> {
                    if(result != null){
                        et_city.isEnabled = true
                        result.handleData(requireContext(),
                            et_city,
                            onSelectItem = { _ -> viewModel.city.value = et_city.text.toString() }
                        )
                    }
                }
                is Result.Error -> {
                    Toast.makeText(requireContext(), result.error.message, Toast.LENGTH_SHORT)
                        .show()
                }
                Result.Loading -> {
                    et_city.isEnabled = false
                }
            }

        })

        viewModel.city.observe(viewLifecycleOwner, Observer { city ->
            et_city.setText(city,false)
        })

        viewModel.mobileNumber.observe(viewLifecycleOwner, Observer { mobileNumber ->
            et_mobile_number.setText(mobileNumber)
        })

        viewModel.healthCondition.observe(viewLifecycleOwner, Observer { healthCondition ->
            et_health_condition.setText(healthCondition)
        })

        iv_cit_back.setOnClickListener {
            viewModel.saveStep3(
                et_temperature.text.toString(),
                et_mobile_number.text.toString(),
                et_region.text.toString(),
                et_city.text.toString(),
                et_health_condition.text.toString()
            )
            guestTabChanger.changeTab(GuestLoginTab.PURPOSE)
        }

        iv_cit_save.setOnClickListener {
            if (viewModel.saveStep3(
                    et_temperature.text.toString(),
                    et_mobile_number.text.toString(),
                    et_region.text.toString(),
                    et_city.text.toString(),
                    et_health_condition.text.toString()
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
        et_mobile_number.isEnabled = enabled
        et_health_condition.isEnabled = enabled
        iv_cit_back.isEnabled = enabled
        iv_cit_save.isEnabled = enabled
    }


}