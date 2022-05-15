package com.example.psyhealthapp.tests

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.psyhealthapp.R
import com.example.psyhealthapp.databinding.ComplexTestReactionFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random.Default.nextDouble

@AndroidEntryPoint
class ComplexTestReactionFragment : Fragment(R.layout.complex_test_reaction_fragment) {
    private val viewBinding by viewBinding(ComplexTestReactionFragmentBinding::bind)

    private var _buttonState = ButtonState.Start

    private lateinit var timer: CountDownTimer
    private var reactionTime: Long = 0
    private var color: Double = 0.0
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
                    viewBinding.reactionButton.text = resources.getString(R.string.complex_test_reaction_wait_for_choice)
                    val time = (nextDouble(minTime, maxTime) * 1000).toLong()
                    timer = timer(time, 1000).start()
                    _buttonState = ButtonState.Wait
                }
                ButtonState.Wait -> {
                    viewBinding.reactionButton.text = resources.getString(R.string.complex_test_reaction_soon_tap)
                    _buttonState = ButtonState.Start
                    timer.cancel()
                }
                ButtonState.End -> {
                    // TODO: реализовать переход, сохранение данных
                }
                else -> {}
            }
        }
        viewBinding.reactionButtonGreen.setOnClickListener {
            viewBinding.colour.text = ""
            when (_buttonState) {
                ButtonState.ClickGreen -> {
                    viewBinding.reactionButton.visibility = View.VISIBLE
                    viewBinding.reactionButtonGreen.visibility = View.INVISIBLE
                    viewBinding.reactionButtonRed.visibility = View.INVISIBLE
                    reactionTime = System.currentTimeMillis() - reactionTime
                    viewBinding.reactionButton.text =
                        "Ваш результат $reactionTime мс. Нажмите чтобы попробовтаь снова."
                    _buttonState = ButtonState.Start
                    counter++
                    meanReactionTime += reactionTime
                    if (counter == attemptsCount) {
                        meanReactionTime /= counter
                        viewBinding.reactionButton.text =
                            "Ваш итоговый результат $meanReactionTime мс."
                        _buttonState = ButtonState.End
                    }
                }

                ButtonState.ClickRed -> {
                    viewBinding.reactionButton.visibility = View.VISIBLE
                    viewBinding.reactionButtonGreen.visibility = View.INVISIBLE
                    viewBinding.reactionButtonRed.visibility = View.INVISIBLE
                    viewBinding.reactionButton.text = resources.getString(R.string.complex_test_reaction_wrong_button_pressed)
                    _buttonState = ButtonState.Start
                }

                else -> {}
            }
        }

        viewBinding.reactionButtonRed.setOnClickListener {
            viewBinding.colour.text = ""
            when (_buttonState) {
                ButtonState.ClickRed -> {
                    viewBinding.reactionButton.visibility = View.VISIBLE
                    viewBinding.reactionButtonGreen.visibility = View.INVISIBLE
                    viewBinding.reactionButtonRed.visibility = View.INVISIBLE
                    reactionTime = System.currentTimeMillis() - reactionTime
                    viewBinding.reactionButton.text =
                        "Ваш результат $reactionTime мс. Нажмите чтобы попробовтаь снова."
                    _buttonState = ButtonState.Start
                    counter++
                    meanReactionTime += reactionTime
                    if (counter == attemptsCount) {
                        meanReactionTime /= counter
                        viewBinding.reactionButton.text =
                            "Ваш итоговый результат $meanReactionTime мс."
                        _buttonState = ButtonState.End
                    }
                }

                ButtonState.ClickGreen -> {
                    viewBinding.reactionButton.visibility = View.VISIBLE
                    viewBinding.reactionButtonGreen.visibility = View.INVISIBLE
                    viewBinding.reactionButtonRed.visibility = View.INVISIBLE
                    viewBinding.reactionButton.text = resources.getString(R.string.complex_test_reaction_wrong_button_pressed)
                    _buttonState = ButtonState.Start
                }

                else -> {}
            }
        }
    }

    override fun onPause() {
        super.onPause()
        if(_buttonState == ButtonState.Wait) {
            viewBinding.reactionButton.setBackgroundColor(resources.getColor(R.color.purple_700))
            viewBinding.reactionButton.text = resources.getString(R.string.complex_test_reaction_smth_wrong)
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
        ClickRed,
        ClickGreen,
        End
    }

    private fun timer(millisRunning : Long, countDownInterval : Long) : CountDownTimer {
        return object: CountDownTimer(millisRunning, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                viewBinding.reactionButton.visibility = View.INVISIBLE
                color = nextDouble(0.0, 1.0)
                if (color > 0.5) {
                    _buttonState = ButtonState.ClickRed
                    viewBinding.colour.text = resources.getString(R.string.complex_test_reaction_press_red_button)
                }
                else {
                    _buttonState = ButtonState.ClickGreen
                    viewBinding.colour.text = resources.getString(R.string.complex_test_reaction_press_green_button)
                }
                viewBinding.reactionButtonRed.visibility = View.VISIBLE
                viewBinding.reactionButtonGreen.visibility = View.VISIBLE
                reactionTime = System.currentTimeMillis()
            }
        }
    }
}