package com.myoptimind.lilo_xpress.guestlogin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.myoptimind.lilo_xpress.R
import com.myoptimind.lilo_xpress.data.Option
import timber.log.Timber

class MultipleSelectAdapter(var selectList: List<Option>, var selectedListener: SelectedListener) : RecyclerView.Adapter<MultipleSelectAdapter.SelectViewHolder>() {

    var selected: ArrayList<Option> = ArrayList()

    init {
        if(selected != null){
//            selectedListener.onSelectedChange(selected)
            for(option in selectList){
                if(selected.contains(option)){
                    option.selected = true
                }
            }
        }
    }

    class SelectViewHolder(private val v: View): RecyclerView.ViewHolder(v){

        private var selectButton: CheckBox? = null
        var selectListener: SelectListener? = null

        init {
            selectButton = v.findViewById(R.id.btn_select)
        }

        fun bind(option: Option, position: Int){

            Timber.v("bind executed")

            selectButton?.let {
                it.isChecked = option.selected
                if (option.name?.isNotBlank() == true) {
                    it.setText(option.name)
                } else {
                    it.setText(option.fullname)
                }
                it.setOnCheckedChangeListener{ _, isChecked ->
                    if(isChecked){
                        selectListener?.onSelectOption(option, position)
                    }else{
                        selectListener?.onUnselectOption(option, position)
                    }
                }
            }

        }

        interface SelectListener {
            fun onSelectOption(option: Option, position: Int)
            fun onUnselectOption(option: Option, position: Int)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SelectViewHolder(inflater.inflate(R.layout.list_item_selection,parent,false))
    }

    override fun getItemCount() = selectList.size

    override fun onBindViewHolder(holder: SelectViewHolder, position: Int) {

        holder.bind(selectList[position],position)

        holder.selectListener = object : SelectViewHolder.SelectListener {
            override fun onSelectOption(option: Option, position: Int){
                selected?.let { selectedOptions ->
                    if(!selected.contains(option)){
                        option.selected = true
                        selectedOptions.add(option)
                    }
                }
                selectedListener.onSelectedChange(selected)
                Timber.v("onselect ${selected.toString()}")


            }
            override fun onUnselectOption(option: Option, position: Int) {
                selected?.let { selectedOptions ->
                    if(selectedOptions.contains(option)){
                        selectedOptions.remove(option)
                    }
                    selectList[position].selected = false
                    selectedListener.onSelectedChange(selectedOptions)
                }
                Timber.v("onremove ${selected.toString()}")
            }
        }

    }

    interface SelectedListener {
        fun onSelectedChange(selected: ArrayList<Option>)
    }
}