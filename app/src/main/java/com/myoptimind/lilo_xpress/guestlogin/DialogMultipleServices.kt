package com.myoptimind.lilo_xpress.guestlogin

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.myoptimind.lilo_xpress.R
import com.myoptimind.lilo_xpress.data.Result
import kotlinx.android.synthetic.main.fragment_dialog_multiple_select.view.*
import timber.log.Timber

class DialogMultipleServices: DialogMultipleSelectFragment() {

    val viewModel: GuestLoginViewModel by activityViewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initList()
    }

    private fun initList() {
        val rv = v?.findViewById<RecyclerView>(R.id.rv_selections)

        val selectAdapter = MultipleSelectAdapter(ArrayList())

        if(rv != null){
            rv.layoutManager = StaggeredGridLayoutManager(3,LinearLayoutManager.VERTICAL)
            rv.adapter = selectAdapter
        }


        viewModel.purposes.observe(viewLifecycleOwner, Observer { options ->
            when(options){
                is Result.Success -> {
                    selectAdapter.selectList = options.data
                    selectAdapter.notifyDataSetChanged()
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