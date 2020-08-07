package com.myoptimind.lilo_xpress.guestlogout.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.myoptimind.lilo_xpress.R
import com.myoptimind.lilo_xpress.data.Result
import com.myoptimind.lilo_xpress.guestlogout.GuestLogoutTab
import com.myoptimind.lilo_xpress.guestlogout.GuestLogoutViewModel
import com.myoptimind.lilo_xpress.shared.TabChildFragment
import com.myoptimind.lilo_xpress.shared.displayAlert
import com.myoptimind.lilo_xpress.shared.initLoading
import kotlinx.android.synthetic.main.fragment_guest_logout_enter_pin.*

class GuestLogoutEnterPinFragment : TabChildFragment<GuestLogoutTab>() {

    companion object {
        fun newInstance(): GuestLogoutEnterPinFragment {
            val args = Bundle()
            
            val fragment =
                GuestLogoutEnterPinFragment()
            fragment.arguments = args
            return fragment
        }
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_guest_logout_enter_pin,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewModel: GuestLogoutViewModel by activityViewModels()

        loading.initLoading(requireContext())

        viewModel.resetData()

        viewModel.enterPin.observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Result.Success -> {
                    guestTabChanger.changeTab(GuestLogoutTab.EXPERIENCE)
                    loading_components.visibility = View.INVISIBLE
                    et_pin_code.isEnabled = true
                    iv_enterpin_next.isEnabled = true
                }
                is Result.Error -> {
                    requireContext().displayAlert(
                        "",
                        result.error.message!!
                    )
                    loading_components.visibility = View.INVISIBLE
                    et_pin_code.isEnabled = true
                    iv_enterpin_next.isEnabled = true
                }
                Result.Loading -> {
                    loading_components.visibility = View.VISIBLE
                    et_pin_code.isEnabled = false
                    iv_enterpin_next.isEnabled = false
                }
            }
//            loading_components.visibility = View.INVISIBLE
        })

        iv_enterpin_next.setOnClickListener {
            if(et_pin_code.text.toString().isNotBlank()){
                viewModel.validatePin(et_pin_code.text.toString())
            }else{
                requireContext().displayAlert(
                    "",
                    "Please enter a pin"
                )
            }
        }


    }
    
}