package com.example.psyhealthapp.user.statistics.tests

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.psyhealthapp.R
import com.example.psyhealthapp.user.statistics.util.Calcus
import com.example.psyhealthapp.user.testing.results.ReactionTestResult
import com.example.psyhealthapp.user.testing.results.ReactionTestResultList
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import java.lang.Float.max
import java.text.SimpleDateFormat
import kotlin.math.roundToInt

class Reaction : Fragment(R.layout.stat_tests_reaction) {
    private lateinit var chart: LineChart

    @SuppressLint("SimpleDateFormat")
    private val dateFmt = SimpleDateFormat("dd.MM")

    companion object {
        enum class ChartConstants(val value: Float) {
            VALUE_TEXT_SIZE(14F),
            DASHED_LINE_LENGTH(10F),
            X_AXIS_PERCENT(5F),
            POINTS_AT_SCREEN(5F)
        }

        fun newInstance(reactionResult: ReactionTestResultList): Reaction {
            val arguments = Bundle()
            val reaction = Reaction()
            arguments.putParcelable("testResult", reactionResult)
            reaction.arguments = arguments
            return reaction
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val testResultList = it.getParcelable<ReactionTestResultList>("testResult")
            testResultList?.let {
                setupView(view, testResultList)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun setupView(view: View, testResultList: ReactionTestResultList) {
        chart = view.findViewById(R.id.chart)
        setupChart(testResultList)
        val bestResult = testResultList.results.maxByOrNull { it.result }?.result?.roundToInt() ?: 0
        val averageResult = testResultList.results.map { it.result }.average().roundToInt()

        val bestResultTextView = view.findViewById<TextView>(R.id.best_reaction_result)
        bestResultTextView.text = " $bestResult"

        val averageResultTextView = view.findViewById<TextView>(R.id.average_reaction_result)
        averageResultTextView.text = " $averageResult"
    }

    private fun setupChart(testResultList: ReactionTestResultList) {
        val dataSet =
            LineDataSet(
                testResultList.results.mapIndexed { it, i -> Entry(it.toFloat(), i.result) },
                "reaction_attempts"
            )

        dataSet.apply {
            lineWidth = 2F
            setDrawCircles(false)
            setDrawValues(true)
            mode = LineDataSet.Mode.CUBIC_BEZIER
            valueTextSize = valueTextSize
            color =
                ContextCompat.getColor(
                    requireContext(),
                    R.color.stat_tests_lastDaysActivity_barColor_3
                )
            setDrawFilled(true)
            fillColor =
                ContextCompat.getColor(requireContext(), R.color.stat_tests_tapping_textColor_1)
            valueTextColor =
                ContextCompat.getColor(
                    requireContext(),
                    R.color.stat_tests_tapping_chart_lineColor_3
                )
            valueTypeface = ResourcesCompat.getFont(requireContext(), R.font.oswald)
            enableDashedLine(
                ChartConstants.DASHED_LINE_LENGTH.value,
                ChartConstants.DASHED_LINE_LENGTH.value,
                ChartConstants.DASHED_LINE_LENGTH.value
            )
        }

        chart.xAxis.apply {
            granularity = 1F
            valueFormatter =
                IndexAxisValueFormatter(testResultList.results.map { dateFmt.format(it.date) })
            setDrawGridLines(false)
        }

        chart.axisLeft.apply {
            axisMinimum = 100F
            axisMaximum = 900F
            setDrawGridLines(false)
            setDrawLabels(false)
        }

        chart.apply {
            setScaleMinima(
                max(
                    ChartConstants.POINTS_AT_SCREEN.value,
                    testResultList.results.size.toFloat()
                ) / ChartConstants.POINTS_AT_SCREEN.value, 1f
            )
            centerViewTo(
                max(
                    0F,
                    testResultList.results.size - ChartConstants.POINTS_AT_SCREEN.value
                ), 0F, YAxis.AxisDependency.LEFT
            )
            description.isEnabled = false
            data = LineData(dataSet)
            axisRight.isEnabled = false
            legend.isEnabled = false
            animateY(250)
            invalidate()
        }
    }
}