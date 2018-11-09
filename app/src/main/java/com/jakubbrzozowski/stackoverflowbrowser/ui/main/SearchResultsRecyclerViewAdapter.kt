package com.jakubbrzozowski.stackoverflowbrowser.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakubbrzozowski.stackoverflowbrowser.R

class SearchResultsRecyclerViewAdapter(private val items: List<Any?>) :
        RecyclerView.Adapter<SearchResultsRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_main, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
    }
}