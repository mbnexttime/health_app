package com.example.psyhealthapp.user.statistics.subpage

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.psyhealthapp.R
import com.example.psyhealthapp.user.statistics.tests.*
import com.example.psyhealthapp.user.testing.results.ReactionTestResult
import com.example.psyhealthapp.user.testing.results.TappingTestResult

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
    listOf(5F, 10F, 15F, 20F, 25F),
    listOf(37F, 30F, 28F, 29F, 27F),
)

private val reactionSampleResults = ReactionTestResult(
    listOf("29.04", "вчера", "вчера", "сегодня", "сегодня"),
    listOf(405F, 458F, 300F, 357F, 380F)
)

private val lastDaysSampleStat = LastDaysStat(
    listOf("пн", "вт", "ср", "чт", "пт"),
    listOf(2, 3, 1, 5, 4)
)

class StatPageTestFragment : Fragment(R.layout.statpage_tests_fragment) {
    @SuppressLint("InflateParams")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.statpage_tests_fragment, null)

        childFragmentManager.beginTransaction().apply {
            replace(R.id.summary_content, Summary.newInstance(summaryTestValues))
            replace(R.id.tapping_content, Tapping.newInstance(tappingSampleResult))
            replace(R.id.reaction_content, Reaction.newInstance(reactionSampleResults))
            replace(
                R.id.last_days_activity_content,
                LastDaysActivity.newInstance(lastDaysSampleStat)
            )
        }.commit()

        return view
    }
}
