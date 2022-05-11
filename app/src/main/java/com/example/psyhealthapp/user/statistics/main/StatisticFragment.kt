package com.example.psyhealthapp.user.statistics.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.psyhealthapp.R
import com.example.psyhealthapp.core.TestResultsHolder
import com.example.psyhealthapp.user.statistics.LastDaysActivity
import com.example.psyhealthapp.user.statistics.Reaction
import com.example.psyhealthapp.user.statistics.Summary
import com.example.psyhealthapp.user.statistics.tapping.TappingMainFragment
import com.example.psyhealthapp.user.testing.results.TappingTestResult
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject

private val summaryTestValues = listOf(
    Pair("мышление", 90F),
    Pair("контроль", 81F),
    Pair("реакция", 75F),
    Pair("стабильность", 66F),
    Pair("воля", 52F),
    Pair("внимание", 37F),
    Pair("регуляция", 67F)
)

private val tappingSampleResult = TappingTestResult(
    Date(),
    listOf(
        3.361F,
        3.597F,
        3.659F,
        3.719F,
        3.792F,
        3.886F,
        3.983F,
        4.052F,
        4.123F,
        4.184F,
        4.243F,
        4.309F,
        4.38F,
        4.476F,
        4.54F,
        4.609F,
        4.709F,
        4.782F,
        4.854F,
        4.928F,
        4.998F,
        5.073F,
        5.154F,
        5.247F,
        5.323F,
        5.421F,
        5.506F,
        5.614F,
        5.723F,
        5.789F,
        5.884F,
        5.958F,
        6.02F,
        6.088F,
        6.186F,
        6.254F,
        6.313F,
        6.376F,
        6.476F,
        6.537F,
        6.596F,
        6.696F,
        6.797F,
        6.875F,
        6.936F,
        7F,
        7.06F,
        7.159F,
        7.25F,
        7.316F,
        7.399F,
        7.458F,
        7.516F,
        7.613F,
        7.713F,
        7.81F,
        7.913F,
        8.014F,
        8.093F,
        8.198F
    ),
    listOf(3F, 7F, 10F, 13F, 45F),
)

@AndroidEntryPoint
class StatPageTestFragment : Fragment(R.layout.statistic_fragment) {
    @Inject
    lateinit var resultsHolder: TestResultsHolder

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val resultsByDays = resultsHolder.getResultsCountByDays()

        childFragmentManager.beginTransaction().apply {
            replace(R.id.summary_content, Summary.newInstance(summaryTestValues))
            replace(
                R.id.tapping_content,
                TappingMainFragment.newInstance(resultsHolder.getLastTappingTestResult())
            )
            replace(
                R.id.reaction_content,
                Reaction.newInstance(resultsHolder.getReactionTestResults())
            )
            replace(
                R.id.last_days_activity_content,
                LastDaysActivity.newInstance(resultsHolder.getResultsCountByDays())
            )
        }.commit()
    }

}
