package com.jakubbrzozowski.stackoverflowbrowser.ui.main

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakubbrzozowski.stackoverflowbrowser.R
import com.jakubbrzozowski.stackoverflowbrowser.data.model.Question
import kotlinx.android.synthetic.main.item_question.view.*

class SearchResultsRecyclerViewAdapter(items: List<Question?>) :
        RecyclerView.Adapter<SearchResultsRecyclerViewAdapter.ViewHolder>() {

    var items: List<Question?> = items
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_question, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.count()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        items[position]?.let { holder.bind(it) }
    }

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bind(question: Question) {
            view.question_title.text = question.title
        }
    }
}