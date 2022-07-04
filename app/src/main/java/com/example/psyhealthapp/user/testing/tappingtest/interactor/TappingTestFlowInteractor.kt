package com.example.psyhealthapp.user.testing.tappingtest.interactor

import com.example.psyhealthapp.core.TestResultsHolder
import com.example.psyhealthapp.user.testing.results.TappingTestResult
import kotlinx.coroutines.flow.MutableStateFlow
import java.time.LocalDateTime
import javax.inject.Inject
import javax.inject.Singleton

sealed class TappingTestState {
    object Instruction : TappingTestState()
    object Challenge : TappingTestState()
    object Result : TappingTestState()
    object End : TappingTestState()
}

@Singleton
class TappingTestFlowInteractor @Inject constructor() {

    @Inject
    lateinit var resultsHolder: TestResultsHolder

    fun notifyInstructionScreenGoNext() {
        tappingTestFlowValue = TappingTestState.Challenge
    }

    fun notifyChallengeScreenGoNext() {
        tappingTestFlowValue = TappingTestState.Result
        val result = getTappingTestResult()
        resultsHolder.putTappingTestResult(result)
    }

    fun notifyClicked(timeMillis: Long) {
        clickTimesInner.add(timeMillis)
    }

    fun notifyChallengeEnd() {
        resultForAllChallenges.add(clickTimesInner.toList())
        clickTimesInner.clear()
        tappingTestFlowValue = TappingTestState.End
    }

    fun notifyRepeatClicked() {
        tappingTestFlowValue = TappingTestState.Challenge
    }

    fun getTappingTestResult(): TappingTestResult {
        val resultsNormalized = resultForAllChallenges.map { lst ->
            lst.map {
                it.toFloat() / 1000
            }
        }
        with(resultsNormalized) {
            return TappingTestResult(LocalDateTime.now(), this[1], this[0])
        }
    }

    fun needMoreChallenges(): Boolean {
        return resultForAllChallenges.size < 2
    }

    fun reset() {
        clickTimesInner.clear()
        resultForAllChallenges.clear()
    }


    private val clickTimesInner = mutableListOf<Long>()

    private val resultForAllChallenges: MutableList<List<Long>> = mutableListOf()

    private val tappingTestFlowInner =
        MutableStateFlow<TappingTestState>(TappingTestState.Instruction)

    private var tappingTestFlowValue: TappingTestState
        get() = tappingTestFlowInner.value
        set(newValue) {
            tappingTestFlowInner.value = newValue
        }
}