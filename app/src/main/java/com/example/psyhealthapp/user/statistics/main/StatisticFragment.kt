package com.example.psyhealthapp.user.statistics.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.psyhealthapp.R
import com.example.psyhealthapp.core.EmptyContentFragment
import com.example.psyhealthapp.core.TestResultsHolder
import com.example.psyhealthapp.user.statistics.LastDaysActivity
import com.example.psyhealthapp.user.statistics.reaction.Reaction
import com.example.psyhealthapp.user.statistics.Summary
import com.example.psyhealthapp.user.statistics.tapping.TappingMainFragment
import com.example.psyhealthapp.user.testing.results.TappingTestResult
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class StatPageTestFragment : Fragment(R.layout.statistic_fragment) {
    @Inject
    lateinit var resultsHolder: TestResultsHolder

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
}
