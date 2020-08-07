package com.myoptimind.lilo_xpress.cesbie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.myoptimind.lilo_xpress.R


class CesbieFragment : Fragment() {


    companion object{

        const val TYPE = "type_login"
        const val LOGIN = "cesbie_login"
        const val LOGOUT = "cesbie_logout"

        fun newInstance(type: String): CesbieFragment {
            val args = Bundle()
            args.putString(TYPE,type)
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
            arguments.let {
                if(it?.getString(TYPE).equals(LOGIN)){
                    activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container_post,CesbieLoginFragment.newInstance())?.commit()
                }else{
                    activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.fragment_container_post,CesbieLogoutFragment.newInstance())?.commit()
                }
            }
        return inflater.inflate(R.layout.fragment_cesbie,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}