package com.example.psyhealthapp.user.testing.tappingtest.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.psyhealthapp.R
import com.example.psyhealthapp.core.TestResultsHolder
import com.example.psyhealthapp.databinding.TappingTestResultFragmentBinding
import com.example.psyhealthapp.user.statistics.tapping.TappingMainFragment
import com.example.psyhealthapp.user.testing.tappingtest.viewmodel.TappingTestResultViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TappingTestResultFragment : Fragment(R.layout.tapping_test_result_fragment) {

    companion object {
        fun newInstance() = TappingTestResultFragment()
    }

    private val viewModel: TappingTestResultViewModel by viewModels()
    private val viewBinding by viewBinding(TappingTestResultFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        childFragmentManager.beginTransaction().apply {
            replace(
                R.id.tappingTestResultFragmentView,
                TappingMainFragment.newInstance(viewModel.getTappingTestResult())
            )
        }.commit()

        viewBinding.endButton.setOnClickListener {
            viewModel.notifyChallengeEnd()
            findNavController().navigate(R.id.action_tappingTestResult_to_tests_list)
        }

        viewBinding.repeatButton.setOnClickListener {
            viewModel.notifyChallengeEnd()
            viewModel.notifyRepeatButtonClicked()
            findNavController().navigate(R.id.tappingTestChallenge)
        }
    }
}