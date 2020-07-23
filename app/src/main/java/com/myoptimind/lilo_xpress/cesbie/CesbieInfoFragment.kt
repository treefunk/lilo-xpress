package com.myoptimind.lilo_xpress.cesbie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.myoptimind.lilo_xpress.R

class CesbieInfoFragment : Fragment () {
    companion object {
        fun newInstance(): CesbieInfoFragment{
            val args = Bundle()
            
            val fragment = CesbieInfoFragment()
            fragment.arguments = args
            return fragment
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cesbie_info,container,false)
    }
}