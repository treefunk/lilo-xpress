package com.myoptimind.lilo_xpress.guestlogout.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import com.myoptimind.lilo_xpress.R
import com.myoptimind.lilo_xpress.guestlogout.GuestLogoutTab
import com.myoptimind.lilo_xpress.shared.TabChildFragment
import kotlinx.android.synthetic.main.fragment_guest_logout_experience.*

class GuestLogoutExperienceFragment : TabChildFragment<GuestLogoutTab>() {
    
    companion object {
        fun newInstance(): GuestLogoutExperienceFragment {
            val args = Bundle()
            
            val fragment =
                GuestLogoutExperienceFragment()
            fragment.arguments = args
            return fragment
        }
    }
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_guest_logout_experience,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        iv_good.initRadio()
        iv_okay.initRadio()
        iv_bad.initRadio()
    }

    private fun RadioButton.initRadio() {
        this.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                iv_good.isChecked = !isChecked
                tv_good_label.isEnabled = !isChecked

                iv_okay.isChecked = !isChecked
                tv_okay_label.isEnabled = !isChecked

                iv_bad.isChecked  = !isChecked
                tv_bad_label.isEnabled = !isChecked

                this.isChecked = isChecked
                when(this){
                    iv_good -> { tv_good_label.isEnabled = isChecked }
                    iv_okay -> { tv_okay_label.isEnabled = isChecked }
                    iv_bad  -> { tv_bad_label.isEnabled  = isChecked }
                }

            }

        }

    }
}