package com.example.psyhealthapp.debug

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.psyhealthapp.R
import com.example.psyhealthapp.databinding.ComplexTestReactionFragmentBinding
import com.example.psyhealthapp.databinding.TestReactionFragmentBinding
import kotlin.random.Random.Default.nextDouble

class ComplexTestReactionFragment : Fragment(R.layout.complex_test_reaction_fragment) {
    private val viewBinding by viewBinding(ComplexTestReactionFragmentBinding::bind)

    private var _buttonState = ButtonState.Start

    private lateinit var timer: CountDownTimer
    private var reactionTime: Long = 0
    private var color: Double = 0.0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var counter = 0
        var meanReactionTime: Long = 0

        viewBinding.reactionButton.setOnClickListener {
            when (_buttonState) {
                ButtonState.Start -> {
                    viewBinding.reactionButton.text = "Wait for choice."
                    val time = (nextDouble(2.0, 4.0) * 1000).toLong()
                    //Toast.makeText(requireContext(), "$time", Toast.LENGTH_SHORT).show()
                    timer = timer(time, 1000).start()
                    _buttonState = ButtonState.Wait
                }
                ButtonState.Wait -> {
                    viewBinding.reactionButton.text = "To soon! Try again."
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
            when (_buttonState) {
                ButtonState.ClickGreen -> {
                    viewBinding.reactionButton.visibility = View.VISIBLE
                    viewBinding.reactionButtonGreen.visibility = View.INVISIBLE
                    viewBinding.reactionButtonRed.visibility = View.INVISIBLE
                    reactionTime = System.currentTimeMillis() - reactionTime
                    viewBinding.reactionButton.text =
                        "Your result $reactionTime ms. Press to start again."
                    _buttonState = ButtonState.Start
                    counter++
                    meanReactionTime += reactionTime
                    if (counter == 10) {
                        meanReactionTime /= counter
                        viewBinding.reactionButton.text =
                            "Your result $meanReactionTime ms."
                        _buttonState = ButtonState.End
                    }
                }

                ButtonState.ClickRed -> {
                    viewBinding.reactionButton.visibility = View.VISIBLE
                    viewBinding.reactionButtonGreen.visibility = View.INVISIBLE
                    viewBinding.reactionButtonRed.visibility = View.INVISIBLE
                    viewBinding.reactionButton.text = "Wrong Button! Try again."
                    _buttonState = ButtonState.Start
                }

                else -> {}
            }
        }

        viewBinding.reactionButtonRed.setOnClickListener {
            when (_buttonState) {
                ButtonState.ClickRed -> {
                    viewBinding.reactionButton.visibility = View.VISIBLE
                    viewBinding.reactionButtonGreen.visibility = View.INVISIBLE
                    viewBinding.reactionButtonRed.visibility = View.INVISIBLE
                    reactionTime = System.currentTimeMillis() - reactionTime
                    viewBinding.reactionButton.text =
                        "Your result $reactionTime ms. Press to start again."
                    _buttonState = ButtonState.Start
                    counter++
                    meanReactionTime += reactionTime
                    if (counter == 10) {
                        meanReactionTime /= counter
                        viewBinding.reactionButton.text =
                            "Your total result $meanReactionTime ms."
                        _buttonState = ButtonState.End
                    }
                }

                ButtonState.ClickGreen -> {
                    viewBinding.reactionButton.setBackgroundColor(resources.getColor(R.color.purple_700))
                    viewBinding.reactionButton.visibility = View.VISIBLE
                    viewBinding.reactionButtonGreen.visibility = View.INVISIBLE
                    viewBinding.reactionButtonRed.visibility = View.INVISIBLE
                    viewBinding.reactionButton.text = "Wrong Button! Try again."
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
            viewBinding.reactionButton.text = "Something happens! Try again."
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
                    viewBinding.reactionButtonRed.text = "Tap red!"
                    viewBinding.reactionButtonGreen.text = ""
                }
                else {
                    _buttonState = ButtonState.ClickGreen
                    viewBinding.reactionButtonGreen.text = "Tap green!"
                    viewBinding.reactionButtonRed.text = ""
                }
                viewBinding.reactionButtonRed.visibility = View.VISIBLE
                viewBinding.reactionButtonGreen.visibility = View.VISIBLE
                reactionTime = System.currentTimeMillis()
            }
        }
    }
}