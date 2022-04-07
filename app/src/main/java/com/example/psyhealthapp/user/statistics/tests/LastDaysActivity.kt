package com.example.psyhealthapp.user.statistics.tests

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.psyhealthapp.R
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class LastDaysActivity : CardView {
    private lateinit var chart: BarChart

    private val sampleData = listOf(2, 3, 1, 5, 4)
    private val sampleDays = listOf("пн", "вт", "ср", "чт", "пт")

    private val sampleColors = listOf(
        ContextCompat.getColor(context, R.color.bar_sample_color_1),
        ContextCompat.getColor(context, R.color.bar_sample_color_2),
        ContextCompat.getColor(context, R.color.bar_sample_color_3),
        ContextCompat.getColor(context, R.color.bar_sample_color_4),
        ContextCompat.getColor(context, R.color.bar_sample_color_5)
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

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
    }

    private fun setupChart() {
        chart = findViewById(R.id.chart)
        val entries = sampleData.mapIndexed { i, it -> BarEntry(i.toFloat(), it.toFloat()) }
        println(entries)

        val dataSet = BarDataSet(entries, "last_days_activity")
        dataSet.colors = sampleColors
        dataSet.setDrawValues(false)

        val xAxis = chart.xAxis
        xAxis.valueFormatter = IndexAxisValueFormatter(sampleDays)
        xAxis.granularity = 1f
        xAxis.isGranularityEnabled = true
        xAxis.setDrawGridLines(false)
        xAxis.setDrawAxisLine(false)
        xAxis.position = XAxis.XAxisPosition.BOTTOM

        chart.data = BarData(dataSet)
        chart.description.isEnabled = false
        chart.axisRight.isEnabled = false
        chart.axisLeft.isEnabled = false
        chart.setBackgroundColor(ContextCompat.getColor(context, R.color.light_beige))
        chart.legend.isEnabled = false

        chart.invalidate()
    }

    private fun setupView(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.lastdaysactivity, this)
        setupChart()
    }
}