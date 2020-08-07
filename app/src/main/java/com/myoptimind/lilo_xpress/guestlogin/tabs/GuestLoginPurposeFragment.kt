package com.myoptimind.lilo_xpress.guestlogin.tabs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.myoptimind.lilo_xpress.R
import com.myoptimind.lilo_xpress.data.Option
import com.myoptimind.lilo_xpress.guestlogin.GuestLoginTab
import com.myoptimind.lilo_xpress.guestlogin.GuestLoginViewModel
import com.myoptimind.lilo_xpress.shared.TabChildFragment
import com.myoptimind.lilo_xpress.shared.displayAlert
import com.myoptimind.lilo_xpress.shared.displayGenericFormError
import com.myoptimind.lilo_xpress.shared.handleData
import kotlinx.android.synthetic.main.fragment_guest_login_purpose.*

class GuestLoginPurposeFragment : TabChildFragment<GuestLoginTab>() {

    val viewModel: GuestLoginViewModel by activityViewModels()

    companion object {
        fun newInstance(): GuestLoginPurposeFragment {
            val args = Bundle()

            val fragment =
                GuestLoginPurposeFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_guest_login_purpose,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.divisions.observe(viewLifecycleOwner, Observer { result ->
            result.handleData(requireContext(),
                et_division_to_visit,
                onSelectItem = { index -> viewModel.divisionToVisitIndex.value = index.toString() }
            )
        })

        viewModel.divisionToVisit.observe(viewLifecycleOwner, Observer { division ->
            et_division_to_visit.setText(division,false)
        })


        viewModel.purposes.observe(viewLifecycleOwner, Observer { result ->
            result.handleData(requireContext(),
                et_purpose_service,
                onSelectItem = { index -> viewModel.purposeIndex.value = index.toString() }
            )
        })

        viewModel.purpose.observe(viewLifecycleOwner, Observer { purpose ->
            et_purpose_service.setText(purpose,false)
        })

        viewModel.persons.observe(viewLifecycleOwner, Observer { result ->
            result.handleData(requireContext(),
                et_person_to_visit,
                list = { res ->
                    (res.data as List<Option>).map{ o -> o.fullname!! }
                },
                onSelectItem = { index -> viewModel.personToVisitIndex.value = index.toString() }
            )
        })

        viewModel.personToVisit.observe(viewLifecycleOwner, Observer { personToVisit ->
            et_person_to_visit.setText(personToVisit,false)
        })

        iv_guest_purpose_next.setOnClickListener {
            if(viewModel.saveStep2()){
                guestTabChanger.changeTab(GuestLoginTab.CIT)
            }else{
                requireContext().displayGenericFormError()
            }
        }

        iv_guest_purpose_back.setOnClickListener {
            guestTabChanger.changeTab(GuestLoginTab.GUEST_INFO)
            viewModel.saveStep2()
        }
    }
}