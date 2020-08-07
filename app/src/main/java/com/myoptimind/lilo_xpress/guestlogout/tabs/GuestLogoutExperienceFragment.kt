package com.myoptimind.lilo_xpress.guestlogout.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.myoptimind.lilo_xpress.R
import com.myoptimind.lilo_xpress.data.FeedbackExperience
import com.myoptimind.lilo_xpress.data.Result
import com.myoptimind.lilo_xpress.guestlogout.GuestLogoutTab
import com.myoptimind.lilo_xpress.guestlogout.GuestLogoutViewModel
import com.myoptimind.lilo_xpress.shared.TabChildFragment
import com.myoptimind.lilo_xpress.shared.displayAlert
import com.myoptimind.lilo_xpress.shared.initLoading
import kotlinx.android.synthetic.main.fragment_guest_logout_experience.*

class GuestLogoutExperienceFragment : TabChildFragment<GuestLogoutTab>() {

    val viewModel: GuestLogoutViewModel by activityViewModels()


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
        return inflater.inflate(R.layout.fragment_guest_logout_experience, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loading.initLoading(requireContext())

        iv_good.initRadio()
        iv_okay.initRadio()
        iv_bad.initRadio()

        iv_okay.isChecked = true
        tv_okay_label.isSelected = true
        viewModel.experience.value = FeedbackExperience.GOOD

        iv_experience_back.setOnClickListener {
            guestTabChanger.changeTab(GuestLogoutTab.ENTER_PIN)
        }

        iv_experience_next.setOnClickListener {
            viewModel.save(
                et_feedback.text.toString()
            )
        }

        viewModel.guestLogout.observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Result.Success -> {
                    loading_components_experience.visibility = View.GONE
                    enableInputs(true)
                    requireContext().displayAlert("Success", result.data.meta.message)
                    guestTabChanger.changeTab(GuestLogoutTab.PRINT)

                }
                is Result.Error -> {
                    loading_components_experience.visibility = View.GONE
                    enableInputs(true)
                    requireContext().displayAlert(
                        "",
                        result.error.message ?: "Something went wrong"
                    )
                }
                Result.Loading -> {
                    loading_components_experience.visibility = View.VISIBLE
                    enableInputs(false)
                }
            }
        })


    }

    private fun RadioButton.initRadio() {
        this.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                iv_good.isChecked = !isChecked
                tv_good_label.isSelected = !isChecked

                iv_okay.isChecked = !isChecked
                tv_okay_label.isSelected = !isChecked

                iv_bad.isChecked = !isChecked
                tv_bad_label.isSelected = !isChecked

                this.isChecked = isChecked
                when (this) {
                    iv_good -> {
                        tv_good_label.isSelected = isChecked
                        viewModel.experience.value = FeedbackExperience.GOOD
                    }
                    iv_okay -> {
                        tv_okay_label.isSelected = isChecked
                        viewModel.experience.value = FeedbackExperience.OKAY
                    }
                    iv_bad -> {
                        tv_bad_label.isSelected = isChecked
                        viewModel.experience.value = FeedbackExperience.BAD
                    }
                }

            }

        }

    }

    private fun enableInputs(enabled: Boolean) {
        iv_good.isEnabled = enabled
        iv_okay.isEnabled = enabled
        iv_bad.isEnabled = enabled
        iv_experience_next.isEnabled = enabled
        iv_experience_back.isEnabled = enabled
        et_feedback.isEnabled = enabled
    }
}