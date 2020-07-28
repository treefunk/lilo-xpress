package com.myoptimind.lilo_xpress.guestlogin.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.myoptimind.lilo_xpress.R
import com.myoptimind.lilo_xpress.guestlogin.GuestLoginTab
import com.myoptimind.lilo_xpress.guestlogin.GuestLoginViewModel
import com.myoptimind.lilo_xpress.shared.TabChildFragment
import com.myoptimind.lilo_xpress.shared.handleData
import kotlinx.android.synthetic.main.fragment_guest_login_cit.*

class GuestLoginCitFragment : TabChildFragment<GuestLoginTab>() {

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
        return inflater.inflate(R.layout.fragment_guest_login_cit,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel: GuestLoginViewModel by activityViewModels()

        viewModel.temperature.observe(viewLifecycleOwner, Observer { temperature ->
            et_temperature.setText(temperature)
        })

        viewModel.placeOfOrigins.observe(viewLifecycleOwner, Observer { origins ->
            origins.handleData(requireContext(),
                et_place_of_origin,
                onSelectItem = { index -> viewModel.placeOfOriginIndex.value = index.toString() }
            )
        })

        viewModel.placeOfOrigin.observe(viewLifecycleOwner, Observer { placeOfOrigin ->
            et_place_of_origin.setText(placeOfOrigin,false)
        })

        viewModel.mobileNumber.observe(viewLifecycleOwner, Observer { mobileNumber ->
            et_mobile_number.setText(mobileNumber)
        })

        viewModel.healthCondition.observe(viewLifecycleOwner, Observer { healthCondition ->
            et_health_condition.setText(healthCondition)
        })

        iv_cit_back.setOnClickListener {
            guestTabChanger.changeTab(GuestLoginTab.PURPOSE)
            viewModel.saveStep3(
                et_temperature.text.toString(),
                et_mobile_number.text.toString(),
                et_health_condition.text.toString()
            )
        }

        iv_cit_save.setOnClickListener {
            guestTabChanger.changeTab(GuestLoginTab.PRINT)
            viewModel.saveStep3(
                et_temperature.text.toString(),
                et_mobile_number.text.toString(),
                et_health_condition.text.toString()
            )
            viewModel.loginGuest()
        }
    }
    

}