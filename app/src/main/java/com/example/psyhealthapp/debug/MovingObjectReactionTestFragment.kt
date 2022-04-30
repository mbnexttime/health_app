package com.example.psyhealthapp.debug

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
import androidx.core.view.marginLeft
import androidx.core.view.marginTop
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.psyhealthapp.R
import com.example.psyhealthapp.databinding.MovingObjectReactionTestFragmentBinding
import kotlin.math.abs
import kotlin.random.Random


class MovingObjectReactionTestFragment : Fragment(R.layout.moving_object_reaction_test_fragment) {
    private val viewBinding by viewBinding(MovingObjectReactionTestFragmentBinding::bind)

    private lateinit var timer: CountDownTimer
    private var testState = TestState.Start

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val path = Path()
        val radiusTrajectory = resources.getDimension(R.dimen.moving_object_reaction_test_fragment_diameter_of_trajectory) / 2
        val radiusCircle = resources.getDimension(R.dimen.moving_object_reaction_test_fragment_diameter_of_circle) / 2
        val ringThickness = resources.getDimension(R.dimen.moving_object_reaction_test_fragment_thickness_of_ring)

        path.addCircle(
            radiusTrajectory,
            viewBinding.trajectory.marginTop + radiusTrajectory,
            radiusTrajectory,
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

        var reactionTime = System.currentTimeMillis()
        var time = (Random.nextDouble(3.0, 6.0) * 1000).toLong()
        viewBinding.stopButton.isEnabled = false

        val visibleTrajectoryPart = 0.75
        timer = timer((time * visibleTrajectoryPart).toLong(), time).start()
        testState = TestState.Proceed

        val circleAnimatorSet = AnimatorSet()
        circleAnimatorSet.play(circleMoveAnimator)
        circleAnimatorSet.duration = time
        circleAnimatorSet.start()

        viewBinding.stopButton.setOnClickListener {
            when (testState) {
                TestState.Start -> {}
                TestState.Proceed -> {
                    viewBinding.circle.visibility = View.VISIBLE
                    circleAnimatorSet.pause()
                    reactionTime = abs(System.currentTimeMillis() - reactionTime - time)
                    testState = TestState.End
                }
                TestState.End -> {
                    circleAnimatorSet.cancel()
                    reactionTime = System.currentTimeMillis()
                    time = (Random.nextDouble(3.0, 6.0) * 1000).toLong()
                    viewBinding.stopButton.isEnabled = false
                    timer = timer((time * visibleTrajectoryPart).toLong(), time).start()
                    testState = TestState.Proceed
                    circleAnimatorSet.duration = time
                    circleAnimatorSet.start()

                }
            }

        }

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
        End
    }
}