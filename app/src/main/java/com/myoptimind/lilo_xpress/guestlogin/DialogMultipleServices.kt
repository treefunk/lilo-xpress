package com.myoptimind.lilo_xpress.guestlogin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.myoptimind.lilo_xpress.R
import com.myoptimind.lilo_xpress.data.Option
import com.myoptimind.lilo_xpress.data.Result
import kotlinx.android.synthetic.main.fragment_dialog_multiple_select.view.*
import timber.log.Timber

class DialogMultipleServices: DialogMultipleSelectFragment() {

    val viewModel: GuestLoginViewModel by activityViewModels()

    override fun getDialogTitle() = "Select one or more services"

    override val selectListener: MultipleSelectAdapter.SelectedListener
        get() = object : MultipleSelectAdapter.SelectedListener {
            override fun onSelectedChange(selected: ArrayList<Option>) {
                Timber.v("selected is changed \n ${selected}")
                viewModel.selectedPurposes.value = selected
            }
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
    }

    private fun initList() {

        viewModel.selectedPurposes.observe(viewLifecycleOwner, Observer { selectedPurposes ->
            selectAdapter?.selected = ArrayList(selectedPurposes)
        })

        viewModel.purposes.observe(viewLifecycleOwner, Observer { options ->
            when(options){
                is Result.Success -> {
                    selectAdapter?.selectList = options.data
                    selectAdapter?.notifyDataSetChanged()
                }
                is Result.Error -> {
                    Timber.e("Oops")
                }
                Result.Loading -> {
                    Toast.makeText(requireContext(),"loading purposes..",Toast.LENGTH_SHORT).show()
                }
            }
        })



    }
}