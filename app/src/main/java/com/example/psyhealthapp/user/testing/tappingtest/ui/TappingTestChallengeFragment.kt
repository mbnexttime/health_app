package com.example.psyhealthapp.user.testing.tappingtest.ui

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.psyhealthapp.R
import com.example.psyhealthapp.databinding.TappingTestChallengeFragmentBinding
import com.example.psyhealthapp.user.testing.tappingtest.viewmodel.TappingTestChallengeViewModel
import com.example.psyhealthapp.user.testing.tappingtest.viewmodel.TappingTestChallengeViewModel.ChallengeState.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TappingTestChallengeFragment : Fragment(R.layout.tapping_test_challenge_fragment) {

    companion object {
        fun newInstance() = TappingTestChallengeFragment()
    }

    private val viewModel: TappingTestChallengeViewModel by viewModels()
    private val viewBinding by viewBinding(TappingTestChallengeFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.challengeButton.setOnClickListener {
            viewModel.notifyItemClicked()
        }
        viewBinding.challengeButton.isEnabled = false

        viewBinding.toResultsButton.setOnClickListener {
            viewModel.notifyNextClicked()
            findNavController().navigate(R.id.tappingTestResult)
        }

        subscribeToEvents()
    }

    override fun onResume() {
        super.onResume()
        viewModel.runChallenge()
    }

    override fun onPause() {
        if (!viewModel.isSuccess()) {
            viewModel.reset()
        }
        super.onPause()
    }

    private fun subscribeToEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.challengeStateFlow.collect {
                    when (it) {
                        Ending -> applyEndingState()
                        is WaitingForStart -> applyWaitingForStateState(it.secondsRemaining)
                        is Challenge -> applyChallengeState(it.secondsRemaining)
                    }
                }
            }
        }
    }

    private fun applyChallengeState(secondsRemaining: Int) {
        viewBinding.waitingTimer.isVisible = false
        viewBinding.challengeButton.isVisible = true
        viewBinding.textView.isVisible = true

        viewBinding.challengeButton.isEnabled = true
        viewBinding.textView.text = "$secondsRemaining"
    }

    private fun applyEndingState() {
        viewBinding.apply {
            challengeButton.isVisible = false
            textView.isVisible = false
            toResultsButton.isVisible = true
        }
    }

    private fun applyWaitingForStateState(secondsRemaining: Int) {
        viewBinding.apply {
            challengeButton.isVisible = false
            textView.isVisible = false

            waitingTimer.apply {
                isVisible = true
                text = "$secondsRemaining"
            }
        }
    }
}