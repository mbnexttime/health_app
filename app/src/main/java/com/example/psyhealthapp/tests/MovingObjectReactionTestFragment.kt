package com.example.psyhealthapp.tests

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.graphics.Matrix
import android.graphics.Path
import android.graphics.RectF
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.psyhealthapp.R
import com.example.psyhealthapp.databinding.MovingObjectReactionTestFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.abs
import kotlin.random.Random

@AndroidEntryPoint
class MovingObjectReactionTestFragment : Fragment(R.layout.moving_object_reaction_test_fragment) {
    private val viewBinding by viewBinding(MovingObjectReactionTestFragmentBinding::bind)

    private lateinit var timer: CountDownTimer
    private var testState = TestState.Start
    private lateinit var circleAnimatorSet : AnimatorSet

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var counter = 0
        val reactionTimesList = mutableListOf<Long>()
        val spinTimes = mutableListOf<Long>()


        val path = Path()
        val radiusTrajectory = resources.getDimensionPixelSize(R.dimen.moving_object_reaction_test_fragment_diameter_of_trajectory).toFloat() / 2
        val radiusCircle = resources.getDimensionPixelSize(R.dimen.moving_object_reaction_test_fragment_diameter_of_circle).toFloat() / 2
        val ringThickness = resources.getDimensionPixelSize(R.dimen.moving_object_reaction_test_fragment_thickness_of_ring).toFloat()

        path.addCircle(
            (view.parent as View).width.toFloat() / 2 - radiusCircle,
            viewBinding.trajectory.marginTop + radiusTrajectory - radiusCircle,
            radiusTrajectory - ringThickness / 2,
            Path.Direction.CW
        )

        val mMatrix = Matrix()
        val bounds = RectF()
        path.computeBounds(bounds, true)
        mMatrix.postRotate(-90F, bounds.centerX(), bounds.centerY())
        path.transform(mMatrix)

        val circleMoveAnimator = ObjectAnimator.ofFloat(
            viewBinding.circle,
            View.X,
            View.Y,
            path
        )
        circleMoveAnimator.repeatCount = Animation.INFINITE
        circleMoveAnimator.interpolator = LinearInterpolator()

        var reactionTime : Long = 0
        var time : Long = 0
        viewBinding.stopButton.isEnabled = true
        viewBinding.stopButton.text = resources.getString(R.string.moving_object_reaction_test_start_test)

        val visibleTrajectoryPart = 0.75
        testState = TestState.End

        circleAnimatorSet = AnimatorSet()
        circleAnimatorSet.play(circleMoveAnimator)

        viewBinding.stopButton.setOnClickListener {
            when (testState) {
                TestState.Start -> {}
                TestState.Proceed -> {
                    viewBinding.circle.visibility = View.VISIBLE
                    circleAnimatorSet.pause()
                    reactionTime = abs(System.currentTimeMillis() - reactionTime - time)
                    reactionTimesList.add(reactionTime)
                    testState = TestState.End
                    counter++
                    if (counter == 5) {
                        viewBinding.stopButton.text = resources.getString(R.string.moving_object_reaction_test_tap_to_proceed)
                        testState = TestState.Complete
                    }
                    else {
                        viewBinding.stopButton.text = resources.getString(R.string.moving_object_reaction_test_move_on)
                    }
                }
                TestState.End -> {
                    circleAnimatorSet.cancel()
                    reactionTime = System.currentTimeMillis()
                    time = (Random.nextDouble(3.0, 6.0) * 1000).toLong()
                    viewBinding.stopButton.isEnabled = false
                    timer = timer((time * visibleTrajectoryPart).toLong(), time).start()
                    testState = TestState.Proceed
                    circleAnimatorSet.duration = time
                    spinTimes.add(time)
                    circleAnimatorSet.start()
                    viewBinding.stopButton.text = resources.getString(R.string.moving_object_reaction_test_stop_circle)
                }
                TestState.Complete -> {
                    // TODO: реализовать переход, сохранение данных
                }
            }

        }

    }

    override fun onPause() {
        super.onPause()
        circleAnimatorSet.cancel()
    }

    private fun timer(millisRunning : Long, countDownInterval : Long) : CountDownTimer {
        return object: CountDownTimer(millisRunning, countDownInterval) {
            override fun onTick(millisUntilFinished: Long) {
            }

            override fun onFinish() {
                viewBinding.circle.visibility = View.INVISIBLE
                viewBinding.stopButton.isEnabled = true

            }
        }
    }

    enum class TestState {
        Start,
        Proceed,
        End,
        Complete
    }
}