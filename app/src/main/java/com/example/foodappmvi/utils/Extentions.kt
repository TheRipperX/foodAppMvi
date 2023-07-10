package com.example.foodappmvi.utils

import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner

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