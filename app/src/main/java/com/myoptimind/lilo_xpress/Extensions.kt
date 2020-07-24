package com.myoptimind.lilo_xpress

import android.content.Context
import android.util.Log
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import kotlinx.android.synthetic.main.fragment_guest_login_info.*


fun AutoCompleteTextView.setUpDropDown(context: Context, values: List<String>, onItemClickListener: (index: Int) -> Unit ){
    val adapter: ArrayAdapter<String> = ArrayAdapter(
        context,
        R.layout.support_simple_spinner_dropdown_item,
        values
    )
    this.setAdapter(adapter)
    this.setOnClickListener{
        this.showDropDown()
    }
    this.setOnItemClickListener { _, _, i, _ -> onItemClickListener(i) }
}