package com.example.psyhealthapp.user.statistics.main

import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.psyhealthapp.R
import com.example.psyhealthapp.core.EmptyContentFragment
import com.example.psyhealthapp.core.TestResultsHolder
import com.example.psyhealthapp.user.statistics.LastDaysActivity
import com.example.psyhealthapp.user.statistics.reaction.Reaction
import com.example.psyhealthapp.user.statistics.tapping.TappingMainFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.psyhealthapp.databinding.StatisticFragmentBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class StatisticFragment : Fragment(R.layout.statistic_fragment) {
    private val viewBinding by viewBinding(StatisticFragmentBinding::bind)

    @Inject
    lateinit var resultsHolder: TestResultsHolder

    private fun setupDescriptions() {
        viewBinding.aboutTappingTest.setOnClickListener {
            val dialog = Dialog(requireContext())
            dialog.setContentView(R.layout.stat_tapping_description)
            dialog.show()

        }
    }

    private fun setupCards() {
        val tappingTestLastResult = resultsHolder.getLastTappingTestResult()
        val resultsByDay = resultsHolder.getResultsCountByDays()

        childFragmentManager.beginTransaction().apply {
            replace(
                R.id.summary_content,
                EmptyContentFragment.newInstance(
                    R.string.summary_placeholder_text,
                    R.drawable.ic_question
                )
            )

            if (tappingTestLastResult != null) {
                replace(
                    R.id.tapping_content,
                    TappingMainFragment.newInstance(tappingTestLastResult)
                )
            } else {
                replace(
                    R.id.tapping_content,
                    EmptyContentFragment.newInstance(
                        R.string.tapping_placeholder_text,
                        R.drawable.ic_hand
                    )
                )
            }

            if (resultsByDay.data.isNotEmpty()) {
                replace(
                    R.id.last_days_activity_content,
                    LastDaysActivity.newInstance(resultsByDay)
                )
            } else {
                replace(
                    R.id.last_days_activity_content,
                    EmptyContentFragment.newInstance(
                        R.string.lastdaycacitivy_placeholder_text,
                        R.drawable.ic_wizard
                    )
                )
            }

            replace(
                R.id.reaction_content,
                Reaction.newInstance(
                    resultsHolder.getReactionTestResults(),
                    resultsHolder.getComplexReactionTestResults(),
                    resultsHolder.getMovingReactionTestResults()
                )
            )
        }.commit()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDescriptions()
        setupCards()
    }
}
