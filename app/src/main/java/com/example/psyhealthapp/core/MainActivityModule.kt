package com.example.psyhealthapp.core

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.psyhealthapp.BuildConfig
import com.example.psyhealthapp.R
import com.example.psyhealthapp.debug.DebugModeHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Provider

@Module
@InstallIn(ActivityComponent::class)
interface MainActivityModule {
    companion object {
        @Provides
        fun provideAppCompactActivity(activity: Activity): AppCompatActivity {
            return activity as AppCompatActivity
        }

        @Provides
        fun provideLaunchModeHandler(
            debugModeHandler: Provider<DebugModeHandler>,
            normalModeHandler: Provider<NormalModeHandler>,
        ): LaunchModeHandler {
            return if (BuildConfig.DEBUG) {
                debugModeHandler.get()
            } else {
                normalModeHandler.get()
            }
        }

        @Provides
        fun provideNavController(
            activity: AppCompatActivity,
        ): NavController {
            val host = activity.supportFragmentManager.findFragmentById(R.id.main_root) as NavHostFragment
            return host.navController
        }
    }
}