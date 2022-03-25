package com.example.psyhealthapp.debug

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import com.example.psyhealthapp.R
import com.example.psyhealthapp.core.LaunchModeHandler
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class DebugModeHandler @Inject constructor(private val activity: AppCompatActivity): LaunchModeHandler {
    override fun onActivityCreate() {
        activity.setContentView(R.layout.debug_activity_main)
        activity.supportFragmentManager.beginTransaction().add(
            R.id.debug_fragment_holder,
            DebugFragment()
        ).commit()
    }
}