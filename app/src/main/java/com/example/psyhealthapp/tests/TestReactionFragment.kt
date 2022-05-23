package com.example.psyhealthapp.tests

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.psyhealthapp.R
//import com.example.psyhealthapp.core.TestResultsHolder
import com.example.psyhealthapp.databinding.TestReactionFragmentBinding
//import com.example.psyhealthapp.user.testing.results.ReactionTestResult
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import javax.inject.Inject
import kotlin.random.Random.Default.nextDouble

@AndroidEntryPoint
class TestReactionFragment : Fragment(R.layout.test_reaction_fragment) {
    //@Inject
    //lateinit var resultsHolder: TestResultsHolder

    private val viewBinding by viewBinding(TestReactionFragmentBinding::bind)

    private var _buttonState = ButtonState.Start

    private lateinit var timer: CountDownTimer
    private var reactionTime: Long = 0
    private val attemptsCount = 5
    private val minTime = 2.0
    private val maxTime = 4.0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var counter = 0
        var meanReactionTime: Long = 0
        viewBinding.reactionButton.setOnClickListener {
            when (_buttonState) {
                ButtonState.Start -> {
                    viewBinding.reactionButton.setBackgroundColor(resources.getColor(R.color.red))
                    viewBinding.reactionButton.text = resources.getString(R.string.test_reaction_wait_green_button)
                    val time = (nextDouble(minTime, maxTime) * 1000).toLong()
                    timer = timer(time, 1000).start()
                    _buttonState = ButtonState.Wait
                }
                ButtonState.Wait -> {
                    viewBinding.reactionButton.setBackgroundColor(resources.getColor(R.color.purple_700))
                    viewBinding.reactionButton.text = resources.getString(R.string.test_reaction_soon_tap)
                    _buttonState = ButtonState.Start
                    timer.cancel()
                }
                ButtonState.Click -> {
                    viewBinding.reactionButton.setBackgroundColor(resources.getColor(R.color.purple_700))
                    reactionTime = System.currentTimeMillis() - reactionTime

                    viewBinding.reactionButton.text = "Ваш результат $reactionTime мс. Нажмите чтобы попробовтаь снова."
                    _buttonState = ButtonState.Start
                    counter++
                    meanReactionTime += reactionTime
                    if (counter == attemptsCount) {
                        meanReactionTime /= counter
                        viewBinding.reactionButton.setBackgroundColor(resources.getColor(R.color.purple_700))
                        viewBinding.reactionButton.text =
                            "Ваш итоговый результат $meanReactionTime мс. Нажмите для продолжения"
                        _buttonState = ButtonState.End
                    }
                }
                ButtonState.End -> {
                    // TODO: реализовать переход, сохранение данных
                    //val result = ReactionTestResult(LocalDate.now(), meanReactionTime.toFloat() / 1000)
                    //resultsHolder.putReactionTestResult(result)
                    val controller = findNavController()
                    controller.navigate(R.id.testReactionInstruction)
                }

            }
        }
    }

    override fun onPause() {
        super.onPause()
        if(_buttonState == ButtonState.Wait) {
            viewBinding.reactionButton.setBackgroundColor(resources.getColor(R.color.purple_700))
            viewBinding.reactionButton.text = resources.getString(R.string.test_reaction_smth_wrong)
            _buttonState = ButtonState.Start
            timer.cancel()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if(_buttonState == ButtonState.Wait) {
            timer.cancel()
        }
    }

    enum class ButtonState {
        Start,
        Wait,
        Click,
        End
    }

    private fun timer(millisRunning : Long, countDownInterval : Long) : CountDownTimer {
        return object: CountDownTimer(millisRunning, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                viewBinding.reactionButton.setBackgroundColor(resources.getColor(R.color.brand_green))
                viewBinding.reactionButton.text = resources.getString(R.string.test_reaction_tap_green_button)
                _buttonState = ButtonState.Click
                reactionTime = System.currentTimeMillis()
            }
        }
    }
}