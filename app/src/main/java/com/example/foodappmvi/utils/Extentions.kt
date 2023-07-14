package com.example.foodappmvi.utils

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun Spinner.setItemSpinner(items: MutableList<*>, str: (String) -> Unit) {

    val adapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, items)
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    this.adapter = adapter
    this.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
        override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
            str(items[p2].toString())
        }
        override fun onNothingSelected(p0: AdapterView<*>?) {}
    }

}

fun View.isVisibleView(show: Boolean, view: View) {
    if (show) {
        this.isVisible = true
        view.isVisible = false
    }else {
        this.isVisible = false
        view.isVisible = true
    }
}

fun RecyclerView.isSetAction(adapter: RecyclerView.Adapter<*>, layout: RecyclerView.LayoutManager) {
    this.adapter = adapter
    this.layoutManager = layout
}


class DifferAdapterEx(private val oldList: List<*>, private val newList: List<*>): DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldList.size
    override fun getNewListSize(): Int = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}