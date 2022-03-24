package com.example.psyhealthapp.user.testing.tappingtest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.psyhealthapp.R
import com.example.psyhealthapp.databinding.TappingTestMainFragmentBinding
import com.example.psyhealthapp.user.testing.tappingtest.interactor.TappingTestState
import com.example.psyhealthapp.user.testing.tappingtest.viewmodel.TappingTestMainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TappingTestMainFragment : Fragment() {

    companion object {
        fun newInstance() = TappingTestMainFragment()
    }

    private val viewModel: TappingTestMainViewModel by viewModels()
    private val viewBinding by viewBinding(TappingTestMainFragmentBinding::bind)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.tapping_test_main_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToEvents()
    }

    private fun subscribeToEvents() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentScreenFlow().collect {
                    viewBinding.apply {
                        when (it) {
                            TappingTestState.Challenge ->
                                mainFragmentContainer.findNavController()
                                    .navigate(R.id.tappingTestChallenge)
                            TappingTestState.Instruction ->
                                mainFragmentContainer.findNavController()
                                    .navigate(R.id.tappingTestInstruction)
                            TappingTestState.Result ->
                                mainFragmentContainer.findNavController()
                                    .navigate(R.id.tappingTestResult)
                        }
                    }
                }
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

}