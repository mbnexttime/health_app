package com.example.psyhealthapp.debug

import androidx.appcompat.app.AppCompatActivity
import com.example.psyhealthapp.R

class DebugModeHandler(private val activity: AppCompatActivity) {
    fun onActivityCreate() {
        activity.supportFragmentManager.beginTransaction().add(
            R.id.debug_fragment_holder,
            DebugFragment()
        ).commit()
    }
}