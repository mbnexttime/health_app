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
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.psyhealthapp.core.UserDataHolder
import com.example.psyhealthapp.databinding.StatLastdaysactivityBinding
import java.time.LocalDateTime
import javax.inject.Inject
import kotlin.math.roundToInt

class LastDaysActivity : Fragment(R.layout.stat_lastdaysactivity) {
    private val viewBinding by viewBinding(StatLastdaysactivityBinding::bind)

    companion object {
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("dd MMMM")
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
        viewBinding.testsNumberTotal.text = " ${resultsByDay.data.values.sum()}"

        val now = LocalDateTime.now()
        viewBinding.testsNumberAtWeek.text = " ${
            resultsByDay.data.toList().fold(0) { acc, e ->
                if (ChronoUnit.MONTHS.between(e.first, now) < 1) {
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
        val chart = viewBinding.chart

        val entries =
            lastDaysStat.data.values.mapIndexed { i, it -> BarEntry(i.toFloat(), it.toFloat()) }
                .toMutableList()

        while (POINTS_AT_SCREEN - entries.size > 1) {
            entries.add(0, BarEntry(entries[0].x - 1, 0F))
            entries.add(BarEntry(entries.last().x + 1, 0F))
        }

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
            )
        )

        fun colorByResult(result: Float): Int {
            return when {
                result <= 2F -> colors[0]
                result <= 4F -> colors[1]
                else -> colors[2]
            }
        }

        dataSet.apply {
            setColors(entries.map { colorByResult(it.y) })
            isHighlightEnabled = false

            valueFormatter = object : ValueFormatter() {
                override fun getBarLabel(barEntry: BarEntry?): String {
                    if (barEntry != null) {
                        return if (barEntry.y > 0) {
                            barEntry.y.roundToInt().toString()
                        } else {
                            ""
                        }
                    }
                    return ""
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

        val days = lastDaysStat.data.keys.map { it.format(formatter) }

        chart.xAxis.apply {
            valueFormatter = object : ValueFormatter() {
                override fun getFormattedValue(value: Float): String {
                    val id = value.roundToInt()
                    return if (id in days.indices) {
                        days[id]
                    } else {
                        ""
                    }
                }
            }
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