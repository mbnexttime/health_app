package com.example.psyhealthapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.psyhealthapp.debug.DebugModeHandler
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HealthAppMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (BuildConfig.DEBUG) {
            handleDebugLaunch()
        } else {
            handleNormalLaunch()
        }
    }

    private fun handleNormalLaunch() {
        setContentView(R.layout.activity_main)
    }

    private fun handleDebugLaunch() {
        setContentView(R.layout.debug_activity_main)
        DebugModeHandler(this).onActivityCreate()
    }
}