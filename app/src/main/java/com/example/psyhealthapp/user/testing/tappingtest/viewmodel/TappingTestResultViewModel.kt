package com.example.psyhealthapp.user.testing.tappingtest.viewmodel

import androidx.lifecycle.ViewModel
import com.example.psyhealthapp.user.testing.results.TappingTestResult
import com.example.psyhealthapp.user.testing.tappingtest.interactor.TappingTestFlowInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TappingTestResultViewModel @Inject constructor(
    private val interactor: TappingTestFlowInteractor
) : ViewModel() {

    fun notifyRepeatButtonClicked() {
        interactor.notifyRepeatClicked()
    }

    fun notifyChallengeEnd() {
        interactor.notifyChallengeEnd()
    }

    fun getTappingTestResult(): TappingTestResult {
        return interactor.getTappingTestResult()
    }

}