package com.myoptimind.lilo_xpress.guestlogin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.myoptimind.lilo_xpress.R

abstract class DialogMultipleSelectFragment: BottomSheetDialogFragment() {

    internal var v: View? = null;
    abstract fun getDialogTitle(): String
    abstract val selectListener: MultipleSelectAdapter.SelectedListener
    var selectAdapter : MultipleSelectAdapter? = null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        v = inflater.inflate(R.layout.fragment_dialog_multiple_select,container,false);
        v?.findViewById<TextView>(R.id.tv_select_header)?.setText(getDialogTitle())

        v?.findViewById<Button>(R.id.ib_return)?.setOnClickListener {
            dismiss()
        }

        val rv = v?.findViewById<RecyclerView>(R.id.rv_selections)
//        rv?.layoutManager = StaggeredGridLayoutManager(3, LinearLayoutManager.VERTICAL)
        rv?.layoutManager = GridLayoutManager(requireContext(),3)

        selectAdapter = MultipleSelectAdapter(ArrayList(), selectListener)
        rv?.adapter = selectAdapter
        return v
    }

}