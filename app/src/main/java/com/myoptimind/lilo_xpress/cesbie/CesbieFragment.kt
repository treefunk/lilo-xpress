package com.myoptimind.lilo_xpress.cesbie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.myoptimind.lilo_xpress.R

class CesbieFragment : Fragment() {

    companion object{
        fun newInstance(): CesbieFragment {
            val args = Bundle()

            val fragment = CesbieFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_cesbie,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container_post,CesbieInfoFragment.newInstance())?.commit()
    }
}