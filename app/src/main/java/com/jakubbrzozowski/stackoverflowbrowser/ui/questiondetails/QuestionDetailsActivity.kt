package com.jakubbrzozowski.stackoverflowbrowser.ui.questiondetails

import android.os.Bundle
import android.webkit.WebViewClient
import com.jakubbrzozowski.stackoverflowbrowser.R
import com.jakubbrzozowski.stackoverflowbrowser.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_question_details.*
import javax.inject.Inject

class QuestionDetailsActivity : BaseActivity(), QuestionDetailsView {
    companion object {
        const val QUESTION_ID_KEY = "question_id"
    }

    @Inject
    lateinit var presenter: QuestionDetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)
        setContentView(R.layout.activity_question_details)
        presenter.attachView(this)
        handleIntent()
        setupQuestionDetailsWebView()
    }

    private fun handleIntent() {
        val questionId = intent.getIntExtra(QUESTION_ID_KEY, 0)
        presenter.questionIdReceived(questionId)
    }

    private fun setupQuestionDetailsWebView() {
        questionDetails.webViewClient = WebViewClient()
    }

    override fun setWebViewUrl(url: String) {
        questionDetails.loadUrl(url)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

}
