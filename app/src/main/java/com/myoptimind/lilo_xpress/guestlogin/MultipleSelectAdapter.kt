package com.myoptimind.lilo_xpress.guestlogin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myoptimind.lilo_xpress.R
import com.myoptimind.lilo_xpress.data.Option

class MultipleSelectAdapter(var selectList: List<Option>) : RecyclerView.Adapter<MultipleSelectAdapter.SelectViewHolder>() {


    class SelectViewHolder(private val v: View): RecyclerView.ViewHolder(v){

        private var selectButton: Button? = null

        init {
            selectButton = v.findViewById(R.id.btn_select)
        }

        fun bind(option: Option){
            selectButton?.setText(option.name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return SelectViewHolder(inflater.inflate(R.layout.list_item_selection,parent,false))
    }

    override fun getItemCount() = selectList.size

    override fun onBindViewHolder(holder: SelectViewHolder, position: Int) {
        holder.bind(selectList[position])
    }
}