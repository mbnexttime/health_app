package com.example.psyhealthapp.user.testing.tappingtest.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.psyhealthapp.R
import com.example.psyhealthapp.databinding.TappingTestInstructionFragmentBinding
import com.example.psyhealthapp.user.testing.tappingtest.viewmodel.TappingTestInstructionViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TappingTestInstructionFragment : Fragment(R.layout.tapping_test_instruction_fragment) {

    companion object {
        fun newInstance() = TappingTestInstructionFragment()
    }

    private val viewModel: TappingTestInstructionViewModel by viewModels()
    private val viewBinding by viewBinding(TappingTestInstructionFragmentBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.challengeButton.setOnClickListener {
            viewModel.notifyNextClicked()
        }
    }

}