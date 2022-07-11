package com.example.psyhealthapp

import android.app.Application
import com.example.psyhealthapp.core.ColorHolder
import com.example.psyhealthapp.core.TestResultsHolder
import com.example.psyhealthapp.history.HistoryModel
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class HealthApp : Application() {
    @Inject
    lateinit var colorHolder: ColorHolder

    @Inject
    lateinit var historyModel: HistoryModel

    @Inject
    lateinit var testResultsHolder: TestResultsHolder

    override fun onCreate() {
        super.onCreate()
        colorHolder.initialize()
        historyModel.initialize()
        testResultsHolder.initialize()
    }
}