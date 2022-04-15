package com.example.psyhealthapp.onboarding

import com.example.psyhealthapp.core.OnboardingHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
interface OnboardingModule {
    @Binds
    fun bindOnboardingHandler(impl: OnboardingHandlerImpl): OnboardingHandler
}