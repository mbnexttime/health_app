package com.example.psyhealthapp.user.statistics.tests

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.psyhealthapp.R
import com.example.psyhealthapp.user.statistics.util.RoundedVerticalBarChartRenderer
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter

class LastDaysActivity : CardView {
    private lateinit var chart: BarChart

    private val sampleData = listOf(2, 3, 1, 5, 4)
    private val sampleDays = listOf("пн", "вт", "ср", "чт", "пт")

    private val sampleColors = listOf(
        ContextCompat.getColor(context, R.color.stat_tests_lastDaysActivity_barColor_1),
        ContextCompat.getColor(context, R.color.stat_tests_lastDaysActivity_barColor_2),
        ContextCompat.getColor(context, R.color.stat_tests_lastDaysActivity_barColor_4),
        ContextCompat.getColor(context, R.color.stat_tests_lastDaysActivity_barColor_4),
        ContextCompat.getColor(context, R.color.stat_tests_lastDaysActivity_barColor_3)
    )

    constructor(context: Context) : super(context) {
        setupView(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        setupView(context)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setupView(context)
    }

    private fun setupChart() {
        chart = findViewById(R.id.chart)
        val entries = sampleData.mapIndexed { i, it -> BarEntry(i.toFloat(), it.toFloat()) }

        val dataSet = BarDataSet(entries, "last_days_activity")
        dataSet.colors = sampleColors
        dataSet.valueFormatter = object : ValueFormatter() {
            override fun getBarLabel(barEntry: BarEntry?): String {
                return barEntry?.y?.toInt().toString()
            }
        }
        dataSet.valueTextSize = 10F;
        dataSet.barBorderWidth = 1f;

        val renderer = RoundedVerticalBarChartRenderer(chart, chart.animator, chart.viewPortHandler)
        renderer.setRightRadius(50f)
        renderer.setLeftRadius(50f)

        val xAxis = chart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(sampleDays)
        xAxis.granularity = 1f
        xAxis.isGranularityEnabled = true
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textColor =
            ContextCompat.getColor(context, R.color.stat_tests_tapping_chart_lineColor_3)

        chart.renderer = renderer
        chart.setTouchEnabled(false)
        chart.data = BarData(dataSet)
        chart.description.isEnabled = false
        chart.axisRight.isEnabled = false
        chart.axisLeft.isEnabled = false
        chart.setBackgroundColor(ContextCompat.getColor(context, R.color.stat_cardBackground))
        chart.legend.isEnabled = false
        chart.setScaleEnabled(false)
        chart.animateY(250)

        chart.invalidate()
    }

    private fun setupView(context: Context) {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.stat_tests_lastdaysactivity, this)
        setupChart()
    }
}