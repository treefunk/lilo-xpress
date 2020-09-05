package com.myoptimind.lilo_xpress.guestlogin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.myoptimind.lilo_xpress.R

open class DialogMultipleSelectFragment: BottomSheetDialogFragment() {

    internal var v: View? = null;

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_dialog_multiple_select,container,false);
        return v
    }

}