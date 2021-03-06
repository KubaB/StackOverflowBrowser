package com.jakubbrzozowski.stackoverflowbrowser.injection.module

import android.app.Application
import android.content.Context
import com.jakubbrzozowski.stackoverflowbrowser.data.managers.SearchManager
import com.jakubbrzozowski.stackoverflowbrowser.data.managers.SearchManagerImpl
import com.jakubbrzozowski.stackoverflowbrowser.data.remote.ApiService
import com.jakubbrzozowski.stackoverflowbrowser.data.repository.QuestionsRepository
import com.jakubbrzozowski.stackoverflowbrowser.data.repository.QuestionsRepositoryImpl
import com.jakubbrzozowski.stackoverflowbrowser.injection.qualifier.ApplicationContext
import com.jakubbrzozowski.stackoverflowbrowser.injection.qualifier.MainScheduler
import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import javax.inject.Singleton

@Module
class ApplicationModule(val application: Application) {

    @Provides
    @Singleton
    internal fun provideApplication(): Application {
        return application
    }

    @Provides
    @Singleton
    @ApplicationContext
    internal fun provideContext(): Context {
        return application
    }

    @Provides
    @Singleton
    @MainScheduler
    fun provideMainScheduler(): Scheduler {
        return AndroidSchedulers.mainThread()
    }

    @Provides
    @Singleton
    fun provideQuestionRepository(): QuestionsRepository {
        return QuestionsRepositoryImpl()
    }

    @Provides
    @Singleton
    fun provideSearchManager(apiService: ApiService, questionsRepository: QuestionsRepository): SearchManager {
        return SearchManagerImpl(apiService, questionsRepository)
    }
}