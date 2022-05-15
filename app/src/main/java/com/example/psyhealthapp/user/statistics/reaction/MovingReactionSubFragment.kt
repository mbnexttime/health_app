package com.example.psyhealthapp.user.statistics.reaction

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.psyhealthapp.R
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.psyhealthapp.databinding.StatMovingReactionSubfragmentBinding
import com.example.psyhealthapp.user.testing.results.MovingReactionTestResultList
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.time.format.DateTimeFormatter
import kotlin.math.sqrt

class MovingReactionSubFragment : Fragment(R.layout.stat_moving_reaction_subfragment) {
    private val viewBinding by viewBinding(StatMovingReactionSubfragmentBinding::bind)

    companion object {
        const val testResults = "testResults"
        private const val DASHED = 25F
        private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM")

        fun newInstance(movingReactionTestResultList: MovingReactionTestResultList): MovingReactionSubFragment {
            val arguments = Bundle()
            arguments.putParcelable(testResults, movingReactionTestResultList)
            val movingReactionSubFragment = MovingReactionSubFragment()
            movingReactionSubFragment.arguments = arguments
            return movingReactionSubFragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            it.getParcelable<MovingReactionTestResultList>(testResults)?.let { key ->
                setupChart(key)
            }
        }
    }

    private val indexWithValueToEntry = { it: Int, i: Float ->
        Entry(it.toFloat(), i)
    }

    private fun setupChart(movingReactionTestResultList: MovingReactionTestResultList) {
        val chart = viewBinding.chart

        val minDifferences = movingReactionTestResultList.results.map { it.minDiff }
        val maxDifferences = movingReactionTestResultList.results.map { it.maxDiff }
        val averages = movingReactionTestResultList.results.map { it.expected }
        val deviations = movingReactionTestResultList.results.map { sqrt(it.dispersion) }
        val dates = movingReactionTestResultList.results.map { it.date }

        val dataSetMinDiffs = LineDataSet(
            minDifferences.mapIndexed(indexWithValueToEntry),
            "мин"
        ).apply {
            color = ContextCompat.getColor(
                requireContext(),
                R.color.stat_tests_lastDaysActivity_barColor_1
            )
            lineWidth = 2F
            setCircleColor(R.color.stat_tests_lastDaysActivity_barColor_1)
            setDrawValues(false)
            enableDashedLine(DASHED, DASHED / 2, DASHED)
        }

        val dataSetMaxDiffs = LineDataSet(
            maxDifferences.mapIndexed(indexWithValueToEntry),
            "макс"
        ).apply {
            color = ContextCompat.getColor(
                requireContext(),
                R.color.stat_tests_lastDaysActivity_barColor_1
            )
            lineWidth = 2F
            setCircleColors(R.color.stat_tests_lastDaysActivity_barColor_1)
            setDrawValues(false)
            enableDashedLine(DASHED, DASHED / 2, DASHED)
        }

        val dataSetAverages = LineDataSet(
            averages.mapIndexed(indexWithValueToEntry),
            "среднее"
        ).apply {
            color = ContextCompat.getColor(
                requireContext(),
                R.color.black
            )
            lineWidth = 2F
            setCircleColor(R.color.black)
        }

        val dataSetDeviations = LineDataSet(
            deviations.mapIndexed(indexWithValueToEntry),
            "разница со средним"
        ).apply {
            color = ContextCompat.getColor(
                requireContext(),
                R.color.stat_tests_tapping_chart_lineColor_1
            )
            lineWidth = 2F
            setCircleColor(R.color.stat_tests_tapping_chart_lineColor_1)
            setDrawValues(false)
        }

        val chartData = LineData()
        chartData.addDataSet(dataSetMinDiffs)
        chartData.addDataSet(dataSetMaxDiffs)
        chartData.addDataSet(dataSetAverages)
        chartData.addDataSet(dataSetDeviations)

        chart.axisLeft.apply {
            setDrawGridLines(false)
        }

        chart.xAxis.apply {
            granularity = 1F
            setDrawGridLines(false)
            valueFormatter = IndexAxisValueFormatter(dates.map { it.format(formatter) })
        }

        chart.apply {
            setDrawGridBackground(false)
            description.isEnabled = false
            axisRight.isEnabled = false

            data = chartData
            invalidate()
        }
    }
}