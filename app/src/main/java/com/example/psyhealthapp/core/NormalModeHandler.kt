package com.example.psyhealthapp.core

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.psyhealthapp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import javax.inject.Provider

@ActivityScoped
class NormalModeHandler @Inject constructor(
    private val activity: AppCompatActivity,
    private val navController: Provider<NavController>,
    private val onboardingController: Provider<OnboardingHandler>
) : LaunchModeHandler {
    override fun onActivityCreate() {
        if (onboardingController.get().needShowOnboadring()) {
            onboardingController.get().addListener {
                startNavigation()
            }
            onboardingController.get().showOnboarding()
        } else {
            startNavigation()
        }
    }

    private fun startNavigation() {
        activity.setContentView(R.layout.activity_main)
        activity.findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
            .setupWithNavController(navController.get())
    }
}