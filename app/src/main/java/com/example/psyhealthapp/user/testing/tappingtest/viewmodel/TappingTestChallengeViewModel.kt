package com.example.psyhealthapp.user.testing.tappingtest.viewmodel

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import com.example.psyhealthapp.user.testing.tappingtest.interactor.TappingTestFlowInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.math.round

@HiltViewModel
class TappingTestChallengeViewModel @Inject constructor(
    private val interactor: TappingTestFlowInteractor
) : ViewModel() {

    fun notifyNextClicked() {
        interactor.notifyChallengeScreenGoNext()
    }

    fun notifyItemClicked() {
        interactor.notifyClicked(System.currentTimeMillis() - challengeStartTime)
    }

    fun runChallenge() {
        startWaitingState()
    }

    fun isSuccess(): Boolean {
        return challengeState == ChallengeState.Ending
    }

    fun reset() {
        interactor.reset()
        timer.cancel()
    }

    private var challengeStartTime: Long = 0

    private lateinit var timer: CountDownTimer

    private fun startWaitingState() {
        timer = object : CountDownTimer(
            TimeUnit.SECONDS.toMillis(3),
            TimeUnit.SECONDS.toMillis(1)
        ) {
            override fun onTick(p0: Long) {
                challengeState = ChallengeState.WaitingForStart(round(p0 / 1000.0).toInt())
            }

            override fun onFinish() {
                startChallengeState()
            }
        }.start()
    }

    private fun startChallengeState() {
        challengeStartTime = System.currentTimeMillis()
        timer = object : CountDownTimer(
            TimeUnit.SECONDS.toMillis(30),
            TimeUnit.SECONDS.toMillis(1)
        ) {
            override fun onTick(p0: Long) {
                challengeState = ChallengeState.Challenge(round(p0 / 1000.0).toInt())
            }

            override fun onFinish() {
                endCurrentChallengeState()
            }
        }.start()
    }

    private fun endCurrentChallengeState() {
        interactor.notifyChallengeEnd()
        if (interactor.needMoreChallenges()) {
            startWaitingState()
        } else {
            challengeState = ChallengeState.Ending
        }
    }


    val challengeStateFlow: Flow<ChallengeState>
        get() = challengeStateFlowInner.asStateFlow()

    private val challengeStateFlowInner =
        MutableStateFlow<ChallengeState>(ChallengeState.WaitingForStart(3))

    private var challengeState: ChallengeState
        get() = challengeStateFlowInner.value
        set(newValue) {
            challengeStateFlowInner.value = newValue
        }

    sealed class ChallengeState {
        class WaitingForStart(val secondsRemaining: Int) : ChallengeState()
        class Challenge(val secondsRemaining: Int) : ChallengeState()
        object Ending : ChallengeState()
    }
}