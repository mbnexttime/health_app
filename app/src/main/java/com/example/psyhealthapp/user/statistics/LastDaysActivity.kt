package com.example.psyhealthapp.user.statistics

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.psyhealthapp.R
import com.example.psyhealthapp.util.RoundedVerticalBarChartRenderer
import com.example.psyhealthapp.user.testing.results.ResultsByDay
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import java.lang.Float.max
import java.lang.Float.min
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId

class LastDaysActivity : Fragment(R.layout.stat_lastdaysactivity) {
    private lateinit var chart: BarChart

    @SuppressLint("SimpleDateFormat")
    private val dateFmt = SimpleDateFormat("dd.MM")

    companion object {
        private const val CHART_BAR_RADIUS = 50F
        private const val POINTS_AT_SCREEN = 5F
        private const val DATA_SET_VALUE_TEXT_SIZE = 10F
        private const val XAXIS_GRANULARITY = 1F
        private const val YAXIS_MAXIMUM = 10F
        private const val YAXIS_MINIMUM = 0F
        private const val YAXIS_SCALE_COEF = 2F
        private const val YAXIS_GRANULARITY = 1F

        fun newInstance(lastDaysStat: ResultsByDay): LastDaysActivity {
            val arguments = Bundle()
            val lastDaysActivity = LastDaysActivity()
            arguments.putParcelable("lastDaysStat", lastDaysStat)
            lastDaysActivity.arguments = arguments
            return lastDaysActivity
        }
    }

    @SuppressLint("SetTextI18n")
    fun setTestsNumber(view: View, resultsByDay: ResultsByDay) {
        val totalNumberTextView = view.findViewById<TextView>(R.id.tests_number_total)
        totalNumberTextView.text = " ${resultsByDay.data.values.sum()}"

        val now = LocalDate.now()
        val upWeekNumberTextView = view.findViewById<TextView>(R.id.tests_number_at_week)
        upWeekNumberTextView.text = " ${
            resultsByDay.data.toList().fold(0) { acc, e ->
                if (Period.between(
                        e.first.toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                        now
                    ).months < 1
                ) {
                    acc + e.second
                } else {
                    acc
                }
            }
        }"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val lastDaysStat = it.getParcelable<ResultsByDay>("lastDaysStat")
            lastDaysStat?.let {
                setupChart(view, lastDaysStat)
                setTestsNumber(view, lastDaysStat)
            }
        }
    }

    private fun setupChart(view: View, lastDaysStat: ResultsByDay) {
        chart = view.findViewById(R.id.chart)

        val entries =
            lastDaysStat.data.values.mapIndexed { i, it -> BarEntry(i.toFloat(), it.toFloat()) }
        val dataSet = BarDataSet(entries, "last_days_activity")

        val colors = listOf(
            ContextCompat.getColor(
                requireContext(),
                R.color.stat_tests_lastDaysActivity_barColor_1
            ),
            ContextCompat.getColor(
                requireContext(),
                R.color.stat_tests_lastDaysActivity_barColor_2
            ),
            ContextCompat.getColor(
                requireContext(),
                R.color.stat_tests_lastDaysActivity_barColor_4
            ),
            ContextCompat.getColor(
                requireContext(),
                R.color.stat_tests_lastDaysActivity_barColor_3
            )
        )

        fun colorByResult(result: Int): Int {
            return when (result) {
                in 0..2 -> colors[0]
                in 3..4 -> colors[1]
                in 5..6 -> colors[2]
                else -> colors[3]
            }
        }

        dataSet.apply {
            setColors(lastDaysStat.data.map { colorByResult(it.value) })
            isHighlightEnabled = false

            valueFormatter = object : ValueFormatter() {
                override fun getBarLabel(barEntry: BarEntry?): String {
                    return barEntry?.y?.toInt().toString()
                }
            }

            valueTextSize = DATA_SET_VALUE_TEXT_SIZE;
        }

        val chartRenderer =
            RoundedVerticalBarChartRenderer(chart, chart.animator, chart.viewPortHandler)

        chartRenderer.apply {
            setRightRadius(CHART_BAR_RADIUS)
            setLeftRadius(CHART_BAR_RADIUS)
        }

        chart.xAxis.apply {
            valueFormatter =
                IndexAxisValueFormatter(lastDaysStat.data.keys.map { dateFmt.format(it) })
            textColor =
                ContextCompat.getColor(
                    requireContext(),
                    R.color.stat_tests_tapping_chart_lineColor_3
                )
            granularity = XAXIS_GRANULARITY
            position = XAxis.XAxisPosition.BOTTOM

            isGranularityEnabled = true
            setDrawGridLines(false)
            setDrawAxisLine(false)
        }

        chart.axisLeft.apply {
            val maxTestsByDay = lastDaysStat.data.values.maxOrNull()!!.toFloat()
            axisMaximum = min(
                max(YAXIS_MAXIMUM, maxTestsByDay), maxTestsByDay * YAXIS_SCALE_COEF
            )
            axisMinimum = YAXIS_MINIMUM
            granularity = YAXIS_GRANULARITY
            setDrawLabels(false)
        }

        chart.apply {
            setScaleMinima(
                max(
                    POINTS_AT_SCREEN,
                    lastDaysStat.data.size.toFloat()
                ) / POINTS_AT_SCREEN, 1F
            )
            centerViewTo(
                max(
                    0F,
                    lastDaysStat.data.size - POINTS_AT_SCREEN
                ), 0F, YAxis.AxisDependency.LEFT
            )

            renderer = chartRenderer
            data = BarData(dataSet)

            setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.stat_cardBackground
                )
            )

            axisRight.isEnabled = false
            legend.isEnabled = false
            description.isEnabled = false
            setScaleEnabled(false)

            animateY(250)
            invalidate()
        }
    }
}