package com.myoptimind.lilo_xpress.cesbie

import android.os.Bundle
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.myoptimind.lilo_xpress.R
import com.myoptimind.lilo_xpress.data.Option
import com.myoptimind.lilo_xpress.data.Result
import com.myoptimind.lilo_xpress.shared.DecimalDigitsInputFilter
import com.myoptimind.lilo_xpress.shared.displayAlert
import com.myoptimind.lilo_xpress.shared.handleData
import com.myoptimind.lilo_xpress.shared.initLoading
import kotlinx.android.synthetic.main.fragment_cesbie_login.*
import kotlinx.android.synthetic.main.fragment_cesbie_login.et_city
import kotlinx.android.synthetic.main.fragment_cesbie_login.et_health_condition
import kotlinx.android.synthetic.main.fragment_cesbie_login.et_region
import kotlinx.android.synthetic.main.fragment_cesbie_login.et_temperature
import kotlinx.android.synthetic.main.fragment_cesbie_login.loading

class CesbieLoginFragment : Fragment () {

    val viewModel: CesbieViewModel by activityViewModels()

    companion object {
        fun newInstance(): CesbieLoginFragment{
            val args = Bundle()
            
            val fragment = CesbieLoginFragment()
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cesbie_login,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loading.initLoading(requireContext())

        viewModel.resetData()

        et_temperature.filters = arrayOf<InputFilter>(DecimalDigitsInputFilter(5,2))


        viewModel.persons.observe(viewLifecycleOwner, Observer { result ->
                    et_full_name.isEnabled = true
                    result.handleData(requireContext(),
                        et_full_name,
                        list = { res ->
                            (res.data as List<Option>).map{ o -> o.fullname!! }
                        },
                        onSelectItem = { index -> viewModel.personIndex.value = index.toString() },
                        onFetchFail = { _ ->
                            if(findNavController().currentDestination?.id == R.id.cesbieFragment){
                                findNavController().navigate(R.id.action_cesbieFragment_to_selectUserFragment)
                            }
                        }
                    )
        })

        viewModel.regions.observe(viewLifecycleOwner, Observer { regions ->
            regions.handleData(requireContext(),
                et_region,
                onSelectItem = { index -> viewModel.regionIndex.value = index.toString() }
            )
        })

        viewModel.region.observe(viewLifecycleOwner, Observer { placeOfOrigin ->
            et_region.setText(placeOfOrigin, false)
        })

        et_city.isEnabled = false
        viewModel.cities.observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Result.Success -> {
                    if(result != null){
                        et_city.isEnabled = true
                        iv_cesbie_info_save.isEnabled = true
                        result.handleData(requireContext(),
                            et_city,
                            onSelectItem = { _ -> viewModel.city.value = et_city.text.toString() }
                        )
                    }
                }
                is Result.Error -> {
                    iv_cesbie_info_save.isEnabled = true
                    Toast.makeText(requireContext(), result.error.message, Toast.LENGTH_SHORT)
                        .show()
                }
                Result.Loading -> {
                    et_city.isEnabled = false
                    iv_cesbie_info_save.isEnabled = false
                }
            }

        })

        viewModel.city.observe(viewLifecycleOwner, Observer { city ->
            et_city.setText(city,false)
        })



        iv_cesbie_info_save.setOnClickListener {
            if(!viewModel.loginCesbie(
                    et_temperature.text.toString(),
                    et_region.text.toString(),
                    et_city.text.toString(),
                    et_health_condition.text.toString()
                )){
                requireContext().displayAlert(
                    "",
                    getString(R.string.message_error_fill_all)
                )
            }
        }

        viewModel.loginResult.observe(viewLifecycleOwner, Observer { result ->
            when(result){
                is Result.Success -> {
                    if(result.data.meta.status.equals("201")){

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
                    loading_components_cesbielogin.visibility = View.GONE
                    iv_cesbie_info_save.isEnabled = true

                }
                is Result.Error -> {
                    requireContext().displayAlert(
                        "",
                        result.error.message!!
                    )
                    loading_components_cesbielogin.visibility = View.GONE
                    iv_cesbie_info_save.isEnabled = true
                }
                Result.Loading -> {
                    loading_components_cesbielogin.visibility = View.VISIBLE
                    iv_cesbie_info_save.isEnabled = false
                }
            }
        })
    }
}