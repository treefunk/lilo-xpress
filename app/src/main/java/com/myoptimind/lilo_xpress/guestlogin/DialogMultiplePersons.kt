package com.myoptimind.lilo_xpress.guestlogin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import com.myoptimind.lilo_xpress.data.Option
import com.myoptimind.lilo_xpress.data.Result
import timber.log.Timber

class DialogMultiplePersons : DialogMultipleSelectFragment() {

    val viewModel: GuestLoginViewModel by activityViewModels()

    override fun getDialogTitle() = "Select one or more persons to visit"

    override val selectListener: MultipleSelectAdapter.SelectedListener
        get() = object: MultipleSelectAdapter.SelectedListener {
            override fun onSelectedChange(selected: ArrayList<Option>) {
                viewModel.selectedPersons.value = selected
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
    }

    private fun initList() {
        viewModel.persons.observe(viewLifecycleOwner, Observer { personList ->
            when(personList){
                is Result.Success -> {
                    selectAdapter?.selectList = personList.data
                    selectAdapter?.notifyDataSetChanged()
                }
                is Result.Error -> {
                    Timber.e("Oops")
                }
                Result.Loading -> {
                    Toast.makeText(requireContext(),"loading persons..", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}