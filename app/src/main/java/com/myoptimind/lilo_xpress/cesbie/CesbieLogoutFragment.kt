package com.myoptimind.lilo_xpress.cesbie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.myoptimind.lilo_xpress.R
import com.myoptimind.lilo_xpress.data.Option
import com.myoptimind.lilo_xpress.data.Result
import com.myoptimind.lilo_xpress.shared.displayAlert
import com.myoptimind.lilo_xpress.shared.handleData
import com.myoptimind.lilo_xpress.shared.initLoading
import kotlinx.android.synthetic.main.fragment_cesbie_logout.*
import kotlinx.android.synthetic.main.fragment_cesbie_logout.et_full_name
import kotlinx.android.synthetic.main.fragment_cesbie_logout.loading

class CesbieLogoutFragment: Fragment() {

    val viewModel: CesbieViewModel by activityViewModels()

    companion object {
        fun newInstance(): CesbieLogoutFragment {
            val args = Bundle()

            val fragment = CesbieLogoutFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cesbie_logout,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loading.initLoading(requireContext())

        viewModel.resetData()

        viewModel.persons.observe(viewLifecycleOwner, Observer { result ->
            result.handleData(requireContext(),
                et_full_name,
                list = { res ->
                    (res.data as List<Option>).map{ o -> o.fullname!! }
                },
                onSelectItem = { index -> viewModel.personIndex.value = index.toString() }
            )
        })

        iv_logout_cesbie_save.setOnClickListener {
            if(!viewModel.logoutCesbie()){
                requireContext().displayAlert(
                    "",
                    "Please select a user to proceed."
                )
            }
        }

        viewModel.logoutResult.observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Result.Success -> {
                    if(result.data.meta.status.equals("200")){
                        requireContext().displayAlert(
                            "Success",
                            result.data.meta.message
                        )
                        findNavController().navigate(R.id.action_cesbieFragment_to_selectUserFragment)
                    }else{
                        requireContext().displayAlert(
                            "",
                            result.data.meta.message
                        )
                    }
                    loading_components.visibility = View.GONE
                    iv_logout_cesbie_save.isEnabled = true

                }
                is Result.Error -> {
                    requireContext().displayAlert(
                        "",
                        result.error.message!!
                    )
                    loading_components.visibility = View.GONE
                    iv_logout_cesbie_save.isEnabled = true
                }
                Result.Loading -> {
                    loading_components.visibility = View.VISIBLE
                    iv_logout_cesbie_save.isEnabled = false
                }
            }
        })

    }
}