package com.example.psyhealthapp

import android.app.Application
import com.example.psyhealthapp.history.HistoryModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class HealthApp : Application() {
    @Inject
    lateinit var historyModel: HistoryModel

    override fun onCreate() {
        super.onCreate()
        historyModel.initialize()
    }
}