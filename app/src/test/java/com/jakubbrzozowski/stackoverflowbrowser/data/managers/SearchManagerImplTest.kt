package com.jakubbrzozowski.stackoverflowbrowser.data.managers

import com.jakubbrzozowski.stackoverflowbrowser.data.model.remote.Question
import com.jakubbrzozowski.stackoverflowbrowser.data.model.remote.Questions
import com.jakubbrzozowski.stackoverflowbrowser.data.remote.ApiService
import com.jakubbrzozowski.stackoverflowbrowser.data.repository.QuestionsRepository
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SearchManagerImplTest {

    @Mock
    lateinit var apiService: ApiService

    @Mock
    lateinit var questionsRepository: QuestionsRepository

    private lateinit var searchManager: SearchManagerImpl

    private val questionList = listOf(Question(), Question(), Question())

    private val questionsWithMore = Questions(1000, 970, true, questionList)
    private val questionsWithNoMore = Questions(30, 0, false, questionList)

    private val testScheduler = TestScheduler()

    @Before
    fun setup() {
        RxJavaPlugins.setIoSchedulerHandler { testScheduler }
        searchManager = SearchManagerImpl(apiService, questionsRepository)
        doReturn(questionList).`when`(questionsRepository).getAllQuestions()
    }

    @Test
    fun onNewQuestionSearch_apiSuccess_emitCorrectList() {
        doReturn(Single.just(questionsWithMore))
                .`when`(apiService)
                .searchQuestions(anyString(), anyString(), anyInt())
        val testObserver = searchManager.searchResults.test()

        searchManager.newQuestionSearch("query")
        testScheduler.triggerActions()

        testObserver.assertNoErrors()
        testObserver.assertValuesOnly(questionList)
    }

    @Test
    fun onNewQuestionSearch_apiSuccess_deleteAllQuestions() {
        doReturn(Single.just(questionsWithMore))
                .`when`(apiService)
                .searchQuestions(anyString(), anyString(), anyInt())

        searchManager.newQuestionSearch("query")
        testScheduler.triggerActions()

        verify(questionsRepository).deleteAllQuestions()
    }

    @Test
    fun onNewQuestionSearch_apiSuccess_saveNewQuestions() {
        doReturn(Single.just(questionsWithMore))
                .`when`(apiService)
                .searchQuestions(anyString(), anyString(), anyInt())

        searchManager.newQuestionSearch("query")
        testScheduler.triggerActions()

        verify(questionsRepository).saveQuestions(questionList)
    }

    @Test
    fun onNewQuestionSearch_apiError_emitError() {
        val someError = Throwable()
        val testObserver = getResultsObserverForApiError(someError)

        testObserver.assertError(someError)
        testObserver.assertNoValues()
    }

    @Test
    fun onTwoNewQuestionSearches_twoApiErrors_emitTwoErrors() {
        val firstError = Throwable()
        val secondError = Throwable()

        val firstObserver = getResultsObserverForApiError(firstError)
        val secondObserver = getResultsObserverForApiError(secondError)

        firstObserver.assertError(firstError)
        firstObserver.assertNoValues()
        secondObserver.assertError(secondError)
        secondObserver.assertNoValues()
    }

    private fun getResultsObserverForApiError(err: Throwable): TestObserver<List<Question?>> {
        doReturn(Single.error<Throwable>(err))
                .`when`(apiService)
                .searchQuestions(anyString(), anyString(), anyInt())
        val testObserver = searchManager.searchResults.test()

        searchManager.newQuestionSearch("query")
        testScheduler.triggerActions()

        return testObserver
    }

    @Test
    fun onLoadNextPage_questionsHasMore_loadMore() {
        val query = "query"
        doReturn(Single.just(questionsWithMore))
                .`when`(apiService)
                .searchQuestions(anyString(), anyString(), anyInt())

        searchManager.newQuestionSearch(query)
        testScheduler.triggerActions()
        searchManager.loadNextPage(query)

        verify(apiService, times(2)).searchQuestions(anyString(), anyString(), anyInt())
    }

    @Test
    fun onLoadNextPage_questionsHasNoMore_dontLoadMore() {
        val query = "query"
        doReturn(Single.just(questionsWithNoMore))
                .`when`(apiService)
                .searchQuestions(anyString(), anyString(), anyInt())

        searchManager.newQuestionSearch(query)
        testScheduler.triggerActions()
        searchManager.loadNextPage(query)

        verify(apiService).searchQuestions(anyString(), anyString(), anyInt())
    }
}