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
import com.myoptimind.lilo_xpress.data.PurposeType
import com.myoptimind.lilo_xpress.guestlogin.*
import com.myoptimind.lilo_xpress.shared.TabChildFragment
import com.myoptimind.lilo_xpress.shared.displayAlert
import com.myoptimind.lilo_xpress.shared.displayGenericFormError
import com.myoptimind.lilo_xpress.shared.handleData
import kotlinx.android.synthetic.main.fragment_guest_login_purpose.*

private const val DIALOG_PURPOSE_SERVICES = "dialog_purpose_services"
private const val DIALOG_PERSONS          = "dialog_persons"

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

        et_purpose_service.setOnClickListener {
            showMultipleSelectionDialog(DialogMultipleServices(), DIALOG_PURPOSE_SERVICES)
        }

        viewModel.selectedPurposes.observe(viewLifecycleOwner, Observer { selectedPurposes ->
            if(selectedPurposes != null){
                if(selectedPurposes.isNotEmpty()){
                    et_purpose_service.setText(selectedPurposes.map { it.name }.joinToString(", "))
                }else{
                    et_purpose_service.setText("")
                }
            }
        })


        radiogroup_purposetype.setOnCheckedChangeListener{ group, checkedId ->
            when(checkedId){
                R.id.services_radio -> {
                    viewModel.purposeType.value = PurposeType.SERVICES
                }
                R.id.person_radio -> {
                    viewModel.purposeType.value = PurposeType.PERSON
                }
            }
        }

        viewModel.purposeType.observe(viewLifecycleOwner, Observer { purposeType ->
            when(purposeType){
                PurposeType.SERVICES -> {
                    services_radio.isChecked = true
                    group_purpose_service.visibility = View.VISIBLE
                    group_person_to_visit.visibility = View.GONE
                }
                PurposeType.PERSON -> {
                    person_radio.isChecked = true
                    group_person_to_visit.visibility = View.VISIBLE
                    group_purpose_service.visibility = View.GONE
                }
            }

            viewModel.listenPurposeType()
        })




//        viewModel.purposes.observe(viewLifecycleOwner, Observer { result ->
//            result.handleData(requireContext(),
//                et_purpose_service,
//                onSelectItem = { index -> viewModel.purposeIndex.value = index.toString() }
//            )
//        })

        et_person_to_visit.setOnClickListener {
            showMultipleSelectionDialog(DialogMultiplePersons(), DIALOG_PERSONS)
        }

        viewModel.selectedPersons.observe(viewLifecycleOwner, Observer { selectedPersons ->
            if(selectedPersons != null){
                if(selectedPersons.isNotEmpty()){
                    et_person_to_visit.setText(selectedPersons.map { it.fullname }.joinToString(", "))
                }else{
                    et_person_to_visit.setText("")
                }
            }
        })

//        viewModel.persons.observe(viewLifecycleOwner, Observer { result ->
//            result.handleData(requireContext(),
//                et_person_to_visit,
//                list = { res ->
//                    (res.data as List<Option>).map{ o -> o.fullname!! }
//                },
//                onSelectItem = { index -> viewModel.personToVisitIndex.value = index.toString() }
//            )
//        })

//        viewModel.personToVisit.observe(viewLifecycleOwner, Observer { personToVisit ->
//            et_person_to_visit.setText(personToVisit,false)
//        })

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

    private fun showMultipleSelectionDialog(dialog: DialogMultipleSelectFragment, tag: String) {
        val fm = requireActivity().supportFragmentManager
        val activeDialog = fm.findFragmentByTag(tag)
        if(!(activeDialog != null && activeDialog.isVisible)){
            dialog.show(fm,tag)
        }
    }
}