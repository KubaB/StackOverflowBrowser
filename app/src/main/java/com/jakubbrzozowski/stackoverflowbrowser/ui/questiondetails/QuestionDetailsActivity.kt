package com.jakubbrzozowski.stackoverflowbrowser.ui.questiondetails

import android.os.Bundle
import android.webkit.WebViewClient
import com.jakubbrzozowski.stackoverflowbrowser.R
import com.jakubbrzozowski.stackoverflowbrowser.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_question_details.*
import javax.inject.Inject

class QuestionDetailsActivity : BaseActivity(), QuestionDetailsView {
    @Inject
    lateinit var presenter: QuestionDetailsPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityComponent.inject(this)
        setContentView(R.layout.activity_question_details)

        questionDetails.webViewClient = WebViewClient()
        questionDetails.loadUrl("http://stackoverflow.com/questions/53210375")
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.detachView()
    }

}
