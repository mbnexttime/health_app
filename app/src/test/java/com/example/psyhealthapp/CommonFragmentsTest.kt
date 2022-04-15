package com.example.psyhealthapp

import android.widget.LinearLayout
import androidx.core.view.children
import androidx.test.ext.junit.rules.ActivityScenarioRule
import junit.framework.TestCase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CommonFragmentsTest : TestCase() {

    @get:Rule
    val scenarioRule = ActivityScenarioRule(HealthAppMainActivity::class.java)

    @Test
    fun checkThatAllFragmentsAreOk() {
        if (!BuildConfig.DEBUG) {
            return
        }

        scenarioRule.scenario.onActivity { activity ->
            val view = activity.findViewById<LinearLayout>(R.id.fragment_list)
            view.children.forEach {
                it.performClick()
                activity.onBackPressed()
            }
        }
    }
}