package com.example.psyhealthapp.core

interface OnboardingHandler {
    fun interface OnboardingFinishListener {
        fun onOnboardingFinish()
    }

    fun needShowOnboadring(): Boolean

    fun showOnboarding()

    fun addListener(listener: OnboardingFinishListener)

    fun onOnboardingFinish()
}