package com.example.psyhealthapp.onboarding

import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.psyhealthapp.R
import com.example.psyhealthapp.core.OnboardingHandler
import com.example.psyhealthapp.db.DB
import com.example.psyhealthapp.db.DBProvider
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject
import javax.inject.Provider

@ActivityScoped
class OnboardingHandlerImpl @Inject constructor(
    private val dbProvider: Provider<DBProvider>,
    private val activity: AppCompatActivity,
): OnboardingHandler {
    private val listeners: ArrayList<OnboardingHandler.OnboardingFinishListener> = ArrayList()

    override fun needShowOnboadring(): Boolean {
        return dbProvider.get().getDB(DB_TAG).getBoolean(SHOWN_TAG) != true
    }

    override fun showOnboarding() {
        activity.setContentView(R.layout.onboarding)
    }

    override fun addListener(listener: OnboardingHandler.OnboardingFinishListener) {
        listeners.add(listener)
    }

    override fun onOnboardingFinish() {
        dbProvider.get().getDB(DB_TAG).putBoolean(SHOWN_TAG, true)
        listeners.forEach {
            it.onOnboardingFinish()
        }
    }

    companion object {
        private const val DB_TAG = "onboarding_db"
        private const val SHOWN_TAG = "shown"
    }
}