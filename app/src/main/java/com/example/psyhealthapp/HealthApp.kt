package com.example.psyhealthapp

import android.app.Application
import com.example.psyhealthapp.core.TestResultsHolder
import com.example.psyhealthapp.history.HistoryModel
import com.example.psyhealthapp.user.testing.results.TappingTestResult
import dagger.hilt.android.HiltAndroidApp
import java.util.*
import javax.inject.Inject

@HiltAndroidApp
class HealthApp : Application() {
    @Inject
    lateinit var historyModel: HistoryModel

    @Inject
    lateinit var testResultsHolder: TestResultsHolder

    override fun onCreate() {
        super.onCreate()
        historyModel.initialize()
        testResultsHolder.initialize()
    }
}