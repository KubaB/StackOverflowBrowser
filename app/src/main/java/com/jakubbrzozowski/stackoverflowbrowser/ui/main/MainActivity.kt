package com.jakubbrzozowski.stackoverflowbrowser.ui.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import com.jakubbrzozowski.stackoverflowbrowser.R
import com.jakubbrzozowski.stackoverflowbrowser.data.model.remote.Question
import com.jakubbrzozowski.stackoverflowbrowser.ui.base.BaseActivity
import com.jakubbrzozowski.stackoverflowbrowser.ui.questiondetails.QuestionDetailsActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView {
    @Inject
    lateinit var presenter: MainPresenter
    private var recyclerViewAdapter: SearchResultsRecyclerViewAdapter =
            SearchResultsRecyclerViewAdapter(Collections.emptyList(),
                    { presenter.questionClicked(it) },
                    { presenter.endOfListReached() })

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)
        setContentView(R.layout.activity_main)
        presenter.attachView(this)
        setupSearchView()
        setupMainRecycler()
    }

    private fun setupSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                presenter.searchFieldChanged(newText)
                return true
            }
        })
    }

    private fun setupMainRecycler() {
        mainRecycler.layoutManager = LinearLayoutManager(this)
        mainRecycler.adapter = recyclerViewAdapter
        mainSwipeRefresh.setOnRefreshListener { presenter.onRefresh() }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

    override fun showQuestions(questions: List<Question?>) {
        recyclerViewAdapter.items = questions
    }

    override fun openQuestionDetails(questionId: Int) {
        val intent = Intent(this, QuestionDetailsActivity::class.java)
        intent.putExtra(QuestionDetailsActivity.QUESTION_ID_KEY, questionId)
        startActivity(intent)
    }

    override fun getQueryString(): String {
        return searchView.query.toString()
    }

    override fun showRefreshing(show: Boolean) {
        mainSwipeRefresh.isRefreshing = show
    }
}
