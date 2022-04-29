package com.example.psyhealthapp.user.testing.tappingtest.interactor

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

sealed class TappingTestState {
    object Instruction : TappingTestState()
    object Challenge : TappingTestState()
    object Result : TappingTestState()
}

@Singleton
class TappingTestFlowInteractor @Inject constructor() {
    fun notifyResultScreenGoNext() {
        tappingTestFlowValue = TappingTestState.Instruction
    }

    fun notifyInstructionScreenGoNext() {
        tappingTestFlowValue = TappingTestState.Challenge
    }

    fun notifyChallengeScreenGoNext() {
        tappingTestFlowValue = TappingTestState.Result
    }

    val tappingTestFlow: Flow<TappingTestState>
        get() = tappingTestFlowInner.asStateFlow()

    private val tappingTestFlowInner =
        MutableStateFlow<TappingTestState>(TappingTestState.Instruction)

    private var tappingTestFlowValue: TappingTestState
        get() = tappingTestFlowInner.value
        set(newValue) {
            tappingTestFlowInner.value = newValue
        }
}