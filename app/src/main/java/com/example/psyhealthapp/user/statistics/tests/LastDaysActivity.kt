package com.example.psyhealthapp.user.statistics.tests

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.psyhealthapp.R
import com.example.psyhealthapp.user.statistics.util.RoundedVerticalBarChartRenderer
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
import java.text.SimpleDateFormat

class LastDaysActivity : Fragment(R.layout.stat_tests_lastdaysactivity) {
    private lateinit var chart: BarChart

    @SuppressLint("SimpleDateFormat")
    private val dateFmt = SimpleDateFormat("dd.MM")

    companion object {
        enum class ChartConstants(val value: Float) {
            CHART_BAR_RADIUS(50F),
            POINTS_AT_SCREEN(6F)
        }

        fun newInstance(lastDaysStat: ResultsByDay): LastDaysActivity {
            val arguments = Bundle()
            val lastDaysActivity = LastDaysActivity()
            arguments.putParcelable("lastDaysStat", lastDaysStat)
            lastDaysActivity.arguments = arguments
            return lastDaysActivity
        }
    }

    @SuppressLint("SetTextI18n")
    fun setTestsNumber(view: View, sum: Int) {
        val textPole = view.findViewById<TextView>(R.id.tests_number)
        textPole.text = " $sum"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val lastDaysStat = it.getParcelable<ResultsByDay>("lastDaysStat")
            lastDaysStat?.let {
                setupChart(view, lastDaysStat)
                setTestsNumber(view, lastDaysStat.data.values.sum())
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

            valueTextSize = 10F;
            barBorderWidth = 1F;
        }

        val roundedVerticalBarChartRenderer =
            RoundedVerticalBarChartRenderer(chart, chart.animator, chart.viewPortHandler)

        roundedVerticalBarChartRenderer.apply {
            setRightRadius(50F)
            setLeftRadius(50F)
        }

        chart.xAxis.apply {
            valueFormatter =
                IndexAxisValueFormatter(lastDaysStat.data.keys.map { dateFmt.format(it) })
            granularity = 1F
            isGranularityEnabled = true
            setDrawGridLines(false)
            setDrawAxisLine(false)
            position = XAxis.XAxisPosition.BOTTOM
            textColor =
                ContextCompat.getColor(
                    requireContext(),
                    R.color.stat_tests_tapping_chart_lineColor_3
                )
        }

        chart.apply {
            setScaleMinima(
                max(
                    Companion.ChartConstants.POINTS_AT_SCREEN.value,
                    lastDaysStat.data.size.toFloat()
                ) / ChartConstants.POINTS_AT_SCREEN.value, 1F
            )
            centerViewTo(
                max(
                    0F,
                    lastDaysStat.data.size - ChartConstants.POINTS_AT_SCREEN.value
                ), 0F, YAxis.AxisDependency.LEFT
            )

            renderer = roundedVerticalBarChartRenderer
            data = BarData(dataSet)

            data.apply {
                if (lastDaysStat.data.size < ChartConstants.POINTS_AT_SCREEN.value) {
                    data.barWidth =
                        0.3F * lastDaysStat.data.size / ChartConstants.POINTS_AT_SCREEN.value
                }
            }

            description.isEnabled = false
            axisRight.isEnabled = false
            axisLeft.isEnabled = false
            setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.stat_cardBackground
                )
            )
            legend.isEnabled = false
            setScaleEnabled(false)
            animateY(250)
            invalidate()
        }
    }
}