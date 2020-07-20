package com.myoptimind.lilo_xpress

import android.os.Bundle
import androidx.fragment.app.Fragment

class SelectUserFragment : Fragment(R.layout.fragment_select_user) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    companion object {
        fun newInstance(): SelectUserFragment {
            return SelectUserFragment()
        }
    }
}