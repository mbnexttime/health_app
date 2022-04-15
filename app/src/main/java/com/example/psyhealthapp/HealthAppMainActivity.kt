package com.example.psyhealthapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.psyhealthapp.core.LaunchModeHandler
import com.example.psyhealthapp.debug.DebugModeHandler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Provider

@AndroidEntryPoint
class HealthAppMainActivity : AppCompatActivity() {

    @Inject lateinit var launchModeHandler: LaunchModeHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        launchModeHandler.onActivityCreate()
    }
}