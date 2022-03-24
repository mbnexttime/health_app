package com.example.psyhealthapp.user.testing.tappingtest.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.psyhealthapp.R
import com.example.psyhealthapp.databinding.TappingTestChallengeFragmentBinding
import com.example.psyhealthapp.user.testing.tappingtest.viewmodel.TappingTestChallengeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TappingTestChallengeFragment : Fragment(R.layout.tapping_test_challenge_fragment) {

    companion object {
        fun newInstance() = TappingTestChallengeFragment()
    }

    private val viewModel: TappingTestChallengeViewModel by viewModels()
    private val viewBinding by viewBinding(TappingTestChallengeFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.button.setOnClickListener {
            viewModel.notifyNextClicked()
        }
    }

}