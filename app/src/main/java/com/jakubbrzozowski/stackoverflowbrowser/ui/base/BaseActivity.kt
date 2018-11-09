package com.jakubbrzozowski.stackoverflowbrowser.ui.base

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.LongSparseArray
import com.jakubbrzozowski.stackoverflowbrowser.MyApplication
import com.jakubbrzozowski.stackoverflowbrowser.injection.component.ActivityComponent
import com.jakubbrzozowski.stackoverflowbrowser.injection.component.ConfigPersistentComponent
import com.jakubbrzozowski.stackoverflowbrowser.injection.component.DaggerConfigPersistentComponent
import com.jakubbrzozowski.stackoverflowbrowser.injection.module.ActivityModule
import com.jakubbrzozowski.stackoverflowbrowser.utils.getOrPut
import java.util.concurrent.atomic.AtomicLong

@SuppressLint("Registered")
open class BaseActivity : AppCompatActivity(), MvpView {
    companion object {
        @JvmStatic
        private val KEY_ACTIVITY_ID = "KEY_ACTIVITY_ID"
        @JvmStatic
        private val NEXT_ID = AtomicLong(0)
        @JvmStatic
        private val componentsMap = LongSparseArray<ConfigPersistentComponent>()
    }

    private var activityId: Long = 0
    lateinit var activityComponent: ActivityComponent

    @SuppressLint("UsePropertyAccessSyntax")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityId = savedInstanceState?.getLong(KEY_ACTIVITY_ID) ?: NEXT_ID.getAndIncrement()

        val configPersistentComponent = componentsMap.getOrPut(activityId) {
            val component = (applicationContext as MyApplication).applicationComponent

            DaggerConfigPersistentComponent.builder()
                    .applicationComponent(component)
                    .build()
        }

        activityComponent = configPersistentComponent.activityComponent(ActivityModule(this))
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putLong(KEY_ACTIVITY_ID, activityId)
    }

    override fun getContext(): Context {
        return this
    }

    fun startActivityAndFinish(intent: Intent) {
        startActivity(intent)
        finish()
    }
}