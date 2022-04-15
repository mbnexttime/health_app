package com.example.psyhealthapp.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.psyhealthapp.R
import com.example.psyhealthapp.databinding.OnboardingStartViewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnboardingStartFragment: Fragment(R.layout.onboarding_start_view) {
    lateinit var binding: OnboardingStartViewBinding

    lateinit var navController: NavController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = OnboardingStartViewBinding.bind(view)
        navController = findNavController()

        binding.onboardingContinueButton.setOnClickListener {
            navController.navigate(R.id.onboarding_start_to_onboarding_data)
        }
    }
}