package com.jakubbrzozowski.stackoverflowbrowser.ui.main

import com.jakubbrzozowski.stackoverflowbrowser.data.managers.SearchManager
import com.jakubbrzozowski.stackoverflowbrowser.data.model.remote.Question
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import java.util.*

@RunWith(MockitoJUnitRunner::class)
class MainPresenterTest {

    @Mock
    lateinit var searchManager: SearchManager

    @Mock
    lateinit var view: MainView

    lateinit var presenter: MainPresenter

    @Before
    fun setup() {
        presenter = MainPresenter(searchManager, Schedulers.trampoline())
        doReturn(PublishSubject.create<List<Question?>>())
                .`when`(searchManager)
                .searchResults
        presenter.attachView(view)
    }

    @Test
    fun onAttachView_showQuestionsList() {
        verify(view).showQuestions(anyList())
    }

    @Test
    fun onSearchFieldChanged_oneNonNullOrBlankSearchQuery_startNewSearch() {
        val searchQuery = "testquery"

        presenter.searchFieldChanged(searchQuery)

        verify(searchManager, times(1)).newQuestionSearch(searchQuery)
    }

    @Test
    fun onSearchFieldChanged_twiceTheSameSearchQuery_onlyOneNewSearch() {
        val searchQuery = "testquery"

        presenter.searchFieldChanged(searchQuery)
        presenter.searchFieldChanged(searchQuery)

        verify(searchManager, times(1)).newQuestionSearch(Mockito.anyString())
    }

    @Test
    fun onSearchFieldChanged_twiceTheSameSearchQuery_hideRefreshing() {
        val searchQuery = "testquery"

        presenter.searchFieldChanged(searchQuery)
        presenter.searchFieldChanged(searchQuery)

        verify(view).showRefreshing(false)
    }

    @Test
    fun onSearchFieldChanged_twoDifferentSearchQuery_makeTwoNewSearches() {
        val firstSearchQuery = "testquery1"
        val secondSearchQuery = "testquery2"

        presenter.searchFieldChanged(firstSearchQuery)
        presenter.searchFieldChanged(secondSearchQuery)

        verify(searchManager, times(2)).newQuestionSearch(Mockito.anyString())
    }

    @Test
    fun onSearchFieldChanged_nullSearchQuery_dontSearch() {
        val searchQuery = null

        presenter.searchFieldChanged(searchQuery)

        verify(searchManager, never()).newQuestionSearch(Mockito.anyString())
    }

    @Test
    fun onSearchFieldChanged_emptySearchQuery_dontSearch() {
        val searchQuery = ""

        presenter.searchFieldChanged(searchQuery)

        verify(searchManager, never()).newQuestionSearch(Mockito.anyString())
    }

    @Test
    fun onSearchFieldChanged_blankSearchQuery_dontSearch() {
        val searchQuery = " "

        presenter.searchFieldChanged(searchQuery)

        verify(searchManager, never()).newQuestionSearch(Mockito.anyString())
    }

    @Test
    fun onSearchFieldChanged_nullSearchQuery_showEmptyList() {
        val searchQuery = null

        presenter.searchFieldChanged(searchQuery)

        verify(view, atLeastOnce()).showQuestions(Collections.emptyList())
    }

    @Test
    fun onSearchFieldChanged_emptySearchQuery_showEmptyList() {
        val searchQuery = ""

        presenter.searchFieldChanged(searchQuery)

        verify(view, atLeastOnce()).showQuestions(Collections.emptyList())
    }

    @Test
    fun onSearchFieldChanged_blankSearchQuery_showEmptyList() {
        val searchQuery = " "

        presenter.searchFieldChanged(searchQuery)

        verify(view, atLeastOnce()).showQuestions(Collections.emptyList())
    }

    @Test
    fun onSearchFieldChanged_nullSearchQuery_hideRefreshing() {
        val searchQuery = null

        presenter.searchFieldChanged(searchQuery)

        verify(view).showRefreshing(false)
    }

    @Test
    fun onSearchFieldChanged_emptySearchQuery_hideRefreshing() {
        val searchQuery = ""

        presenter.searchFieldChanged(searchQuery)

        verify(view).showRefreshing(false)
    }

    @Test
    fun onSearchFieldChanged_blankSearchQuery_hideRefreshing() {
        val searchQuery = " "

        presenter.searchFieldChanged(searchQuery)

        verify(view).showRefreshing(false)
    }

    @Test
    fun onRefresh_showRefreshing() {
        presenter.onRefresh()

        verify(view).showRefreshing(true)
    }

    @Test
    fun onRefresh_nonNullOrBlankSearchQuery_startNewSearch() {
        val searchQuery = "testquery"
        doReturn(searchQuery).`when`(view).getQueryString()

        presenter.onRefresh()

        verify(searchManager, times(1)).newQuestionSearch(searchQuery)
    }

    @Test
    fun onQuestionClicked_nullQuestionId_dontOpenQuestionDetails() {
        val questionId = null

        presenter.questionClicked(questionId)

        verify(view, never()).openQuestionDetails(anyInt())
    }

    @Test
    fun onQuestionClicked_nonNullQuestionId_openQuestionDetails() {
        val questionId = 2135

        presenter.questionClicked(questionId)

        verify(view).openQuestionDetails(questionId)
    }

    @Test
    fun onEndOfListReached_blankSearchQuery_dontLoadNextPage() {
        val searchQuery = " "
        doReturn(searchQuery).`when`(view).getQueryString()

        presenter.endOfListReached()

        verify(searchManager, never()).loadNextPage(searchQuery)
    }

    @Test
    fun onEndOfListReached_nonBlankSearchQuery_loadNextPage() {
        val searchQuery = "query"
        doReturn(searchQuery).`when`(view).getQueryString()

        presenter.endOfListReached()

        verify(searchManager).loadNextPage(searchQuery)
    }
}