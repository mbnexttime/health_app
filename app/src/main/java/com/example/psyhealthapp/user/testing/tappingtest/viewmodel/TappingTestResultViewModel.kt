package com.example.psyhealthapp.user.testing.tappingtest.viewmodel

import androidx.lifecycle.ViewModel
import com.example.psyhealthapp.user.testing.tappingtest.interactor.TappingTestFlowInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class TappingTestResultViewModel @Inject constructor(
    private val interactor: TappingTestFlowInteractor
) : ViewModel() {

    fun notifyGoNextClicked() {
        interactor.notifyResultScreenGoNext()
    }

}